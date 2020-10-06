package aot.sap.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import aot.common.constant.RICConstants;
import aot.sap.repository.SapRicControlDao;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.DETAILRETURN;
import aot.util.sap.domain.response.DETAILRETURNObj;
import aot.util.sap.domain.response.Item;
import aot.util.sap.domain.response.RETURN;
import aot.util.sap.domain.response.RETURNObj;
import aot.util.sap.domain.response.RETURN_;
import aot.util.sap.domain.response.RETURN_Obj;
import aot.util.sap.domain.response.SAPARResponseFail;
import aot.util.sap.domain.response.SAPARResponseFailObj;
import aot.util.sap.domain.response.SAPARResponseSuccess;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.util.paramconfig.ParamConfig;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.util.SqlGeneratorUtils;

@Service
public class SAPARService {

	// test
	@Autowired
	private ParamConfig paramConfig;

	@Autowired
	private SapRicControlDao sapRicControlDao;

	@Value("${ws.username}")
	private String username;
	@Value("${ws.password}")
	private String password;

	public SapResponse callSAPAR(ArRequest request) {
		SapResponse responseData = new SapResponse();
		try {

			ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			String json = mapper.writeValueAsString(request);
			json = json.replace("\"header\"", "\"Header\"");
			System.out.println(" ################# JSON From Client Befor Call SAP #####");
			System.out.println(json);

			final String uri = paramConfig.getParamConfig(RICConstants.SAP_AR_REST_URL);
			System.out.println(" ################# SAP_AR_REST_URL:" + uri);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// HttpEntity<ArRequest> requestcalSAP = new HttpEntity<>(request, headers);
			System.out.println("Username" + username);
			System.out.println("Password" + password);
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(username, password));

			String rawJsonReturnFromSAP = null;
			try {
				System.out.println(" ##  Before Call Case 1 ####");
				rawJsonReturnFromSAP = restTemplate.postForObject(uri, json, String.class);
				System.out.println(" ## After Call Case 1  rawJsonReturnFromSAP 1: " + rawJsonReturnFromSAP);
			} catch (Exception ex) {

				ex.printStackTrace();

				throw new Exception();
			}

			// String rawJsonReturnFromSAP2 = null;
			// try {
			// System.out.println(" ## Before Call Case 2 ####");
			// rawJsonReturnFromSAP2 = restTemplate.postForObject(uri, requestcalSAP,
			// String.class);
			// System.out.println(" ## After Call Case2 rawJsonReturnFromSAP 2:
			// "+rawJsonReturnFromSAP2);
			// }catch(Exception ex) {
			// ex.printStackTrace();
			// }
//			ObjectMapper mapper2 = new ObjectMapper();
			
			SAPARResponseSuccess successObj = null;
			SAPARResponseFail failObj = null;
			boolean isSuccess = true;
			Gson gson = new Gson();
//			try {
//				System.out.println(" ################# Try to Map Success object :");
////				successObj = mapper2.readValue(rawJsonReturnFromSAP, SAPARResponseSuccess.class); // check status sap
//				successObj = gson.fromJson(rawJsonReturnFromSAP, SAPARResponseSuccess.class);
//			} catch (Exception ex) {
//				System.out.println(" ################# Try to Map Fail object :");
//				ex.printStackTrace();
//				isSuccess = false;
//				// failObj= mapper2.readValue(rawJsonReturnFromSAP, SAPARResponseFail.class);
//				failObj = gson.fromJson(rawJsonReturnFromSAP, SAPARResponseFail.class);
//			}
			
			/* map object success or fail */
			JsonObject jsonObject = new JsonParser().parse(rawJsonReturnFromSAP).getAsJsonObject();
			String TRANSNO = jsonObject.get(SAPConstants.TRANSNO).getAsString();
			if (StringUtils.isNotBlank(TRANSNO)) {
				successObj = gson.fromJson(rawJsonReturnFromSAP, SAPARResponseSuccess.class);
			} else {
				isSuccess = false;
//				failObj = gson.fromJson(rawJsonReturnFromSAP, SAPARResponseFail.class);
				try {
					failObj = gson.fromJson(rawJsonReturnFromSAP, SAPARResponseFail.class);
					}catch(Exception ex) {
						ex.printStackTrace();					
						SAPARResponseFailObj failObj_Object = gson.fromJson(rawJsonReturnFromSAP, SAPARResponseFailObj.class);
						
						failObj = transformFailObjToArray(failObj_Object);
					}
			}

//			String jsonresponse = mapper.writeValueAsString(rawJsonReturnFromSAP);
//			System.out.println(" ################# JSON Response From SAP :" + jsonresponse);
			if (isSuccess) {
				System.out.println(" ################# SAP SUCCESS ################# ");
				System.out.println(mapper.writeValueAsString(successObj));
				responseData.setStatus(SAPConstants.SAP_SUCCESS);
				responseData.setSapARResponseSuccess(successObj);
//				System.out.println(" ################# Parse Result successObj :" + successObj);
	
				/* check LG (true => insert N/A replace dzdocNo) */
				Boolean LG = false;
				if(StringUtils.isNotBlank(request.getHeader().get(0).getRefKeyHeader2())) {
					switch (request.getHeader().get(0).getRefKeyHeader2()) {
					case SAPConstants.PAYMENT_TYPE.LG:
						LG = true;
						break;
					}
				}
				// save by jdbc
				sapRicControlDao.updateDataJdbc(successObj, LG);

			} else {
				System.out.println(" ################# SAP FAIL ################# ");
				System.out.println(mapper.writeValueAsString(failObj));
				responseData.setStatus(SAPConstants.SAP_FAIL);
				responseData.setSapARResponseFail(failObj);
//				System.out.println(" ################# Parse Result failObj :" + failObj);
			}

			responseData.setRawJsonStringFromSAP(rawJsonReturnFromSAP);

		} catch (Exception e) {
			System.out.println(" #################  Exception in call SAP Network:");

			responseData.setStatus(SAPConstants.SAP_CONNECTION_FAIL);

			e.printStackTrace();
		}
		return responseData;
	}
	
	private SAPARResponseFail transformFailObjToArray(SAPARResponseFailObj failObj) {

		SAPARResponseFail returnObj = new SAPARResponseFail();
		returnObj.setCOMP(failObj.getCOMP());
		returnObj.setDOCNO(failObj.getDOCNO());
		returnObj.setTAXNO(failObj.getTAXNO());
		returnObj.setTRANSNO(failObj.getTRANSNO());
		returnObj.setYEAR(failObj.getYEAR());

		RETURNObj RETURNObj = failObj.getRETURN();
		RETURN_Obj RETURN_Obj = RETURNObj.getRETURN();
		DETAILRETURNObj DETAILRETURNObj = RETURNObj.getDETAILRETURN();

		RETURN RETURN = new RETURN();
		DETAILRETURN DETAILRETURN = new DETAILRETURN();

		List<Item> itemList = new ArrayList<Item>();
		itemList.add(DETAILRETURNObj.getItem());
		DETAILRETURN.setItem(itemList);

		RETURN_ RETURN_ = new RETURN_();

		RETURN_.setDATERTN(RETURN_Obj.getDATERTN());
		RETURN_.setID(RETURN_Obj.getID());
		RETURN_.setLOGMSGNO(RETURN_Obj.getLOGMSGNO());
		RETURN_.setLOGNO(RETURN_Obj.getLOGNO());
		RETURN_.setMESSAGE(RETURN_Obj.getMESSAGE());
		RETURN_.setMESSAGEV1(RETURN_Obj.getMESSAGEV1());
		RETURN_.setMESSAGEV2(RETURN_Obj.getMESSAGEV2());
		RETURN_.setMESSAGEV3(RETURN_Obj.getMESSAGEV3());
		RETURN_.setMESSAGEV4(RETURN_Obj.getMESSAGEV4());
		RETURN_.setNUMBER(RETURN_Obj.getNUMBER());
		RETURN_.setTIMERTN(RETURN_Obj.getTIMERTN());
		RETURN_.setTYPE(RETURN_Obj.getTYPE());

		RETURN.setRETURN(RETURN_);
		RETURN.setDETAILRETURN(DETAILRETURN);

		returnObj.setRETURN(RETURN);

		return returnObj;

	}
	
	public SapResponse setSapConnection(SapConnectionVo req) throws JsonProcessingException {
		Collection<String> fieldNameList = new ArrayList<String>();
		List<Object> params = new ArrayList<>();
		ObjectMapper mapperMsg = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		
		if (SAPConstants.SAP_SUCCESS.equals(req.getDataRes().getStatus())) {
			req.getDataRes().setStatus(SAPConstants.SAP_SUCCESS);
//			req.getDataRes().setMessage(req.getDataRes().getRawJsonStringFromSAP());
			req.getDataRes().setMessage(RESPONSE_MESSAGE.SAP.SUCCESS);
			req.getDataRes().setMessageType(req.getDataRes().getStatus());

			// save response sap and sapControl
			/* 
			 * transaction_no 
			 * */
			if (StringUtils.isBlank(req.getColumnTransNo())) {
				fieldNameList.add("transaction_no");
			} else {
				fieldNameList.add(req.getColumnTransNo());
			}
			params.add(req.getDataRes().getSapARResponseSuccess().getTRANSNO());

			/* 
			 * invoice_no 
			 * */
			if (StringUtils.isBlank(req.getColumnInvoiceNo())) {
				fieldNameList.add("invoice_no");
			} else {
				fieldNameList.add(req.getColumnInvoiceNo());
			}
			params.add(String.valueOf(req.getDataRes().getSapARResponseSuccess().getDOCNO()));

			/* 
			 * sap_json_res 
			 * */
			if (StringUtils.isBlank(req.getColumnSapJsonRes())) {
				fieldNameList.add("sap_json_res");
			} else {
				fieldNameList.add(req.getColumnSapJsonRes());
			}
			params.add(req.getDataRes().getRawJsonStringFromSAP());
			
			if (StringUtils.isBlank(req.getColumnSapError())) {
				fieldNameList.add("sap_error");
			} else {
				fieldNameList.add(req.getColumnSapError());
			}
			params.add(mapperMsg.writeValueAsString(req.getDataRes().getSapARResponseSuccess()));

		} else if (SAPConstants.SAP_FAIL.equals(req.getDataRes().getStatus())) {
			req.getDataRes().setStatus(SAPConstants.SAP_FAIL);
//			req.getDataRes().setMessage(req.getDataRes().getRawJsonStringFromSAP());
			req.getDataRes().setMessage(RESPONSE_MESSAGE.SAP.FAILED);
			req.getDataRes().setMessageType(req.getDataRes().getStatus());

			// set data response error
			/* 
			 * sap_error 
			 * */
			if (StringUtils.isBlank(req.getColumnSapError())) {
				fieldNameList.add("sap_error");
			} else {
				fieldNameList.add(req.getColumnSapError());
			}
			params.add(mapperMsg.writeValueAsString(req.getDataRes().getSapARResponseFail()));

			/* 
			 * sap_json_res 
			 * */
			if (StringUtils.isBlank(req.getColumnSapJsonRes())) {
				fieldNameList.add("sap_json_res");
			} else {
				fieldNameList.add(req.getColumnSapJsonRes());
			}
			params.add(req.getDataRes().getRawJsonStringFromSAP());

		} else if (SAPConstants.SAP_CONNECTION_FAIL.equals(req.getDataRes().getStatus())) {
			req.getDataRes().setStatus(SAPConstants.SAP_CONNECTION_FAIL);
			req.getDataRes().setMessage(RESPONSE_MESSAGE.SAP.CONNECTION_FAILED);
			req.getDataRes().setMessageType(req.getDataRes().getStatus());

			// set data can't connect base
			/* 
			 * sap_error 
			 * */
			if (StringUtils.isBlank(req.getColumnSapError())) {
				fieldNameList.add("sap_error");
			} else {
				fieldNameList.add(req.getColumnSapError());
			}
			params.add(SAPConstants.SAP_CONNECTION_FAIL_MSG);

			/* 
			 * transaction_no
			 *  */
			if (StringUtils.isBlank(req.getColumnTransNo())) {
				fieldNameList.add("transaction_no");
			} else {
				fieldNameList.add(req.getColumnTransNo());
			}
			params.add(req.getDataSend().getHeader().get(0).getTransactionNo());
		}

		
		/* 
		 * sap_json_req 
		 * */
		if (StringUtils.isBlank(req.getColumnSapJsonReq())) {
			fieldNameList.add("sap_json_req");
		} else {
			fieldNameList.add(req.getColumnSapJsonReq());
		}
		ObjectMapper mapperReq = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		params.add(mapperReq.writeValueAsString(req.getDataSend()).replaceAll(" ", ""));

		/* 
		 * sap_status
		 *  */
		if (StringUtils.isBlank(req.getColumnSapStatus())) {
			fieldNameList.add("sap_status");
		} else {
			fieldNameList.add(req.getColumnSapStatus());
		}
		params.add(req.getDataRes().getStatus());

		/*
		 *  add id table
		 *   */
		Collection<String> conditionFieldNameList = new ArrayList<String>();
		conditionFieldNameList.add(req.getColumnId());
		params.add(req.getId());

		/* 
		 * generator sqlUpdate 
		 * */
		String sqlUpdateSapConnection = SqlGeneratorUtils.genSqlUpdate(req.getTableName(), fieldNameList, conditionFieldNameList);

		sapRicControlDao.updateSapConection(sqlUpdateSapConnection, params);
		return req.getDataRes();
	}
}

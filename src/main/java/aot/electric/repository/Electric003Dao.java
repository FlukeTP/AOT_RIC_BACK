package aot.electric.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricMeter;
import aot.electric.vo.request.Elec003FindMeterReq;
import aot.electric.vo.request.Elec003FindReq;
import aot.electric.vo.response.Electric003Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Electric003Dao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Electric003Res> findElec(Elec003FindReq form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		
//		sqlBuilder.append("SELECT" 
//		+" req.req_id, "
//		+" req.customer_type, " 		
//		+" req.customer_code, " 
//		+" req.customer_name, " 
//		+" req.contract_no, " 
//		+" req.meter_serial_no, "
//		+" req.request_status, " 
//		+" req.rental_area_name, "
//		+" req.install_position_service, "
//		+" req.request_start_date, " 
//		+" req.request_end_date, " 
//		+" req.sum_charge_rates," 
//		+" req.total_charge_rate, " 
//		+" req.sap_status_cash, " 
//		+" req.sap_status_lg, " 
//		+" req.sap_status_packages, " 
//		+" req.sap_error_desc_cash, " 
//		+" req.sap_error_desc_lg, "
//		+" req.sap_error_desc_packages, "
//		+" req.invoice_no_cash, "
//		+" req.invoice_no_lg, "
//		+" req.invoice_no_packages, "
//		+" req.transaction_no_cash, "
//		+" req.transaction_no_lg, "
//		+" req.transaction_no_packages, "
//		+" sap1.docno AS docno_cash, "
//		+" sap1.reverse_inv AS reverse_inv_cash, "
//		+" sap1.reverse_rec AS reverse_rec_cash, "
//		+" sap1.dzdocNo AS dzdocNo_cash, " 
//		+" sap2.docno AS docno_lg, "
//		+" sap2.reverse_inv AS reverse_inv_lg, "
//		+" sap2.reverse_rec AS reverse_rec_lg, "
//		+" sap2.dzdocNo AS dzdocNo_lg, " 
//		+" sap3.docno AS docno_packages, "
//		+" sap3.reverse_inv AS reverse_inv_packages, "
//		+" sap3.reverse_rec AS reverse_rec_packages, "
//		+" sap3.dzdocNo AS dzdocNo_packages, " 
//		+" CASE "
//        +" WHEN sap1.docno is null THEN 'X' "
//        +" WHEN sap1.reverse_inv = 'X' OR sap1.reverse_rec = 'X' THEN 'X' "
//        +" ELSE '' "
//    	+" END AS showButton_cash, "
//    	+" CASE "
//        +" WHEN sap2.docno is null THEN 'X' "
//        +" WHEN sap2.reverse_inv = 'X' OR sap2.reverse_rec = 'X' THEN 'X' "
//        +" ELSE '' "
//        +" END AS showButton_lg, "
//    	+" CASE "
//        +" WHEN sap3.docno is null THEN 'X' "
//        +" WHEN sap3.reverse_inv = 'X' OR sap3.reverse_rec = 'X' THEN 'X' "
//        +" ELSE '' "
//        +" END AS showButton_packages, "
//        +" CASE "
//        +" WHEN sap1.docno is null THEN 'X' "
//        +" WHEN sap1.reverse_inv = 'X' THEN 'X' "
//        +" WHEN sap1.reverse_rec = 'X' THEN 'X' "
//        +" ELSE '' "
//    	+" END AS button_manage_cash, "
//    	+" CASE "
//        +" WHEN sap2.docno is null THEN 'X' "
//        +" WHEN sap2.reverse_inv = 'X' THEN 'X' "
//        +" WHEN sap2.reverse_rec = 'X' THEN 'X' "
//        +" ELSE '' "
//    	+" END AS button_manage_lg, "
//    	+" CASE "
//        +" WHEN ch.old_serial_no is null OR can.serial_no is null THEN 'X' "
//        +" ELSE '' "
//    	+" END AS flag_change, "
//    	+" CASE "
//        +" WHEN can.serial_no is null OR ch.old_serial_no is null THEN 'X' "
//        +" ELSE '' "
//    	+" END AS flag_cancel "
//        +" from ric_electric_req req "
//		+" Left join sap_ric_control sap1 on req.transaction_no_cash = sap1.refkey1 "
//		+" Left join sap_ric_control sap2 on req.transaction_no_lg = sap2.refkey1 "
//		+" Left join sap_ric_control sap3 on req.transaction_no_lg = sap3.refkey1 "
//		+" Left join ric_electric_req_change ch on req.meter_serial_no = ch.old_serial_no "
//		+" Left join ric_electric_req_cancel can on req.meter_serial_no = can.serial_no "
//		+" WHERE req.is_delete ='N' ");
		
		sqlBuilder = SqlGeneratorUtils.genSqlSapRequest("RIC_ELECTRIC_REQ", "RIC_ELECTRIC_REQ_CANCEL", "RIC_ELECTRIC_REQ_CHANGE");
		sqlBuilder.append(" WHERE 1 = 1 ");
		sqlBuilder.append(" AND REQ.is_delete = ? ");
		params.add(FLAG.N_FLAG);
		
		if (StringUtils.isNotEmpty(form.getCustomerCode())) {
			sqlBuilder.append(" AND req.customer_code Like ? ");
			params.add("%" + form.getCustomerCode().trim() + "%");
		}
		
		if (StringUtils.isNotEmpty(form.getCustomerName())) {
			sqlBuilder.append(" AND req.customer_name Like ? ");
			params.add("%" + form.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getContracNo())) {
			sqlBuilder.append(" AND req.contract_no Like ? ");
			params.add("%" + form.getContracNo().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getRentalAreaName())) {
			sqlBuilder.append(" AND req.rental_area_name Like ? ");
			params.add("%" + form.getRentalAreaName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getInstallPositionService())) {
			sqlBuilder.append(" AND req.install_position_service Like ? ");
			params.add("%" + form.getInstallPositionService().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getRequestStatus())) {
			sqlBuilder.append(" AND req.request_status = ? ");
			params.add(form.getRequestStatus());
		}
		
		if (StringUtils.isNotEmpty(form.getCustomerType())) {
			sqlBuilder.append(" AND req.customer_type = ? ");
			params.add(form.getCustomerType());
		}
		
		sqlBuilder.append( " ORDER BY req_id DESC ");
		List<Electric003Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<Electric003Res> listRowmapper = new RowMapper<Electric003Res>() {
		@Override
		public Electric003Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Electric003Res vo = new Electric003Res();
			vo.setReqId(rs.getString("req_id"));
			vo.setCustomerType(rs.getString("customer_type"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContracNo(rs.getString("contract_no"));
			vo.setMeterSerialNo(rs.getString("meter_serial_no"));
			vo.setRequestStatus(rs.getString("request_status"));
			vo.setRentalAreaName(rs.getString("rental_area_name"));
			vo.setInstallPositionService(rs.getString("install_position_service"));
			vo.setDateStartReq(ConvertDateUtils.formatDateToString(rs.getDate("request_start_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setDateEndReq(ConvertDateUtils.formatDateToString(rs.getDate("request_end_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));

			vo.setSumChargeRates(rs.getString("sum_charge_rates"));
			vo.setTotalChargeRate(rs.getString("total_charge_rate"));
			vo.setRequestType(rs.getString("request_type"));
			
			vo.setTransactionNoCash(rs.getString("transaction_no_cash"));
			vo.setSapStatusCash(rs.getString("sap_status_cash"));
			vo.setSapErrorDescCash(rs.getString("sap_error_desc_cash"));
//			vo.setReverseInvCash(rs.getString("reverse_inv_cash"));
//			vo.setReverseRecCash(rs.getString("reverse_rec_cash"));
//			vo.setDzdocNoCash(rs.getString("dzdocNo_cash"));
//			vo.setDocnoCash(rs.getString("docno_cash"));
			vo.setInvoiceNoCash(rs.getString("invoice_no_cash"));
//			vo.setShowButtonCash(rs.getString("showButton_cash"));
//			vo.setButtonManageCash(rs.getString("button_manage_cash"));
	
			vo.setTransactionNoLg(rs.getString("transaction_no_lg"));
			vo.setSapStatusLg(rs.getString("sap_status_lg"));
			vo.setSapErrorDescLg(rs.getString("sap_error_desc_lg"));
//			vo.setReverseInvLg(rs.getString("reverse_inv_lg"));
//			vo.setReverseRecLg(rs.getString("reverse_rec_lg"));
//			vo.setDzdocNoLg(rs.getString("dzdocNo_lg"));
//			vo.setDocnoLg(rs.getString("docno_lg"));
			vo.setInvoiceNoLg(rs.getString("invoice_no_lg"));
//			vo.setShowButtonLg(rs.getString("showButton_lg"));
//			vo.setButtonManageLg(rs.getString("button_manage_lg"));
			
			vo.setTransactionNoPackages(rs.getString("transaction_no_packages"));
			vo.setSapStatusPackages(rs.getString("sap_status_packages"));
			vo.setSapErrorDescPackages(rs.getString("sap_error_desc_packages"));
//			vo.setReverseInvPackages(rs.getString("reverse_inv_packages"));
//			vo.setReverseRecPackages(rs.getString("reverse_rec_packages"));
//			vo.setDzdocNoPackages(rs.getString("dzdocNo_packages"));
//			vo.setDocnoPackages(rs.getString("docno_packages"));
			vo.setInvoiceNoPackages(rs.getString("invoice_no_packages"));
//			vo.setShowButtonPackages(rs.getString("showButton_packages"));
						
//			vo.setFlagChange(rs.getString("flag_change"));
//			vo.setFlagCancel(rs.getString("flag_cancel"));
			
			vo.setReceiptCash(rs.getString("DZDOCNO_CH"));
			vo.setReceiptLg(rs.getString("DZDOCNO_LG"));
			vo.setReceiptPackages(rs.getString("DZDOCNO_PACKAGE"));
			vo.setAllBtn(rs.getString("ALL_BTN"));
			vo.setReverseBtnCash(rs.getString("REVERSE_BTN_CASH"));
			vo.setReverseBtnLg(rs.getString("REVERSE_BTN_LG"));
			vo.setReverseBtnPackages(rs.getString("REVERSE_BTN_PK"));
			vo.setPaymentType(rs.getString("payment_type"));
			vo.setSapJsonReqCash(rs.getString("sap_json_req_cash"));
			vo.setSapJsonReqCash(rs.getString("sap_json_req_packages"));
			return vo;
		}
	};

	public List<RicElectricMeter> getElectricMeterByStatus(Elec003FindMeterReq req) {
		List<RicElectricMeter> target = new ArrayList<RicElectricMeter>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * "
				+ " FROM ric_electric_meter A "
				+ " WHERE NOT EXISTS "
				+ " (SELECT * "
				+ " FROM ric_electric_req B "
				+ " WHERE A.serial_no = B.meter_serial_no "
				+ " AND B.request_status = 'Y' "
				+ " AND B.is_delete = 'N'"
				+ ") "
				+ "AND A.meter_status ='open' "
				);
		
		
		if (StringUtils.isNotBlank(req.getCriteria())) {
			sqlBuilder.append(" AND A.serial_no Like ? ");
			params.add("%" + req.getCriteria().trim() + "%");
			
			sqlBuilder.append(" OR A.meter_type Like ? ");
			params.add("%" + req.getCriteria().trim() + "%");
			
			sqlBuilder.append(" OR A.meter_name Like ? ");
			params.add("%" + req.getCriteria().trim() + "%");			
		}
		
		sqlBuilder.append(" AND A.is_delete = 'N' ");
		sqlBuilder.append(" ORDER BY service_number DESC ");
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapperMeter);
		return target;
	}

	private RowMapper<RicElectricMeter> listRowmapperMeter = new RowMapper<RicElectricMeter>() {
		@Override
		public RicElectricMeter mapRow(ResultSet rs, int arg1) throws SQLException {
			RicElectricMeter vo = new RicElectricMeter();
			vo.setMeterId(rs.getLong("meter_id"));
			vo.setSerialNo(rs.getString("serial_no"));
			vo.setMeterType(rs.getString("meter_type"));
			vo.setMeterName(rs.getString("meter_name"));
			vo.setMultipleValue(rs.getString("multiple_value"));
			vo.setMeterLocation(rs.getString("meter_location"));
			vo.setFunctionalLocation(rs.getString("functional_location"));
			vo.setMeterStatus(rs.getString("meter_status"));
			vo.setServiceNumber(rs.getString("service_number"));
			vo.setAirport(rs.getString("airport"));
			vo.setRemark(rs.getString("remark"));
			return vo;
		}
	};
	
	public void updateIsDeletedOnChangeSuccess() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" UPDATE RIC_ELECTRIC_REQ SET IS_DELETE = ? "
				+ " FROM RIC_ELECTRIC_REQ REQ "
				+ " INNER JOIN RIC_ELECTRIC_REQ_CHANGE CHG "
//				+ " ON REQ.METER_SERIAL_NO = CHG.OLD_SERIAL_NO "
				+ " ON REQ.REQ_ID = CHG.REQ_ID "
				+ " INNER JOIN SAP_RIC_CONTROL SAP_CHG "
				+ " ON CHG.TRANSACTION_NO_LG = SAP_CHG.REFKEY1 "
				+ " WHERE SAP_CHG.DZDOCNO IS NOT NULL ");
		params.add(FLAG.Y_FLAG);
		commonJdbcTemplate.executeUpdate(sqlBuilder.toString(), params.toArray());
	}
}

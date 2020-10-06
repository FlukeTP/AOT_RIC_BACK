package aot.communicate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.common.constant.CommunicateConstants;
import aot.communicate.vo.request.Communicate004Req;
import aot.communicate.vo.response.Communicate004Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.module.service.SysConstantService;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;

@Repository
public class Communicate004Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	@Autowired
	private SysConstantService sysConstantService;

	public List<Communicate004Res> findData(Communicate004Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder = SqlGeneratorUtils.genSqlSapReqCommunicate("RIC_COMMUNICATE_REQ_FLIGHT_SCHEDULE_HDR");
		sqlBuilder.append(" WHERE TABLE_A.is_deleted = 'N' ");
		sqlBuilder.append(" AND TABLE_A.flag_cancel != ? ");
		params.add(CommunicateConstants.FLAG_CANCEL.TRUE);

		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND CONTRACT_NO LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getContractNo()));
		}

		if (StringUtils.isNotBlank(request.getEntreprenuerCode())) {
			sqlBuilder.append(" AND ENTREPRENUER_CODE LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerCode()));
		}
		
		if (StringUtils.isNotBlank(request.getEntreprenuerName())) {
			sqlBuilder.append(" AND ENTREPRENUER_NAME LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerName()));
		}

		sqlBuilder.append(" ORDER BY TABLE_A.ID DESC ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), communicateReqRowMapper);
	}

	private RowMapper<Communicate004Res> communicateReqRowMapper = new RowMapper<Communicate004Res>() {
		@Override
		public Communicate004Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Communicate004Res vo = new Communicate004Res();
			vo.setId(rs.getLong("id"));
			vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
			vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setRentalAreaCode(rs.getString("rental_area_code"));
			vo.setRentalAreaName(rs.getString("rental_area_name"));
			vo.setRemark(rs.getString("remark"));
			vo.setRequestDateStr(ConvertDateUtils.formatDateToString(rs.getDate("request_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setEndDateStr(ConvertDateUtils.formatDateToString(rs.getDate("end_date"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setPaymentType(rs.getString("payment_type"));
			vo.setBankName(rs.getString("bank_name"));
			vo.setBankBranch(rs.getString("bank_branch"));
			vo.setBankExplanation(rs.getString("bank_explanation"));
			vo.setBankGuaranteeNo(rs.getString("bank_guarantee_no"));
			vo.setBankExpNoStr(ConvertDateUtils.formatDateToString(rs.getDate("bank_exp_no"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setAmountLg(rs.getBigDecimal("amount_lg"));
			vo.setAmountMonth(rs.getBigDecimal("amount_month"));
			vo.setTotalAllDF(NumberUtils.toDecimalFormat(sysConstantService.getTotalVat(vo.getAmountLg()), true));
			vo.setAmountLgDF(NumberUtils.toDecimalFormat(vo.getAmountLg(), true));
			vo.setAmountMonthDF(NumberUtils.toDecimalFormat(vo.getAmountMonth(), true));
			
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setSapJsonReq(rs.getString("sap_json_req"));
			vo.setSapJsonRes(rs.getString("sap_json_res"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setInvoiceNo(rs.getString("invoice_no"));
			vo.setReverseBtn(rs.getString("REVERSE_BTN"));
			vo.setFlagCancel(rs.getString("flag_cancel"));
			vo.setCancelBtn(rs.getString("CANCEL_BTN"));
			vo.setFlagEndDate(rs.getString("FLAG_ENDDATE"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			
			/* _________ check wording payment type _________ */
			switch (rs.getString("payment_type")) {
			case CommunicateConstants.PAYMENT_TYPE.CASH.DESC_EN:
				vo.setPaymentTypeTh(CommunicateConstants.PAYMENT_TYPE.CASH.DESC_TH);
				break;
			case CommunicateConstants.PAYMENT_TYPE.BANK_GUARANTEE.DESC_EN:
				vo.setPaymentTypeTh(CommunicateConstants.PAYMENT_TYPE.BANK_GUARANTEE.DESC_TH);
				break;
			}

			return vo;
		}
	};
}

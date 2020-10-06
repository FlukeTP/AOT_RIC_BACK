package aot.water.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.water.vo.response.Water009CalRes;
import aot.water.vo.response.Water009Res;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class Water009Dao {

	private static final Logger logger = LoggerFactory.getLogger(Water009Dao.class);

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Water009Res> findData() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
//		sql.append(" SELECT * ");
//		sql.append(" FROM ric_water_waste_header ");
//		sql.append(" WHERE is_delete ='N' ");
//		sql.append(" ORDER BY waste_header_id DESC ");
		final String sql = "SELECT " + 
				"	HDR.WASTE_HEADER_ID, " + 
				"	HDR.CUSTOMER_CODE, " + 
				"	HDR.CUSTOMER_NAME, " + 
				"	HDR.CONTRACT_NO, " + 
				"	HDR.REMARK, " + 
				"	HDR.CUSTOMER_BRANCH, " + 
				"	HDR.RENTAL_AREA_CODE, " + 
				"	HDR.RENTAL_AREA_NAME, " + 
				"	HDR.PAYMENT_TYPE, " + 
				"	HDR.TRANSACTION_NO, " + 
				"	HDR.INVOICE_NO, " + 
				"	HDR.SAP_STATUS, " + 
				"	HDR.SAP_ERROR, " + 
				"	SUM(DTL.AMOUNT) AMOUNT, " + 
				"	SUM(DTL.VAT) VAT, " + 
				"	SUM(DTL.NET_AMOUNT) NET_AMOUNT, " + 
				"	SAP.DZDOCNO, " + 
				"	CASE " + 
				"		WHEN HDR.INVOICE_NO IS NULL THEN 'X' " + 
				"		WHEN SAP.REVERSE_INV = 'X' THEN 'X' " + 
				"		ELSE '' " + 
				"	END AS REVERSE_BTN " + 
				"FROM " + 
				"	RIC_WATER_WASTE_HEADER HDR " + 
				"INNER JOIN RIC_WATER_WASTE_DETAIL DTL ON " + 
				"	HDR.WASTE_HEADER_ID = DTL.WASTE_HEADER_ID " + 
				"LEFT JOIN SAP_RIC_CONTROL SAP ON " + 
				"	HDR.TRANSACTION_NO = SAP.REFKEY1 " + 
				"WHERE " + 
				"	HDR.IS_DELETE = 'N' " + 
				"GROUP BY " + 
				"	HDR.WASTE_HEADER_ID, " + 
				"	HDR.CUSTOMER_CODE, " + 
				"	HDR.CUSTOMER_NAME, " + 
				"	HDR.CONTRACT_NO, " + 
				"	HDR.CUSTOMER_BRANCH, " + 
				"	HDR.REMARK, " + 
				"	HDR.RENTAL_AREA_CODE, " + 
				"	HDR.RENTAL_AREA_NAME, " + 
				"	HDR.PAYMENT_TYPE, " + 
				"	HDR.TRANSACTION_NO, " + 
				"	HDR.INVOICE_NO, " + 
				"	HDR.SAP_STATUS, " + 
				"	HDR.SAP_ERROR, " + 
				"	SAP.REVERSE_INV, " +
				"	SAP.DZDOCNO " +
				" ORDER BY HDR.WASTE_HEADER_ID DESC";
		
		sqlBuilder.append(sql);
		List<Water009Res> data = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		logger.info("datas.size()={}", data.size());
		return data;
	}

	private RowMapper<Water009Res> listRowmapper = new RowMapper<Water009Res>() {
		@Override
		public Water009Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Water009Res vo = new Water009Res();
//			String createdDate = ConvertDateUtils.formatDateToString(rs.getDate("created_date"),
//					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN);
//
//			String updatedDate = ConvertDateUtils.formatDateToString(rs.getDate("updated_date"),
//					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN);

			vo.setWasteHeaderId(rs.getLong("waste_header_id"));
//			vo.setWasteDetailId(rs.getLong("waste_detail_id"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setRentalAreaName(rs.getString("rental_area_name"));
//			vo.setServiceType(rs.getString("service_type"));
//			vo.setUnit(rs.getBigDecimal("unit"));
//			vo.setAmount(rs.getBigDecimal("amount"));
			vo.setRemark(rs.getString("remark"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setInvoiceNo(rs.getString("invoice_no"));
//			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setReverseBtn(rs.getString("REVERSE_BTN"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setNetAmount(rs.getBigDecimal("NET_AMOUNT"));
			vo.setTransactionNo(rs.getString("TRANSACTION_NO"));
		/*	if (StringUtils.isNotBlank(updatedDate)) {
				vo.setDate(updatedDate);
			} else {
				vo.setDate(createdDate);
			}*/
			return vo;
		}
	};

	public Water009CalRes sumAmountDtl(Long idHdr) {
		StringBuilder sqlBD = new StringBuilder();
		List<Object> params = new ArrayList<>();
		final String sql = " SELECT " + "	SUM(AMOUNT) AMOUNT, " + "	SUM(VAT) VAT, " + "	SUM(NET_AMOUNT) NET_AMOUNT "
				+ " FROM " + "	RIC_WATER_WASTE_DETAIL " + " WHERE 1=1 " + "	AND IS_DELETE = 'N' "
				+ "	AND WASTE_HEADER_ID = ? " + " GROUP BY WASTE_HEADER_ID ";
		sqlBD.append(sql);
		params.add(idHdr);
		return commonJdbcTemplate.executeQueryForObject(sqlBD.toString(), params.toArray(),
				new RowMapper<Water009CalRes>() {
					@Override
					public Water009CalRes mapRow(ResultSet rs, int rowNum) throws SQLException {
						Water009CalRes vo = new Water009CalRes();
						vo.setAmount(rs.getBigDecimal("AMOUNT"));
						vo.setVat(rs.getBigDecimal("VAT"));
						vo.setNetAmount(rs.getBigDecimal("NET_AMOUNT"));
						return vo;
					}
				});
	}

}

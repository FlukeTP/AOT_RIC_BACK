package aot.common.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.common.vo.request.CustomerReq;
import aot.common.vo.response.ContractNoRes;
import aot.common.vo.response.CustomerRes;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class CustomerRepository {

	@Autowired
	CommonJdbcTemplate commonJdbcTemplate;

	public List<CustomerRes> getCustomer(CustomerReq request) {
		List<String> param = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT" + " CONCAT(name_org1,+' '+name_org2, name_org3, name_org4) AS customer_name, "
				+ " CONCAT(street+' ', str_suppl3+' ', location+' ', str_suppl1+' ', str_suppl2+' ', city2+' ', city1) as address, "
				+ " adr_kind, " + " partner, " + " '' AS phone_no, " + " '' AS contract_name, " + " customer_id, "
				+ " partner AS customer_code, " + " but000 AS status " + " FROM sap_customer ");

		if ("B3".equals(request.getType())) {
			sql.append(" WHERE butgroup = 'B3' ");
		} else {
			sql.append(" WHERE butgroup != 'B3' ");
		}

		if (StringUtils.isNotBlank(request.getCriteria())) {
			sql.append(" AND partner Like ? ");
			param.add("%" + request.getCriteria().trim() + "%");

			sql.append(" OR CONCAT(name_org1,+' '+name_org2, name_org3, name_org4) Like ? ");
			param.add("%" + request.getCriteria().trim() + "%");

			sql.append(" OR adr_kind Like ? ");
			param.add("%" + request.getCriteria().trim() + "%");

			sql.append(
					" OR CONCAT(street+' ', str_suppl3+' ', location+' ', str_suppl1+' ', str_suppl2+' ', city2+' ', city1) Like ? ");
			param.add("%" + request.getCriteria().trim() + "%");

		}

		return commonJdbcTemplate.executeQuery(sql.toString(), param.toArray(), customerMapper);
	}

	private RowMapper<CustomerRes> customerMapper = new RowMapper<CustomerRes>() {
		@Override
		public CustomerRes mapRow(ResultSet rs, int arg1) throws SQLException {
			CustomerRes vo = new CustomerRes();

			vo.setCustomerId(rs.getLong("customer_id"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setAdrKind(rs.getString("adr_kind"));
			vo.setContactName(rs.getString("contract_name"));
			vo.setPhoneNo(rs.getString("phone_no"));
			vo.setAddress(rs.getString("address"));
			vo.setStatus(rs.getString("status"));
			vo.setPartner(rs.getString("partner"));
			return vo;
		}
	};

	public List<CustomerRes> getCustomerAddress(String customerCode, String customerBranch) {
		List<String> param = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT" + " partner, " + " name_org1, " + " name_org2, " + " name_org3, " + " name_org4, "
				+ " adr_kind, " + " street, " + " location, " + " str_suppl1, " + " str_suppl2, " + " str_suppl3, "
				+ " city1, " + " city2, " + " post_code1, " + " country, " + " taxnumxl, " + " but000 "
				+ " FROM sap_customer ");
		sql.append(" WHERE partner = ? ");
		param.add(customerCode);
		if (StringUtils.isNotBlank(customerBranch)) {
			sql.append(" AND adr_kind = ? ");
			param.add(customerBranch);
		}

		return commonJdbcTemplate.executeQuery(sql.toString(), param.toArray(), customerAddressMapper);
	}

	private RowMapper<CustomerRes> customerAddressMapper = new RowMapper<CustomerRes>() {
		@Override
		public CustomerRes mapRow(ResultSet rs, int arg1) throws SQLException {
			CustomerRes vo = new CustomerRes();
			vo.setBusinessPartner(rs.getString("partner"));
			vo.setName1(rs.getString("name_org1"));
			vo.setName2(rs.getString("name_org2"));
			vo.setName3(rs.getString("name_org3"));
			vo.setName4(rs.getString("name_org4"));
			vo.setBranchCode(rs.getString("adr_kind"));
			vo.setStreet(rs.getString("street"));
			vo.setStreet2(rs.getString("str_suppl1"));
			vo.setStreet3(rs.getString("str_suppl2"));
			vo.setStreet4(rs.getString("str_suppl3"));
			vo.setStreet5(rs.getString("location"));
			vo.setDistrict(rs.getString("city2"));
			vo.setCity(rs.getString("city1"));
			vo.setPostCode(rs.getString("post_code1"));
			vo.setCountry(rs.getString("country"));
			vo.setTaxNumber(rs.getString("taxnumxl"));
			vo.setStatus(rs.getString("but000"));
			return vo;
		}
	};

	public List<ContractNoRes> getContractNo(String partner, String branchCode) {
		List<String> param = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT id, ");
		sql.append(" 	compCode, ");
		sql.append(" 	contractNo, ");
		sql.append(" 	contractStartDate, ");
		sql.append(" 	contractEndDate, ");
		sql.append(" 	businessPartner, ");
		sql.append(" 	invoiceAddressType, ");
		sql.append(" 	senderAddressType, ");
		sql.append(" 	bpValidFrom, ");
		sql.append(" 	bpValidTo, ");
		sql.append(" 	branchCode ");
		sql.append(" FROM aot_ric_m_re_utility ");
		sql.append(" WHERE branchCode = ? ");
		param.add(branchCode);
		sql.append(" AND businessPartner = ? ");
		param.add(partner);
		sql.append(" ORDER BY contractNo ASC ");

		return commonJdbcTemplate.executeQuery(sql.toString(), param.toArray(), contractNoMapper);
	}

	private RowMapper<ContractNoRes> contractNoMapper = new RowMapper<ContractNoRes>() {
		@Override
		public ContractNoRes mapRow(ResultSet rs, int arg1) throws SQLException {
			ContractNoRes vo = new ContractNoRes();
			vo.setId(rs.getLong("id"));
			vo.setCompCode(rs.getString("compCode"));
			vo.setContractNo(rs.getString("contractNo"));
			vo.setContractStartDate(ConvertDateUtils.formatDateToString(rs.getDate("contractStartDate"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setContractEndDate(ConvertDateUtils.formatDateToString(rs.getDate("contractEndDate"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setBusinessPartner(rs.getString("businessPartner"));
			vo.setInvoiceAddressType(rs.getString("invoiceAddressType"));
			vo.setSenderAddressType(rs.getString("senderAddressType"));
			vo.setBpValidFrom(ConvertDateUtils.formatDateToString(rs.getDate("bpValidFrom"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setBpValidTo(ConvertDateUtils.formatDateToString(rs.getDate("bpValidTo"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setBranchCode(rs.getString("branchCode"));
			return vo;
		}
	};

	public List<ContractNoRes> getRentalAreaRes(String contractNo) {
		List<String> param = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT id,roNumber,roName,compCode,contractNo ");
		sql.append(" FROM aot_ric_m_re_ro_utility ");
		sql.append(" WHERE contractNo = ? ");
		param.add(contractNo);
		sql.append(" ORDER BY id ASC ");

		return commonJdbcTemplate.executeQuery(sql.toString(), param.toArray(), rentalAreaResMapper);
	}

	private RowMapper<ContractNoRes> rentalAreaResMapper = new RowMapper<ContractNoRes>() {
		@Override
		public ContractNoRes mapRow(ResultSet rs, int arg1) throws SQLException {
			ContractNoRes vo = new ContractNoRes();
			vo.setId(rs.getLong("id"));
			vo.setRoNumber(rs.getString("roNumber"));
			vo.setRoName(rs.getString("roName"));
			return vo;
		}
	};

	public List<CustomerRes> getCustomers(CustomerRes request) {
		List<String> param = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM sap_customer");
		sql.append(" WHERE 1 = 1 ");

		if (StringUtils.isNoneBlank(request.getCustomerCode())) {
			sql.append(" AND partner = ? ");
			param.add(request.getCustomerCode());
		}

		if (StringUtils.isNoneBlank(request.getAdrKind())) {
			sql.append(" AND adr_kind = ? ");
			param.add(request.getAdrKind());
		}

		return commonJdbcTemplate.executeQuery(sql.toString(), param.toArray(), customerAddressMapper);
	}
}

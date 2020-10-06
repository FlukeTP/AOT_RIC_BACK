package aot.common.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.repository.CustomerRepository;
import aot.common.vo.request.CustomerReq;
import aot.common.vo.response.ContractNoRes;
import aot.common.vo.response.CustomerRes;

@Service
public class CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	@Autowired
	private CustomerRepository customerRepository;

	public List<CustomerRes> getSAPCustumer(CustomerReq request) throws Exception {
		List<CustomerRes> dataRes = customerRepository.getCustomer(request);
		return dataRes;
	}

	public CustomerRes getSAPCustomerAddress(String customerCode, String customerBranch) {
		CustomerRes cus = new CustomerRes();
		try {
			cus = customerRepository.getCustomerAddress(customerCode, customerBranch).get(0);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return cus;
	}

	public List<ContractNoRes> getSAPContractNo(String partner, String branchCode) throws Exception {
		List<ContractNoRes> dataRes = customerRepository.getContractNo(partner, branchCode);
		return dataRes;
	}

	public List<ContractNoRes> getRentalAreaRes(String contractNo) throws Exception {
		List<ContractNoRes> dataRes = customerRepository.getRentalAreaRes(contractNo);
		return dataRes;
	}

}

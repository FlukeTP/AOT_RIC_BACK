package aot.pos.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import aot.pos.model.RicPosCustomer;
import aot.pos.repository.Pos004Dao;
import aot.pos.repository.jpa.RicPosCustomerRepository;
import aot.pos.vo.request.Pos004CustomerReq;
import aot.pos.vo.request.Pos004UserReq;
import aot.pos.vo.response.Pos004CustomerRes;
import aot.pos.vo.response.Pos004Res;
import aot.pos.vo.response.Pos004UserRes;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.module.model.FwUsers;
import baiwa.module.model.FwUsersRole;
import baiwa.module.repository.jpa.FwUsersRepository;
import baiwa.module.repository.jpa.FwUsersRoleRepository;
import baiwa.util.UserLoginUtils;

@Service
public class Pos004Service {

	private static final Logger logger = LoggerFactory.getLogger(Pos004Service.class);

	@Autowired
	private RicPosCustomerRepository posCustomerRepository;

	@Autowired
	private FwUsersRepository fwUsersRepository;

	@Autowired
	FwUsersRoleRepository fwUserRoleRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private Pos004Dao pos004Dao;

	@Transactional(rollbackOn = { Exception.class })
	public String saveCustomer(Pos004CustomerReq request) {
		logger.info("saveCustomer", request);
		RicPosCustomer data = null;
		String msg = "";
		try {
			if (StringUtils.isBlank(request.getPosCustomerId())) {
				data = posCustomerRepository.checkContractNo(request.getContractNo());
				// check checkContractNo
				if (data == null) {
					// save
					data = new RicPosCustomer();
					data.setCustomerCode(request.getCustomerCode());
					data.setCustomerName(request.getCustomerName());
					data.setCustomerBranch(request.getCustomerBranch());
					data.setContractNo(request.getContractNo());
					data.setRentalArea(request.getRentalArea());
					data.setRemark(request.getRemark());
					data.setCreatedBy("setCreatedBy");
					data.setCreatedDate(new Date());
					data.setIsDelete("N");
					posCustomerRepository.save(data);

					msg = RESPONSE_MESSAGE.SAVE.SUCCESS;
				} else {
					msg = RESPONSE_MESSAGE.SAVE.DUPLICATE_DATA;
				}
			} else {
				// update
				data = posCustomerRepository.findById(Long.valueOf(request.getPosCustomerId())).get();
				data.setContractNo(request.getContractNo());
				data.setRentalArea(request.getRentalArea());
				data.setRemark(request.getRemark());
				data.setUpdatedBy("setUpdatedBy");
				data.setUpdatedDate(new Date());
				posCustomerRepository.save(data);

				msg = RESPONSE_MESSAGE.SAVE.SUCCESS;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return msg;
	}

	@Transactional(rollbackOn = { Exception.class })
	public String saveUser(Pos004UserReq request) {
		logger.info("saveUser", request);
		FwUsers fwUsers = null;

		String msg = "";
		try {
			if (StringUtils.isBlank(request.getUserId())) {
				fwUsers = fwUsersRepository.checkUserName(request.getUserName());
				// check userName
				if (fwUsers == null) {
					// save user
					fwUsers = new FwUsers();
					fwUsers.setPosCustomerId(Long.valueOf(request.getPosCustomerId()));
					fwUsers.setName(request.getName());
					fwUsers.setSurname(request.getSurname());
					fwUsers.setEmail(request.getEmail());
					fwUsers.setUserName(request.getUserName());
					fwUsers.setAirportCode(request.getAirportCode());
					fwUsers.setAirportDes(request.getAirportDes());
					fwUsers.setPassword(bcryptEncoder.encode(request.getPassword()));
					fwUsers.setCreatedBy(UserLoginUtils.getCurrentUsername());
					fwUsers.setCreatedDate(new Date());
					fwUsers.setIsDelete("N");
					fwUsersRepository.save(fwUsers);

					// save role
					if (request.getRole() != null && request.getRole().size() > 0) {
						for (String item : request.getRole()) {
							FwUsersRole userRole = new FwUsersRole();
							userRole.setUsername(request.getUserName());
							userRole.setRoleCode(item);
							userRole.setCreatedBy(UserLoginUtils.getCurrentUsername());
							userRole.setCreatedDate(new Date());
							fwUserRoleRepository.save(userRole);
						}
					}

					msg = RESPONSE_MESSAGE.SAVE.SUCCESS;
				} else {
					msg = RESPONSE_MESSAGE.SAVE.DUPLICATE_DATA;
				}
			} else {
				// update user
				fwUsers = fwUsersRepository.findById(Long.valueOf(request.getUserId())).get();
				fwUsers.setName(request.getName());
				fwUsers.setSurname(request.getSurname());
				fwUsers.setEmail(request.getEmail());
				fwUsers.setUserName(request.getUserName());
				fwUsers.setAirportCode(request.getAirportCode());
				fwUsers.setAirportDes(request.getAirportDes());
				fwUsers.setUpdatedBy("setUpdatedBy");
				fwUsers.setUpdatedDate(new Date());
				fwUsersRepository.save(fwUsers);

				// update role

				if (request.getRole() != null && request.getRole().size() > 0) {
					List<FwUsersRole> oldRole = fwUserRoleRepository.findByUsername(request.getUserName());
					fwUserRoleRepository.deleteAll(oldRole);
					for (String item : request.getRole()) {
						FwUsersRole userRole = new FwUsersRole();
						userRole.setUsername(request.getUserName());
						userRole.setRoleCode(item);
						userRole.setCreatedBy("setCreatedBy");
						userRole.setCreatedDate(new Date());
						fwUserRoleRepository.save(userRole);
					}
				}

				msg = RESPONSE_MESSAGE.SAVE.SUCCESS;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return msg;
	}

	public Pos004CustomerRes getByIdCustomer(String idStr) {
		logger.info("getByIdCustomer");
		Pos004CustomerRes res = null;
		try {
			RicPosCustomer data = posCustomerRepository.findById(Long.valueOf(idStr)).get();
			res = new Pos004CustomerRes();
			res.setPosCustomerId(data.getPosCustomerId().toString());
			res.setCustomerCode(data.getCustomerCode());
			res.setCustomerName(data.getCustomerName());
			res.setCustomerBranch(data.getCustomerBranch());
			res.setContractNo(data.getContractNo());
			res.setRentalArea(data.getRentalArea());
			res.setRemark(data.getRemark());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}

	public List<Pos004Res> findData() {
		logger.info("findData");

		List<Pos004Res> dataList = new ArrayList<>();

		try {
			dataList = pos004Dao.findData();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return dataList;
	}

	public List<Pos004UserRes> listUser(String posId) {
		List<Pos004UserRes> dataList = new ArrayList<>();
		Pos004UserRes data = null;
		try {
			List<FwUsers> userList = fwUsersRepository.posUserAll(Long.valueOf(posId));
			for (FwUsers user : userList) {
				data = new Pos004UserRes();
				data.setUserId(user.getUserId().toString());
				data.setPosCustomerId(user.getPosCustomerId().toString());
				data.setUserName(user.getUserName());
				data.setAirportDes(user.getAirportDes());
				data.setAirportCode(user.getAirportDes());
				data.setName(user.getName());
				data.setSurname(user.getSurname());
				data.setEmail(user.getEmail());

				List<FwUsersRole> roleList = fwUserRoleRepository.findByUsername(data.getUserName());
				data.setRole(roleList);

				dataList.add(data);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return dataList;
	}

	public Pos004UserRes getByIdUser(String idStr) {
		logger.info("getByIdUser");
		Pos004UserRes res = null;
		try {
			FwUsers data = fwUsersRepository.findById(Long.valueOf(idStr)).get();
			res = new Pos004UserRes();
			res.setUserId(data.getUserId().toString());
			res.setPosCustomerId(data.getPosCustomerId().toString());
			res.setUserName(data.getUserName());
			res.setAirportCode(data.getAirportCode());
			res.setAirportDes(data.getAirportDes());
			res.setEmail(data.getEmail());
			res.setName(data.getName());
			res.setSurname(data.getSurname());

			List<FwUsersRole> roleList = fwUserRoleRepository.findByUsername(data.getUserName());
			res.setRole(roleList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void deleteUser(String idStr) {
		logger.info("deleteUser");
		try {
			FwUsers fwUsers = fwUsersRepository.findById(Long.valueOf(idStr)).get();
			List<FwUsersRole> oldRole = fwUserRoleRepository.findByUsername(fwUsers.getUserName());
			// delete UserRole
			fwUserRoleRepository.deleteAll(oldRole);
			// delete user
			fwUsersRepository.deleteById(Long.valueOf(idStr));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}

package baiwa.module.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import baiwa.module.model.FwUsersRole;
import baiwa.module.repository.jpa.FwUsersRoleRepository;
import baiwa.module.vo.request.FwUserRoleReq;
import baiwa.module.vo.response.FwUserRoleRes;
import baiwa.util.ConvertDateUtils;

@Service
public class FwUserRoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(FwUserRoleService.class);
	
	@Autowired
	private FwUsersRoleRepository fwUserRoleRepository;

	public List<FwUserRoleRes> getUserRoleList() {
		logger.info("getUserRoleList");
		List<FwUsersRole> listFind = fwUserRoleRepository.findAll();
		List<FwUserRoleRes> newList = new ArrayList<>();
		FwUserRoleRes role = null;
		for (FwUsersRole roleFind : listFind) {
			role = new FwUserRoleRes();
			role.setUserRoleId(roleFind.getUsersRoleId());
			role.setUserId(roleFind.getUsername());
			role.setRoleId(roleFind.getRoleCode());
			role.setCreateDate(ConvertDateUtils.formatDateToString(roleFind.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY));
			newList.add(role);
		}
		return newList;
	}
	
	public FwUserRoleRes findById(FwUserRoleReq form) {
		logger.info("findById");
		FwUsersRole oldRole = fwUserRoleRepository.findById(Long.valueOf(form.getUserRoleId())).get();
		FwUserRoleRes newRole = new FwUserRoleRes();
		newRole.setUserId(oldRole.getUsername());
		newRole.setRoleId(oldRole.getRoleCode());
		newRole.setCreateDate(ConvertDateUtils.formatDateToString(oldRole.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY));
		return newRole;
	}
	
	public void saveUserRole(FwUserRoleReq form) {
		System.out.println("saveUserRole :"+form.getUserRoleId());
		FwUsersRole role = null;
		if (StringUtils.isNotEmpty(form.getUserRoleId())) {
			role = fwUserRoleRepository.findById(Long.valueOf(form.getUserRoleId())).get();
			System.out.println("saveUserRole Cound User Role :"+role);
			role.setUsername(form.getUsername());
			role.setRoleCode(form.getRoleCode());
			role.setUpdatedBy("Tester");
			role.setUpdatedDate(new Date());
		} else {
			role = new FwUsersRole();
			role.setUsername(form.getUsername());
			role.setRoleCode(form.getRoleCode());
			role.setCreatedBy("Tester");
			role.setCreatedDate(new Date());
		}
		
		fwUserRoleRepository.save(role);
		
	}
	
	public void deleteUserRole(FwUserRoleReq form) {
		logger.info("deleteUserRole");
		FwUsersRole role = fwUserRoleRepository.findById(Long.valueOf(form.getUserRoleId())).get();
		role.setIsDelete("Y");
		
		fwUserRoleRepository.save(role);
		
	}
}

package baiwa.module.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import baiwa.module.model.FwRole;
import baiwa.module.repository.jpa.FwRoleRepository;
import baiwa.module.vo.request.FwRoleReq;
import baiwa.module.vo.response.FwRoleRes;
import baiwa.util.ConvertDateUtils;

@Service
public class FwRoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(FwRoleService.class);
	
	@Autowired
	private FwRoleRepository fwRoleRepository;

	public List<FwRoleRes> getRoleList() {
		logger.info("getRoleList");
		List<FwRole> listFind = fwRoleRepository.findAll();
		List<FwRoleRes> newList = new ArrayList<>();
		FwRoleRes role = null;
		for (FwRole roleFind : listFind) {
			role = new FwRoleRes();
			role.setRoleId(roleFind.getRoleId());
			role.setRoleCode(roleFind.getRoleCode());
			role.setRoleCategory(roleFind.getRoleCategory());
			role.setRoleDesc(roleFind.getRoleDesc());
			role.setCreateDate(ConvertDateUtils.formatDateToString(roleFind.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY));
			newList.add(role);
		}
		return newList;
	}
	
	public FwRoleRes findById(FwRoleReq form) {
		logger.info("findById");
		FwRole oldRole = fwRoleRepository.findById(Long.valueOf(form.getRoleId())).get();
		FwRoleRes newRole = new FwRoleRes();
		newRole.setRoleId(oldRole.getRoleId());
		newRole.setRoleCode(oldRole.getRoleCode());
		newRole.setRoleCategory(oldRole.getRoleCategory());
		newRole.setRoleDesc(oldRole.getRoleDesc());
		newRole.setCreateDate(ConvertDateUtils.formatDateToString(oldRole.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY));
		return newRole;
	}
	
	public void saveRole(FwRoleReq form) {
		logger.info("saveRole");
		FwRole role = null;
		if (StringUtils.isNotEmpty(form.getRoleId())) {
			role = fwRoleRepository.findById(Long.valueOf(form.getRoleId())).get();
			role.setRoleCode(form.getRoleCode());
			role.setRoleCategory(form.getRoleCategory());
			role.setRoleDesc(form.getRoleDesc());
			role.setUpdatedBy("Tester");
			role.setUpdatedDate(new Date());
		} else {
			role = new FwRole();
			role.setRoleCode(form.getRoleCode());
			role.setRoleCategory(form.getRoleCategory());
			role.setRoleDesc(form.getRoleDesc());
			role.setCreatedBy("Tester");
			role.setCreatedDate(new Date());
		}
		
		fwRoleRepository.save(role);
		
	}
	
	public void deleteRole(FwRoleReq form) {
		logger.info("deleteRole");
		FwRole role = fwRoleRepository.findById(Long.valueOf(form.getRoleId())).get();
		role.setIsDelete("Y");
		
		fwRoleRepository.save(role);
		
	}
}

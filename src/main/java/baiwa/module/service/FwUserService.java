package baiwa.module.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import baiwa.module.model.FwUserDetail;
import baiwa.module.model.FwUsers;
import baiwa.module.model.FwUsersRole;
import baiwa.module.repository.FwUserDao;
import baiwa.module.repository.jpa.FwUsersRepository;
import baiwa.module.repository.jpa.FwUsersRoleRepository;
import baiwa.module.vo.request.UsersReq;

@Service
public class FwUserService {

	@Autowired
	FwUserDao fwUserDao;

	@Autowired
	FwUsersRoleRepository fwUserRoleRepository;

	@Autowired
	private FwUsersRepository fwUsersRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public List<String> getAlluser(UsersReq req) {
//		Object userList = fwUserDao.getAlluser(req);
//		UsersRes res = new UsersRes();
		List<String> supplierNames = Arrays.asList("sup1", "sup2", "sup3");
		return supplierNames;
	}

	public List<FwUsers> listAll() {
		List<FwUsers> fwLovList = (List<FwUsers>) fwUsersRepository.findAll();
		return fwLovList;
	}

	public void saveUser(UsersReq request) {
		FwUsers fwUsers = null;
		fwUsers = new FwUsers();
		// set data
		fwUsers.setName(request.getName());
		fwUsers.setSurname(request.getSurname());
		fwUsers.setEmail(request.getEmail());
		fwUsers.setUserName(request.getUserName());
		fwUsers.setAirportCode(request.getAirportCode());
		fwUsers.setAirportDes(request.getAirportDes());
		fwUsers.setPassword(bcryptEncoder.encode(request.getPassword()));
		fwUsers.setCreatedBy("Phattartapong krintavee");
		fwUsers.setCreatedDate(new Date());
		fwUsers.setIsDelete("N");
		// save data
		fwUsers = fwUsersRepository.save(fwUsers);

		saveRole(fwUsers.getUserName(), request.getRole());
	}

	public void editUser(UsersReq request) {
		FwUsers fwUsers = null;
		fwUsers = new FwUsers();
		// set data
		fwUsers = fwUsersRepository.findById(request.getUserId()).get();
		fwUsers.setName(request.getName());
		fwUsers.setSurname(request.getSurname());
		fwUsers.setEmail(request.getEmail());
		fwUsers.setAirportCode(request.getAirportCode());
		fwUsers.setAirportDes(request.getAirportDes());
//		if(StringUtils.isNotBlank(request.getPassword())) {
//			fwUsers.setPassword(request.getPassword());
//		}
		fwUsers.setUpdatedBy("Phattartapong krintavee");
		fwUsers.setUpdatedDate(new Date());
		// save data
		fwUsersRepository.save(fwUsers);
		this.saveRole(fwUsers.getUserName(), request.getRole());
	}

	private void saveRole(String username, String[] role) {
		List<FwUsersRole> oldRole = fwUserRoleRepository.findByUsername(username);
		fwUserRoleRepository.deleteAll(oldRole);
		for (String item : role) {
			FwUsersRole userRole = new FwUsersRole();
			userRole.setUsername(username);
			userRole.setRoleCode(item);
			userRole.setCreatedBy("Phattartapong krintavee");
			userRole.setCreatedDate(new Date());
			fwUserRoleRepository.save(userRole);
		}
	}

	public FwUserDetail getUser(UsersReq req) {
		FwUserDetail userDetail = new FwUserDetail();
		// user
		FwUsers fwUsers = new FwUsers();
		fwUsers = fwUsersRepository.findById(req.getUserId()).get();
		userDetail.setUser(fwUsers);
		// role
		List<FwUsersRole> role = fwUserRoleRepository.findByUsername(fwUsers.getUserName());
		userDetail.setRole(role);
		return userDetail;
	}

}

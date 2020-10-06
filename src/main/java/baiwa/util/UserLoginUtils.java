package baiwa.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import aot.common.model.Users;
import aot.common.repository.UsersRepository;
import baiwa.module.model.FwUsers;
import baiwa.module.repository.jpa.FwUsersRepository;
import baiwa.security.domain.UserDetails;

@Component
public class UserLoginUtils {
	
	private static UsersRepository usersRepository;

	@Autowired
	public UserLoginUtils(UsersRepository usersRepo) {
		UserLoginUtils.usersRepository = usersRepo;
	}
	
	public static UserDetails getCurrentUserBean() {
		UserDetails userBean = null;
		
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				userBean = (UserDetails) principal;
			} else if (principal instanceof User) {
				// UnitTest @WithMockUser
				User user = (User) principal;
				userBean = new UserDetails(user.getUsername(), "", user.getAuthorities());
			} else {
				// "anonymous" user
				String username = principal.toString();
				userBean = new UserDetails(username, "", AuthorityUtils.NO_AUTHORITIES);
			}
		} else {
			String username = "NO LOGIN";
			userBean = new UserDetails(username, "", AuthorityUtils.NO_AUTHORITIES);
		}
		
		return userBean;
	}

	public static String getCurrentUsername() {
		return UserLoginUtils.getCurrentUserBean().getUsername();
	}
	public static String getDepartment() {
		return UserLoginUtils.getCurrentUserBean().getDepartmentCode();
	}
	public static Users getUser() {
		if(StringUtils.isNotBlank(UserLoginUtils.getCurrentUserBean().getUsername())) {
			Users fwusers = UserLoginUtils.usersRepository.getUsers(UserLoginUtils.getCurrentUserBean().getUsername());
			return fwusers ;
		}else {
			return null;
		}
	}
	public static Date getDateNow() {
		return new Date();
	}
}

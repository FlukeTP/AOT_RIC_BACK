package baiwa.module.model;

import java.util.List;

public class FwUserDetail{
	
	private FwUsers user;
	private List<FwUsersRole> role;
	
	public FwUsers getUser() {
		return user;
	}
	public void setUser(FwUsers user) {
		this.user = user;
	}
	public List<FwUsersRole> getRole() {
		return role;
	}
	public void setRole(List<FwUsersRole> role) {
		this.role = role;
	}
	

}

package baiwa.security.domain;

public class UserInfo {
	
	private String username;
	private String password;
	private String role;
	
	private String organizeCode;
	private String organizeDesc;
	
	private String fullName;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOrganizeCode() {
		return organizeCode;
	}
	public void setOrganizeCode(String organizeCode) {
		this.organizeCode = organizeCode;
	}
	public String getOrganizeDesc() {
		return organizeDesc;
	}
	public void setOrganizeDesc(String organizeDesc) {
		this.organizeDesc = organizeDesc;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	

}

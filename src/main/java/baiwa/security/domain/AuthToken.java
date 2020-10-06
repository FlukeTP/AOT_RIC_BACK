package baiwa.security.domain;

public class AuthToken {

    private String token;
    private String username;
    private String fullName;
    private String  authorities;
    private String organizeCode;
    private String organizeDesc;
    public AuthToken(){

    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
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

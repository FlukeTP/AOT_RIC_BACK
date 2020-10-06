package aot.common.model;

public class Users {
	private Long userId;
	private String userName;
	private String name;
	private String surname;
	private String email;
	private String airportDes;
	private String airportCode;
	private String profitCenter;
	private Long posCustomerId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAirportDes() {
		return airportDes;
	}

	public void setAirportDes(String airportDes) {
		this.airportDes = airportDes;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	public Long getPosCustomerId() {
		return posCustomerId;
	}

	public void setPosCustomerId(Long posCustomerId) {
		this.posCustomerId = posCustomerId;
	}

}

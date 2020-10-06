package aot.pos.vo.response;

import java.util.List;

import baiwa.module.model.FwUsersRole;

public class Pos004UserRes {
	private String userId;
	private String posCustomerId;
	private String userName;
	private String password;
	private String airportDes;
	private String airportCode;
	private String surname;
	private String name;
	private String email;
	private List<FwUsersRole> role;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPosCustomerId() {
		return posCustomerId;
	}

	public void setPosCustomerId(String posCustomerId) {
		this.posCustomerId = posCustomerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<FwUsersRole> getRole() {
		return role;
	}

	public void setRole(List<FwUsersRole> role) {
		this.role = role;
	}

}

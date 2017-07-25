package com.zjht.manager.entity.base;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjht.manager.entity.UserRole;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class BaseUser implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2195555485509637879L;

    private Long              id;

    private String            code;                                     // 鐢ㄦ埛缂栧彿

    private String            userName;                                 // 鐢ㄦ埛鍚�
    @JsonIgnore
    private String            userPwd;                                  // 瀵嗙爜

    private Integer           status;                                   // 鐘舵��:0-澶辨晥; 1-姝ｅ父; 2-閿佸畾;3-寰呭鏍�

    private String            email;                                    // 閭

    private String            mobile;                                   // 鎵嬫満

    private Integer           userType;                                 // 鐢ㄦ埛绫诲瀷:0.鏅�氱敤鎴�1.绠＄悊鍛� 2.娌圭珯鐢ㄦ埛 3.浼佷笟鐢ㄦ埛 

    private Set<UserRole>     userRoles;								//鐢ㄦ埛瑙掕壊
    // Constructors

    /** default constructor */
    public BaseUser() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
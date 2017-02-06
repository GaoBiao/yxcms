package org.hebgb.app.cms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hebgb.app.cms.commons.enums.ObjectStatus;
import org.hebgb.web.shiro.authc.ShiroUser;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_admin")
public class Admin extends ShiroUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum RoleType {
		admin, operator, auditor
	}

	@Id
	@GeneratedValue(generator = "uuidGenericGenerator")
	@GenericGenerator(name = "uuidGenericGenerator", strategy = "uuid")
	@Column(length = 64)
	private String id;

	@Column(length = 128)
	private String name;

	@Column(name = "login_name", length = 128, updatable = false)
	private String loginName;

	@Column(length = 128)
	private String password;

	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Enumerated(EnumType.STRING)
	@Column(length = 32)
	private RoleType role;

	@Column(name = "last_login_time")
	private Date lastLoginTime;

	@Enumerated(EnumType.STRING)
	@Column(length = 32)
	private ObjectStatus status;

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ObjectStatus getStatus() {
		return status;
	}

	public void setStatus(ObjectStatus status) {
		this.status = status;
	}

	@Override
	public boolean isEnabled() {
		return ObjectStatus.enabled.equals(status);
	}

}

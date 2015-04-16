package xyz.fourcolor.oa.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

public class User {
	private Long id;
	private Department department;
	private Set<Role> roles = new HashSet<Role>();

	private String loginName;
	private String password;
	private String name;
	private String gender;
	private String phoneNumber;
	private String email;
	private String description;

	/**
	 * 判断本用户是否有指定名称的权限
	 */
	public boolean hasPrivilegeByName(String jspName) {
		// 超级管理有所有的权限
		if (isAdmin()) {
			return true;
		}
		// 普通用户要判断是否含有这个权限
		for (Role role : roles) {
			for (Privilege privilege : role.getPrivileges()) {
				if (privilege.getName().equals(jspName)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasPrivilegeByUrl(String privUrl) {
		// 超级管理有所有的权限
		if (isAdmin()) {
			return true;
		}
		// 去掉链接？号后面的参数
		int pos = privUrl.indexOf("?");
		if (pos > -1) {
			privUrl = privUrl.substring(0, pos);
		}
		// 去掉链接后面的UI
		if (privUrl.endsWith("UI")) {
			privUrl = privUrl.substring(0, privUrl.length() - 2);
		}

		// 如果本URL不需要权限验证，则登录用户就可以使用
		@SuppressWarnings("unchecked")
		Collection<String> allPrivilegeUrls = (Collection<String>) ActionContext
				.getContext().getApplication().get("allPrivilegeUrls");
		if (!allPrivilegeUrls.contains(privUrl)) {
			return true;
		} else {// 普通用户要判断是否含有这个权限
			for (Role role : roles) {
				for (Privilege privilege : role.getPrivileges()) {
					if (privUrl.equals(privilege.getUrl())) {
						return true;
					}
				}
			}
			return false;
		}
	}

	/**
	 * 判断本用户是否是超级管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		return "admin".equals(loginName);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

package com.yangtzelsl.dto;





import com.yangtzelsl.model.SysRole;

import java.util.List;

/**
 * @author luis.liu
 */
public class RoleDto extends SysRole {

	private static final long serialVersionUID = -5784234789156935003L;

	private List<Long> permissionIds;

	public List<Long> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}
}

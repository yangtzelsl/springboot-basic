package com.yangtzelsl.service;

import com.alibaba.fastjson.JSONArray;
import com.yangtzelsl.base.result.Results;
import com.yangtzelsl.model.SysPermission;


import java.util.List;

public interface PermissionService {

    Results<JSONArray> listAllPermission();

    Results<SysPermission> listByRoleId(Integer intValue);

    Results<SysPermission> getMenuAll();

    Results<SysPermission> save(SysPermission sysPermission);

    SysPermission getSysPermissionById(Integer id);

    Results  updateSysPermission(SysPermission sysPermission);

    Results delete(Integer id);

    List<SysPermission> getMenu();

    Results<SysPermission> getMenu(Long userId);
}

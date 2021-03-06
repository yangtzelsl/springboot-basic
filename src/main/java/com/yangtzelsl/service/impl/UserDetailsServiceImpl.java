package com.yangtzelsl.service.impl;


import com.yangtzelsl.dao.PermissionDao;
import com.yangtzelsl.dto.LoginUser;
import com.yangtzelsl.model.SysPermission;
import com.yangtzelsl.model.SysUser;
import com.yangtzelsl.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spring security登陆处理<br>
 *
 * @author luis.liu
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 实现用户自定义的验证逻辑
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1.根据该用户名查询在数据库中是否存在
        SysUser sysUser = userService.getUser(username);
        if (sysUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户名不存在");
        } else if (sysUser.getStatus() == SysUser.Status.LOCKED) {
            throw new LockedException("用户被锁定,请联系管理员");
        } else if (sysUser.getStatus() == SysUser.Status.DISABLED) {
            throw new DisabledException("用户已作废");
        }

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);

        // 2.存在则查询对应的用户权限
        // TODO 实际生产操作即从DB获取用户，权限
        List<SysPermission> permissions = permissionDao.listByUserId(sysUser.getId());

        // 3.将该权限添加到security
        loginUser.setPermissions(permissions);

        return loginUser;
    }

}

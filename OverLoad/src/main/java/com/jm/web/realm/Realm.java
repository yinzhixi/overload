package com.jm.web.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.jm.bean.Emp;
import com.jm.bean.Permission;
import com.jm.bean.Role;
import com.jm.dao.EmpDao;
import com.jm.dao.PermissionDao;
import com.jm.dao.RoleDao;

public class Realm extends AuthorizingRealm {

    @Autowired
    private EmpDao empDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 根据用户名 查询出当前用户的角色
        List<Role> roles = roleDao.getByEmpName(principals.toString());
        // 将角色交于授权器
        SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();
        for (Role role : roles) {

            // 根据角色id拿到具体要操作的权限
            List<Permission> byRoleId = permissionDao.getByRoleId(role.getId());
            for (Permission permission : byRoleId) {
                sa.addStringPermission(permission.getId());
            }
            sa.addRole(role.getId());

        }
        return sa;
    }

    /**
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取令牌的身份标识
        String username = token.getPrincipal().toString();
        Emp byEmpName = empDao.getByEmpName(username);
        // 根据用户名去数据库中查询数据
        if (username.equals(byEmpName.getEmpName())) {
            String password = byEmpName.getPassWord();
            String salt = byEmpName.getSalt();
            return new SimpleAuthenticationInfo(byEmpName.getEmpName(), password, ByteSource.Util.bytes(salt),
                    this.getName());
        }
        return null;
    }

}

package com.jm.data.base;

public class PermissionConst {
    //权限
    public static final String PERMISSION_ADD = "permission:add";// 添加权限
    public static final String PERMISSION_DEL = "permission:del";// 删除权限", "admin"
    public static final String PERMISSION_UPDATE = "permission:update";// 修改权限", "admin"
    
    //预检
    public static final String PREVIEW_DEL = "preview:del";// 明细删除", "admin"
    public static final String PREVIEW_EXPORT = "preview:export";// 明细导出", "admin"
    public static final String PREVIEW_REVIEWED = "preview:reviewed";// 审核明细", "admin"
    public static final String PREVIEW_UPDATE = "preview:update";// 明细修改", "admin"
    
    //角色
    public static final String ROLE_ADD = "role:add";// 添加角色", "admin"
    public static final String ROLE_DEL = "role:del";// 角色删除", "admin"
    public static final String ROLE_EDITMENU = "role:editMenu";// 编辑菜单", "admin"
    public static final String ROLE_EDITPERMISSION = "role:editPermission";// 编辑权限", "admin"
    public static final String ROLE_UPDATE = "role:update";// "角色修改", "admin"
    
    //用户
    public static final String USER_ADD = "user:add";// "添加用户", "admin"
    public static final String USER_DEL = "user:del";// "删除用户", "admin"
    public static final String USER_EDITROLE = "user:editRole";// "编辑角色", "admin"
    public static final String USER_UPDATE = "user:update";// "用户修改", "admin"
    public static final String USER_VIEWALL = "user:viewAll";//查询用户
    
    //精简
    public static final String REVIEWED_DELETE = "reviewed:delete";// 精简删除
    public static final String REVIEWED_EXPORT = "reviewed:export";// 精简导出
    
    
    
    
}

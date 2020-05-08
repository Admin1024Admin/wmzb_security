package com.l024.wmzbsecuritycore.mapper;

import com.l024.wmzbsecuritycore.model.user.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询条件查询所有用户
     */
    public List<UserModel> queryUserAll(@Param("user") UserModel user);

    /**
     * 查询用户所有角色以及权限
     */
    public List<UserModel> getUserRoleAndPermission(@Param("user") UserModel user);
}

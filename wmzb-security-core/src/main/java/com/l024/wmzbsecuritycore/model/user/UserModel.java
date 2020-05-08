package com.l024.wmzbsecuritycore.model.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApiModel(value = "用户类")
@Data
public class UserModel implements Serializable,UserDetails {
    private Long uId;
    private String userName;
    private String password;
    private String name;
    private Integer age;
    private Long roleId;
    private int lock;
    private int enable;
    private int expired;
    private Timestamp insertTime;
    private Timestamp updateTime;
    private List<RoleModel> roleModels;
    // 权限集合
    List<GrantedAuthority> authList = new ArrayList<>();

    public UserModel(){

    }

    public UserModel(String userName){
        this.userName = userName;
    }

    /**
     * 获取权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.roleModels!=null&&this.roleModels.size()>0){
            for(RoleModel role:roleModels){
                GrantedAuthority grantedAuthority = new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return role.getRoleName();
                    }
                };
                authList.add(grantedAuthority);
            }
        }
        return authList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * 用户名字
     * @return
     */
    @Override
    public String getUsername() {
        return this.userName;
    }

    /**
     * 是否到期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.expired==1;
    }

    /**
     * 是否账户冻结
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.lock==1;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否删除可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return this.enable==1;
    }
}

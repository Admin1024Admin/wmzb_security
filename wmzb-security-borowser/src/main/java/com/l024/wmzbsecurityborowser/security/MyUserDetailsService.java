package com.l024.wmzbsecurityborowser.security;

import com.l024.wmzbsecuritycore.mapper.UserMapper;
import com.l024.wmzbsecuritycore.model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用户认证
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        //根据用户名从数据库查询用户信息,
        UserModel user = new UserModel(name);
        List<UserModel> userModels = userMapper.queryUserAll(user);
        System.out.println("查询的用户"+userModels);

        List<UserModel> userModels1 = userMapper.getUserRoleAndPermission(user);
        System.out.println("查询的用户"+userModels1);

        //查询用户角色以及对应的所有权限
        if(userModels!=null&&userModels.size()>0){
            UserModel currentUser = userModels.get(0);
//            currentUser.setPassword(passwordEncoder.encode(currentUser.getPassword()));
//            System.out.println("加密用户:"+currentUser);
            return currentUser;
        }
        return new UserModel();
    }
}

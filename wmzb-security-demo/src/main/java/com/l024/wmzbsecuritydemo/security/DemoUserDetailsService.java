package com.l024.wmzbsecuritydemo.security;

import com.l024.wmzbsecuritycore.mapper.UserMapper;
import com.l024.wmzbsecuritycore.model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义用户认证
 */
@Component
public class DemoUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        //根据用户名从数据库查询用户信息,
//        UserModel user = new UserModel(name);
//        List<UserModel> userModels = userMapper.queryUserAll(user);
//        System.out.println("查询的用户"+userModels);
//
//        List<UserModel> userModels1 = userMapper.getUserRoleAndPermission(user);
//        System.out.println("查询的用户"+userModels1);
//
//        //查询用户角色以及对应的所有权限
//        if(userModels!=null&&userModels.size()>0){
//            UserModel currentUser = userModels.get(0);
////            currentUser.setPassword(passwordEncoder.encode(currentUser.getPassword()));
////            System.out.println("加密用户:"+currentUser);
//            return currentUser;
//        }
//        return new UserModel();

        return buildUser("admin");
    }

    /**
     * socal根openid去数据库查询用户信息
     * @param
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
//根据用户名从数据库查询用户信息,
        System.out.println("qq登录获取的userId"+userId);
        UserModel user = new UserModel();
        user.setUId(Long.valueOf(userId));
        List<UserModel> userModels = userMapper.queryUserAll(user);
        System.out.println("查询的用户"+userModels);

        List<UserModel> userModels1 = userMapper.getUserRoleAndPermission(user);
        System.out.println("查询的用户"+userModels1);

        //查询用户角色以及对应的所有权限
        if(userModels!=null&&userModels.size()>0){
            UserModel currentUser = userModels.get(0);
//            currentUser.setPassword(passwordEncoder.encode(currentUser.getPassword()));
            System.out.println("加密用户:"+currentUser);
            return new SocialUser(userId,currentUser.getPassword(),true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
        }
        return new SocialUser(userId,passwordEncoder.encode("123456"),true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));

    }

    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        System.out.println("xxxxxxxxxxx");
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123456");
        return new SocialUser(userId, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }
}

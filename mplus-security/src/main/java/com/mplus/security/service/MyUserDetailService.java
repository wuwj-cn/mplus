package com.mplus.security.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mplus.security.jwt.JWTUser;
import com.mplus.security.model.Role;
import com.mplus.security.model.User;
import com.mplus.security.utils.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置用户权限认证
 * 当用户登录时会进入此类的loadUserByUsername方法对用户进行验证，验证成功后会被保存在当前回话的principal对象中
 * 系统获取当前登录对象信息方法 WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 *
 *              异常信息：
 *              UsernameNotFoundException     用户找不到
 *              BadCredentialsException       坏的凭据
 *              AccountExpiredException       账户过期
 *              LockedException               账户锁定
 *              DisabledException             账户不可用
 *              CredentialsExpiredException   证书过期
 * @author wuwj
 *
 */
@Slf4j
@Service("myUserDetailService")
public class MyUserDetailService implements UserDetailsService {

	@Autowired
//    private UserRoleService userRoleService;
	private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户：" + username);
        //用户用户信息和用户角色
//        UserRoleVO userRole = this.userRoleService.findUserAndRole(username);
        Result<User> result = this.userService.getUserInfo(username);
        if(/*userRole.getUserId() == null*/ null == result.getData()){
            //后台抛出的异常是：org.springframework.security.authentication.BadCredentialsException: Bad credentials  坏的凭证 如果要抛出UsernameNotFoundException 用户找不到异常则需要自定义重新它的异常
            log.info("登录用户：" + username + " 不存在.");
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        //获取用户信息
//        UserInfoVO userInfo = userRole.getUserInfo();
        User userInfo = result.getData();
        //获取用户拥有的角色
//        List<RoleVO> roleList = userRole.getRoles();
        List<Role> roleList = userInfo.getRoles();
        Set<GrantedAuthority> grantedAuths = new HashSet<GrantedAuthority>();
        if(roleList.size() > 0){
            roleList.stream().forEach(role ->{
//                grantedAuths.add(new SimpleGrantedAuthority(role.getAuthorizedSigns()));
            	grantedAuths.add(new SimpleGrantedAuthority(role.getAuthority()));
            });
        }
//        UserDetail userDetail = new UserDetail(userInfo.getUserAccount(),userInfo.getUserPwd(), grantedAuths);

//        return JWTUserDetailsFactory.create(userDetail,user);
        return new JWTUser(
        		userInfo.getId(),
        		userInfo.getUserName(),
        		userInfo.getPassword(),
        		grantedAuths);
    }

}

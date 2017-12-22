package date.robr.springboot.config.security;


import date.robr.springboot.user.domian.UserAuth;
import date.robr.springboot.user.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsServiceImpl  implements UserDetailsService {

	@Autowired
	private UserServiceImpl userServiceImpl;
	/**
	 * 提供一种从用户名可以查到用户并返回的方法
	 * @param account 帐号
	 * @return UserDetails
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		UserAuth userAuth = userServiceImpl.Ide_Query(account);
		if (userAuth == null) {
			throw new UsernameNotFoundException(String.format("No user found with userInfo '%s'.",account ));
		} else {
			return JwtUserFactory.create(userAuth);
		}
	}
/*	public JwtUser loadUserByUsername(UserAuth userAuth) throws UsernameNotFoundException {
		if(userAuth!=null && userAuth.getUserInfo() == null )userAuth = userServiceImpl.Ide_Query(userAuth);
		if (userAuth == null) {
			throw new UsernameNotFoundException(String.format("No user found with userInfo '%s'.", userAuth!=null?userAuth.getUserIde():"unknow"));
		} else {
			return JwtUserFactory.create(userAuth);
		}
	}*/


}

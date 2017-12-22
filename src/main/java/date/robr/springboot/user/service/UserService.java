package date.robr.springboot.user.service;

import org.springframework.stereotype.Service;

import date.robr.springboot.base.service.BaseService;
import date.robr.springboot.user.domian.UserAuth;
import date.robr.springboot.user.domian.UserInfo;
import date.robr.springboot.util.RetStatus;
@Service
public interface UserService extends BaseService{

	/**
	 * 手机号 + 验证码 进行登录 
	 * @param user
	 * @return
	 */
	public RetStatus user_Login(UserAuth userAuth,String code);
	/**
	 * 用户名 + 密码 进行登录
	 */
	public RetStatus user_Login(UserAuth userAuth);
	/**
	 * QQ 等第三方登录
	 * @param user
	 * @param code
	 * @param retStatus
	 * @return
	 */
	public RetStatus user_Login(UserAuth user,UserInfo userInfo);
	
	/**
	 * 增加登录方式时，如手机，邮箱等。
	 * @param user
	 * @param userInfo
	 * @return
	 */
	public RetStatus user_LoginSave(UserAuth user,UserInfo userInfo);
	
	/**
	 * 修改所有信息
	 * @param user
	 * @param userInfo
	 * @return
	 */
	public RetStatus user_LoginUpdate(UserAuth user,UserInfo userInfo);
	
	public String user_Token(String oldToken);
}

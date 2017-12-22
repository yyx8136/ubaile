package date.robr.springboot.user.web;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import date.robr.springboot.base.web.BaseController;
import date.robr.springboot.user.domian.UserAuth;
import date.robr.springboot.user.domian.UserInfo;
import date.robr.springboot.user.service.UserService;
import date.robr.springboot.util.RetStatus;


@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired  
	UserService  userService;
	/**
	 * 用户名和密码登录/短信验证码登录或注册
	 * @param userAuth
	 * @param isSms
	 * @param context
	 * @return
	 */
	@ApiOperation(value = "SMS登录")
	@RequestMapping("/userSms")
	public RetStatus userLogin(UserAuth userAuth,String code){
		return userService.user_Login(userAuth,code);
	}
	@ApiOperation(value = "账户登录")
	@RequestMapping("/userLogin")
	public RetStatus userLogin(UserAuth userAuth){
		return userService.user_Login(userAuth);
	}
	/**
	 * 第三方登录
	 * @param user
	 * @param userInfo
	 * @return
	 */
	@ApiOperation(value = "第三方登录")
	@RequestMapping("/userOther")
	public RetStatus userOther(UserAuth userAuth,UserInfo userInfo){
		return userService.user_Login(userAuth,userInfo);
	}
	
	@ApiOperation(value = "刷新token")
	@RequestMapping("/userToken")
	public String userToken(String Token){
		return userService.user_Token(Token);
	}

	/**
	 * 绑定新登录方式
	 * @param user
	 * @param userInfo
	 * @return
	 */
	@ApiOperation(value = "绑定新登录方式")
	@RequestMapping("/loginType")
	public RetStatus loginType(UserAuth userAuth,UserInfo userInfo){
		return userService.user_LoginSave(userAuth, userInfo);
	}
	
	
	@RequestMapping("/test")
	@PreAuthorize("hasRole('default')")//设置可以访问的用户角色
	//@Secured("role_private") 
	public String test(String ret){
		System.err.println(ret);
		return "ooo";
	}
	/**
	 * 修改用户头像
	 * @param user
	 * @param userInfo
	 * @return
	 */
	@ApiOperation(value = "修改用户头像")
	@RequestMapping("/updateIcon")
	public RetStatus updateIcon(UserAuth userAuth,UserInfo userInfo){
		return userService.user_LoginSave(userAuth, userInfo);
	}


	/*@ExceptionHandler(RuntimeException.class)  
	public @ResponseBody  
	RetStatus<String> runtimeExceptionHandler(RuntimeException runtimeException) {  
		//logger.error(runtimeException.getLocalizedMessage());  
		RetStatus<String>  retStatus = new RetStatus<String>();
		retStatus.setStatus(-100);
		retStatus.setResult(new String(runtimeException.getMessage()));
		return retStatus;  
	}*/


}

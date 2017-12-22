package date.robr.springboot.user.service.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import date.robr.springboot.base.service.impl.BaseServiceImpl;
import date.robr.springboot.config.security.JwtTokenUtil;
import date.robr.springboot.config.security.JwtUser;
import date.robr.springboot.config.security.JwtUserDetailsServiceImpl;
import date.robr.springboot.user.dao.UserAuthDao;
import date.robr.springboot.user.dao.UserInfoDao;
import date.robr.springboot.user.dao.UserRoleDao;
import date.robr.springboot.user.domian.UserAuth;
import date.robr.springboot.user.domian.UserInfo;
import date.robr.springboot.user.domian.UserRole;
import date.robr.springboot.user.service.UserService;
import date.robr.springboot.util.MobClient;
import date.robr.springboot.util.RetStatus;

@Component
public class UserServiceImpl extends BaseServiceImpl implements UserService  {

	@Autowired
	UserAuthDao userAuthDao;
	@Autowired
	UserInfoDao userInfoDao;
	@Autowired
	UserRoleDao userRoleDao;
	@Autowired
	MobClient mobClient;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${jwt.tokenHead}")
	private String tokenHead;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsServiceImpl userDetailsService;
	@Autowired
	RetStatus retStatus;
	UserDetails userDetails = null;
	Map<String, Object>  resultList = null;

	private static final String CACHE_KEY = "'user'";
	private static final String CACHE_NAME = "users";

	@Override
	public RetStatus user_Login(UserAuth userAuth,String code) {
		// TODO Auto-generated method stub

		if (!dataVerity(userAuth, null)) {
			return retStatus.returnRet(retCode_VERITY);
		}
		// 验证码匹配
		if (!smsVerity(userAuth.getUserIde(), code)) {
			return retStatus.returnRet(retCode_FAIL);// 设置状态为验证码或密码错误！
		}

		// 账户存在 即返回个人信息
		UserAuth QuserAuth = Ide_Query(userAuth,true);
		UserInfo userInfo = (null!=QuserAuth?QuserAuth.getUserInfo():null);
		if (userInfo != null) {

			//userInfo.getUserList().get(0)
			userDetails = userDetailsService.loadUserByUsername(userInfo.getUserList().get(0).getUserIde());
			resultList = new HashMap<String, Object>();
			resultList.put("userInfo",userInfo);
			resultList.put("token", jwtTokenUtil.generateToken(userDetails));
			return retStatus.returnRet(retCode_SUCCESS,resultList);// 登录成功
		}
		// 账户不存在 进行注册操作

		userInfo = user_create(userAuth);
		userDetails = userDetailsService.loadUserByUsername(userInfo.getUserList().get(0).getUserIde());
		resultList = new HashMap<String, Object>();
		resultList.put("userInfo",userInfo);
		resultList.put("token", jwtTokenUtil.generateToken(userDetails));
		return retStatus.returnRet(retCode_SAVE,resultList);// 注册成功
	}

	@Override
	public RetStatus user_Login(UserAuth userAuth) {
		// TODO Auto-generated method stub
		if (!dataVerity(userAuth, null)) {
			return retStatus.returnRet(retCode_VERITY);
		}
		UserAuth QuserAuth = Ide_Query(userAuth,true);
		UserInfo userInfo = (null!=QuserAuth?QuserAuth.getUserInfo():null);
		if (userInfo != null) {
			//userInfo.getUserList().get(0)
			userDetails = userDetailsService.loadUserByUsername(userInfo.getUserList().get(0).getUserIde());
			resultList = new HashMap<String, Object>();
			resultList.put("userInfo",userInfo);
			resultList.put("token", jwtTokenUtil.generateToken(userDetails));
			return retStatus.returnRet(retCode_SUCCESS,resultList);// 登录成功
		}
		return retStatus.returnRet(retCode_FAIL);// 设置状态为验证码或密码错误！
	}
	@Override
	public RetStatus user_Login(UserAuth userAuth, UserInfo userInfo) {
		// TODO Auto-generated method stub
		// 账户存在 即返回个人信息
		if (!dataVerity(userAuth, null)) {
			return retStatus.returnRet(retCode_VERITY);
		}
		UserAuth QuserAuth = Ide_Query(userAuth,true);
		UserInfo newUserInfo = (null!=QuserAuth?QuserAuth.getUserInfo():null);
		if (null != newUserInfo) {
			userDetails = userDetailsService.loadUserByUsername(userInfo.getUserList().get(0).getUserIde());
			resultList = new HashMap<String, Object>();
			resultList.put("userInfo",newUserInfo);
			resultList.put("token", jwtTokenUtil.generateToken(userDetails));
			return retStatus.returnRet(retCode_SUCCESS,resultList);// 登录成功

		}

		if (!dataVerity(null, userInfo)) {
			return retStatus.returnRet(retCode_VERITY);
		}
		// 账户不存在 进行注册操作
		userInfo = user_create(userAuth);
		userDetails = userDetailsService.loadUserByUsername(userInfo.getUserList().get(0).getUserIde());
		resultList = new HashMap<String, Object>();
		resultList.put("userInfo",userInfo);
		resultList.put("token", jwtTokenUtil.generateToken(userDetails));
		return retStatus.returnRet(retCode_SAVE,resultList);// 注册成功
	}

	@Override
	public RetStatus user_LoginSave(UserAuth userAuth, UserInfo userInfo) {
		// TODO Auto-generated method stub
		if (!dataVerity(userAuth, null)) {
			return retStatus.returnRet(retCode_VERITY);
		}
		if (null != Ide_Query(userAuth.getUserIde())) {
			return retStatus.returnRet(retCode_EXIST);// 已经存在登录方式
		}
		userAuth.setUserInfo(userInfo);
		resultList = new HashMap<String, Object>();
		resultList.put("userInfo",userInfo_create(userAuth));
		return retStatus.returnRet(retCode_SAVE,resultList);// 新增成功
	}
	@Override
	public RetStatus user_LoginUpdate(UserAuth userAuth, UserInfo userInfo) {
		// TODO Auto-generated method stub
		if (!dataVerity(userAuth, null)) {
			return retStatus.returnRet(retCode_VERITY);
		}
		UserInfo oldInfo = Ide_Query(userAuth,false).getUserInfo();
		if (null == oldInfo) {
			return retStatus.returnRet(retCode_FAIL);// 未注册的用户
		}

		return null;
	}

	/**
	 * 用以校验验证码是否正确进行登录注册操作 是否国际化加区号在这里
	 * 
	 * @param phone
	 * @param code
	 * @return
	 */
	public boolean smsVerity(String phone, String code) {
		mobClient.getSmsVerity(phone, code);
		return true;
	}
	/**
	 * 此方法用于登录时查询用户/已经更新用户资料
	 * @param userAuth
	 * @return
	 */
	@Cacheable(value = CACHE_NAME, key = "'user_'+#userAuth.getUserIde()")
	public UserAuth Ide_Query(UserAuth userAuth,boolean isLogin) {
		System.err.println("没有走缓存！");
		if(!StringUtils.isEmpty(userAuth.getUserInfo())){
			String userUid = StringUtils.isEmpty(userAuth.getUserInfo().getUserUid())?"":userAuth.getUserInfo().getUserUid();
			if(!StringUtils.isEmpty(userAuth.getUserInfo().getUserPassword())){
				userAuth = userAuthDao.findByUserIdeAndUserPassword(userAuth.getUserIde(), passwordEncoder.encode(userAuth.getUserInfo().getUserPassword()));
			}else { 
				userAuth = userAuthDao.findByUserIdeAndUserType(userAuth.getUserIde(),userAuth.getUserType());
			} 
			if(userAuth != null && isLogin){
				//用户登录时进行修改设备id 和 登录 时间  用以token  防止不同设备登录
				if(!userUid.equals(userAuth.getUserInfo().getUserUid())){
					userAuth.getUserInfo().setUserUid(userUid);
				}
				userAuth.getUserInfo().setUserLoginDate(new Timestamp(new Date().getTime()));
				return userAuthDao.save(userAuth);
			}else {
				return userAuth;
			}
		}
		return null;
		
	}
	public UserAuth Ide_Query(String username) {
		System.err.println("没有走缓存！");
		UserAuth userAuth = userAuthDao.findByUserIde(username);
		return userAuth;
	}


	@CacheEvict(value = CACHE_NAME, key = CACHE_KEY)
	public UserInfo user_create(UserAuth userAuth) {
		System.err.println("没有走缓存！");
		UserInfo userInfo = new UserInfo();
		String userName = (userAuth.getUserIde().length() > 4) ? userAuth.getUserIde().substring(0, 4) : userAuth.getUserIde();
		userInfo.setUserName("Ub" + userName);

		// userInfo.setId(userInfoDao.save(userInfo).getId());

		List<UserRole> userRoles = new ArrayList<UserRole>();
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		List<UserAuth> userAuthList = new ArrayList<UserAuth>();
		
		userInfo.setUserUid(userAuth.getUserInfo().getUserUid());
		userInfo.setUserRoles(userRoles);
		//userInfo.setUserPassword(UUID.randomUUID().toString().replace("-", ""));
		userInfo.setUserPassword(passwordEncoder.encode(new Date().toString()));

		userInfo.setUserResetDate(new Timestamp(new Date().getTime()));

		UserRole userRole = userRoleDao.getOne("1");
		userRoles.add(userRole);
		userInfos.add(userInfo);
		userRole.setUserInfos(userInfos);


		userAuth.setUserInfo(userInfo);

		userAuthList.add(userAuth);
		userInfo.setUserList(userAuthList);
		userInfoDao.save(userInfo);

		return userInfo;
	}

	@CacheEvict(value = CACHE_NAME, key = CACHE_KEY)
	public UserInfo userInfo_create(UserAuth userAuth) {
		System.err.println("没有走缓存！");
		return userAuthDao.save(userAuth).getUserInfo();
	}

	// 数据校验 未使用通用数据校验切面
	public boolean dataVerity(UserAuth userAuth, UserInfo userInfo) {
		if (null != userAuth) {
			if (!StringUtils.isEmpty(userAuth.getUserIde()) && !StringUtils.isEmpty(userAuth.getUserInfo().getUserUid()))
				return true;
		}
		if (userInfo != null) {
			if (!StringUtils.isEmpty(userInfo.getUserName()))
				return true;
		}
		return false;
	}
	@Override
	public String user_Token(String oldToken) {
		final String token = oldToken.substring(tokenHead.length());
		String account = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(account);
		if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate(),user.getUserUid())){
			return jwtTokenUtil.refreshToken(token);
		}
		return null;
	}




}

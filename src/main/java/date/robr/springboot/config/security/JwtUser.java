package date.robr.springboot.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import date.robr.springboot.user.domian.UserAuth;
import date.robr.springboot.user.domian.UserInfo;
import date.robr.springboot.user.domian.UserRole;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public class JwtUser extends UserAuth implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final Timestamp lastPasswordResetDate;
	private final String userUid;
	JwtUser(String userIde,UserInfo userInfo) {
		this.setUserIde(userIde);
		this.setUserInfo(userInfo);
		this.lastPasswordResetDate = userInfo.getUserResetDate();
		this.userUid = userInfo.getUserUid();
	}

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}
	@Override
	public String toString() {
		return "JwtUser [getAuthorities()=" + getAuthorities()
				+ ", getPassword()=" + getPassword() + ", getUsername()="
				+ getUsername() + ", isAccountNonExpired()="
				+ isAccountNonExpired() + ", isAccountNonLocked()="
				+ isAccountNonLocked() + ", isCredentialsNonExpired()="
				+ isCredentialsNonExpired() + ", isEnabled()=" + isEnabled()
				+ "]";
	}
	//返回分配给用户的角色列表
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<UserRole> roles = this.getUserInfo().getUserRoles();

		if(roles == null || roles.size() <1){
			return AuthorityUtils.commaSeparatedStringToAuthorityList("");
		}
		StringBuilder commaBuilder = new StringBuilder();
		for(UserRole role : roles){
			commaBuilder.append(role.getRoleName()).append(",");
		}
		String authorities = commaBuilder.substring(0,commaBuilder.length()-1);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
		//return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return getUserInfo().getUserPassword();
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getUserIde();
	}
	// 账户是否未过期
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	// 账户是否未锁定
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	// 密码是否未过期
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	// 账户是否激活
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getUserUid() {
		return userUid;
	}

}

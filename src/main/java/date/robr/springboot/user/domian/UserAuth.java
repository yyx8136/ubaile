package date.robr.springboot.user.domian;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;





import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import date.robr.springboot.base.domian.BaseDomian;

@Entity
@Table(name="user_auth")
public class UserAuth extends BaseDomian  implements Serializable{
	private static final long serialVersionUID = 1L;
	 
    @Id
	//@GeneratedValue
	//@Column(name="user_Id")
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
	protected String id;
	
	@ManyToOne(optional = false)
	//@JoinColumn(name="news_type", nullable=true, insertable = true, updatable = true) 
	private UserInfo userInfo;
	
	@Column(nullable = false)
	private Integer userType; //登录类型（1手机号  2用户名 3邮箱）或第三方应用名称（4qq 5微信 6微博等）
	@Column(nullable = false,unique=true)
	private String userIde; //标识（手机号 邮箱 用户名或第三方应用的唯一标识）
	
	
	public UserAuth(Integer userType, String userIde) {
		super();
		this.userType = userType;
		this.userIde = userIde;
	}
	public UserAuth() {
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	@JsonBackReference
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getUserIde() {
		return userIde;
	}
	public void setUserIde(String userIde) {
		this.userIde = userIde;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserAuth [id=" + id + ", userInfo=" + userInfo + ", userType="
				+ userType + ", userIde=" + userIde + "]";
	}
	

}

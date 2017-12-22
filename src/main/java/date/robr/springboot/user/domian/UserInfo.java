package date.robr.springboot.user.domian;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import date.robr.springboot.base.domian.BaseDomian;

@Entity
@Table(name="user_info")
public class UserInfo extends BaseDomian  implements Serializable{
	private static final long serialVersionUID = 1L;
	 
    @Id
	//@GeneratedValue
	//@Column(name="user_Id")
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
	protected String id;
	@Column(nullable = false)//,unique=true
	private String userName;
	private Integer userSex;
	private String userIcon;
	@JsonIgnore
	private Timestamp userResetDate;
	@JsonIgnore
	private Timestamp userLoginDate;

	/*@OneToOne
	@PrimaryKeyJoinColumn
	//@JoinColumn(name = "id")
	private UserRole userRole;
	 */
	@JsonIgnore
	@ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER,cascade = CascadeType.ALL )
	/*@JoinTable(joinColumns = @JoinColumn(name = "ROLE_ID"),
	inverseJoinColumns = @JoinColumn(name = "USER_ID"))*/
	@JoinTable(name="user_infos_roles", joinColumns={@JoinColumn(referencedColumnName="id")}  
	, inverseJoinColumns={@JoinColumn(referencedColumnName="id")})
	private List<UserRole> userRoles;//1普通用户 2商家用户 3 其他用户
	
	@Column(nullable = false)//,unique=true
	@JsonIgnore
	private String userUid;//设备的唯一标识 不允许多设备登录
	@JsonIgnore
	@OneToMany(mappedBy= "userInfo",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<UserAuth> userList;
	@JsonIgnore
	private String userPassword; //密码凭证（站内的保存密码，站外的不保存或保存token）

	public Timestamp getUserResetDate() {
		return userResetDate;
	}
	public void setUserResetDate(Timestamp userResetDate) {
		this.userResetDate = userResetDate;
	}
	public List<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserSex() {
		return userSex;
	}
	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}
	public Timestamp getUserLoginDate() {
		return userLoginDate;
	}
	public void setUserLoginDate(Timestamp userLoginDate) {
		this.userLoginDate = userLoginDate;
	}
	public List<UserAuth> getUserList() {
		return userList;
	}
	public void setUserList(List<UserAuth> userList) {
		this.userList = userList;
	}

	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserUid() {
		return userUid;
	}
	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


}

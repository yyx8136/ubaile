package date.robr.springboot.user.domian;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import date.robr.springboot.base.domian.BaseDomian;


@Entity
@Table(name="user_role")
public class UserRole extends BaseDomian  implements Serializable{
	private static final long serialVersionUID = 1L;
/*	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;*/
	 
    @Id
	//@GeneratedValue
	//@Column(name="user_Id")
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
	protected String id;
	@Column(nullable = false)//,unique=true
	private String roleName;

	@JsonIgnore
	@ManyToMany(mappedBy="userRoles",cascade = CascadeType.ALL)
	private List<UserInfo> userInfos;
	public List<UserInfo> getUserInfos() {
		return userInfos;
	}
	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	//@ManyToMany(mappedBy = "userRole",cascade = CascadeType.ALL)
	//private UserInfo userInfo;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}

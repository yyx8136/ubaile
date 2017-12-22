package date.robr.springboot.user.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import date.robr.springboot.user.domian.UserAuth;


public interface UserAuthDao extends JpaRepository<UserAuth,String>{
	UserAuth findByUserIdeAndUserType(String userIde, Integer userType);
	@Query(value="SELECT auth FROM "
	        + "UserAuth auth,UserInfo info"
	        + " WHERE auth.userIde = ?1 and info.userPassword = ?2")
	UserAuth findByUserIdeAndUserPassword(String userIde, String userPassword);
	UserAuth findByUserIde(String userIde);
}

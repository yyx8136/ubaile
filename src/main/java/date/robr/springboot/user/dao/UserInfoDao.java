package date.robr.springboot.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import date.robr.springboot.user.domian.UserInfo;

public interface UserInfoDao extends JpaRepository<UserInfo,String>{

}

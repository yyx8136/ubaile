package date.robr.springboot.user.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import date.robr.springboot.user.domian.UserRole;

public interface UserRoleDao extends JpaRepository<UserRole,String>{
}

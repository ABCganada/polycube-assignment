package kr.co.polycube.backendtest.users.domain.repository;

import kr.co.polycube.backendtest.users.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

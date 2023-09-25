package org.yujin.myproc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yujin.myproc.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}

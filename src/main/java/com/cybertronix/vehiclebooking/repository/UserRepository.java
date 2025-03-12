package com.cybertronix.vehiclebooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.cybertronix.vehiclebooking.model.User;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long>{
    Boolean existsByUserName(String userName);

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserNameAndStatus(String userName, Integer status);

    User findByUserIdAndStatusIn(Long userId, List<Integer> values);

    List<User> findByRoleInAndStatusIn(List<Integer> roles, List<Integer> status);
}

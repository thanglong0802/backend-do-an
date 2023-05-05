package com.api.base.repository;

import com.api.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUsername(String userName);
    void deleteUserByUsername(String userName);
}

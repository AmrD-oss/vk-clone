package com.example.socialnetwork.repositories;

import com.example.socialnetwork.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @Override
    List<UserEntity> findAll();
}

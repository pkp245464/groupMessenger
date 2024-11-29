package com.group.Messenger.features.users.repository;

import com.group.Messenger.features.users.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByUserName(String username);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUserIdAndIsDeletedFalse(Long id);

    Optional<Users> findByUserNameAndIsDeletedFalse(String userName);
}

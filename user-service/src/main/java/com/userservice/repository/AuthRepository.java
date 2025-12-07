package com.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.model.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

	Optional<Auth> findByUsername(String username);

	Optional<Auth> findByToken(String refreshToken);

}

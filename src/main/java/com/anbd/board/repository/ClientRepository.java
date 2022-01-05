package com.anbd.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, String>{
	// 아이디 중복검사
	@Query("select c from ClientEntity c where c.client_id = :client_id")
	Optional<ClientEntity> checkId(@Param("client_id")String client_id);
	
	// 닉네임 중복검사
	@Query("select c from ClientEntity c where c.client_nickname = :client_nickname")
	Optional<ClientEntity> checkNickname(@Param("client_nickname")String client_nickname);
	
	// 이메일 중복검사(비밀번호 찾을 때)
	@Query("select c from ClientEntity c where c.client_email = :client_email")
	Optional<ClientEntity> checkEmail(@Param("client_email")String client_email);
}

package com.anbd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbd.board.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, String>{

}

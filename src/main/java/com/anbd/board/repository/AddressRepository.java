package com.anbd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbd.board.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer>{

}

package com.anbd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbd.board.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>{

}

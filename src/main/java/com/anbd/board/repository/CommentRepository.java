package com.anbd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbd.board.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

}

package com.anbd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbd.board.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer>{

}

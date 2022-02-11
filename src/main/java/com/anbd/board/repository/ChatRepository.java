package com.anbd.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer>{
	@Query("select c from ChatEntity c where  c.chat_send.client_id= :client_id or c.chat_receive.client_id = :client_id  GROUP BY c.chat")
	List<ChatEntity>findRoom(@Param("client_id")String client_id);

	@Query("select c from ChatEntity c where c.chat.product_no = :product_no and c.chat_send.client_id = :findSeller")
	List<ChatEntity>findchatlist(@Param("product_no")int product_no, @Param("findSeller")String findSeller) ;
	
	@Query("select c from ChatEntity c where c.chat.product_no = :product_no and (c.chat_send.client_id= :send_client_id or c.chat_receive.client_id = :receive_client_id)")
	List<ChatEntity>findSendAndReceive(@Param("product_no")int product_no, @Param("send_client_id")String send_client_id, @Param("receive_client_id")String receive_client_id);
}

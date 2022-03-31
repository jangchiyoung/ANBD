package com.anbd.board.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.CommentEntity;
import com.anbd.board.model.Comment;
import com.anbd.board.repository.CommentRepository;
import com.anbd.board.service.CommentService;
import com.anbd.board.model.Client;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.service.ClientService;

@Controller
public class CommentController {

	@Autowired
	CommentRepository repository;
	
	@Autowired
	CommentService service;
	
	@Autowired
	ClientRepository c_repository;
	
	@Autowired
	ClientService c_service;
	
	@RequestMapping("/anbd/comment_register")
	public String insert(Comment comment, Model model) {
		Comment comment_save = new Comment();
		
		LocalDateTime date = LocalDateTime.now();
		comment_save.setComment_no(comment.getComment_no());
		comment_save.setComment_board_no(comment.getComment_board_no());
		comment_save.setComment_client_id(comment.getComment_client_id());
		comment_save.setComment_content(comment.getComment_content());
		comment_save.setComment_date(date);
		CommentEntity entity = service.toEntity(comment_save);
		
		repository.save(entity);
		
		return "redirect:/anbd/board_detail?board_no="+comment.getComment_board_no();
	}
	   @RequestMapping(value = "/anbd/comment_modify", method =  RequestMethod.POST)
	   public String update(int comment_no, int comment_board_no, String comment_content, String comment_client_id, LocalDateTime comment_date, Model model) {
		  LocalDateTime date = LocalDateTime.now();
		  
		  CommentEntity update = repository.findComment(comment_no);
		  Comment comment = service.toDto(update);
		  
		  comment.setComment_content(comment_content);
		  comment.setComment_date(date);
		  update = service.toEntity(comment);
	      repository.save(update);
	      return "redirect:/anbd/board_detail?board_no="+comment.getComment_board_no();
	   }	
	
	   @RequestMapping("/anbd/comment_delete")
	   public String update(int comment_no) {
	      CommentEntity comment = repository.getById(comment_no);
	      int board_no = comment.getComment().getBoard_no();
	      repository.deleteById(comment_no);
	      return "redirect:/anbd/board_detail?board_no="+board_no;
	   }	
}



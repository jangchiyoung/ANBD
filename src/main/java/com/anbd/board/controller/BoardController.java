package com.anbd.board.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.Category_boardEntity;
import com.anbd.board.model.Board;
import com.anbd.board.model.Category_board;
import com.anbd.board.model.Client;
import com.anbd.board.repository.BoardRepository;
import com.anbd.board.repository.Category_boardRepository;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.service.BoardService;
import com.anbd.board.service.Category_boardService;
import com.anbd.board.service.ClientService;

@Controller
public class BoardController {
	
	@Autowired
	BoardRepository repository;
	
	@Autowired
	BoardService service;
	
	@Autowired
	Category_boardRepository c_repository;
	
	@Autowired
	Category_boardService c_service;
	
	@Autowired
	ClientRepository  client_repository;
	
	@Autowired
	ClientService client_service;
	// 전체게시물 리스트
		@RequestMapping(value="/anbd/board")
		public String getList(int no,Model model, HttpServletRequest request) {
			Client user = (Client) request.getAttribute("client");
			List<Client> clientlist = new ArrayList<Client>();
			List<Category_board> categoryList = new ArrayList<Category_board>();
			List<Category_boardEntity> Cate_entity =c_repository.findAll();
			for(Category_boardEntity temp: Cate_entity) {
				categoryList.add(c_service.toDto(temp));
			}
			List<Board> boardList = new ArrayList<Board>();
			
			List<BoardEntity> board_All_list_et = new ArrayList<BoardEntity>(); // 전체 게시글 리스트
			if(no==0) {
				board_All_list_et = repository.findAll();
			}else {
				int board_category_board_no = no;
				board_All_list_et = repository.Board_category(board_category_board_no);
			}
			for (BoardEntity temp : board_All_list_et) {
				if(temp.getBoard_status().equals("ing")) {
					boardList.add(service.toDto(temp));
				}
			}
				for(Board temp : boardList) {
					String users = temp.getBoard_client_id();
					clientlist.add(client_service.toDto(client_repository.findId(users)));
				}
			model.addAttribute("boards", boardList); // 게시물 리스트
			model.addAttribute("client", clientlist); // 글작성자 리스트
			model.addAttribute("category", categoryList); // 게시판 카테고리 리스트
			return "board";
		}	
	// 게시물 등록 페이지
	@RequestMapping(value = "/anbd/boardRegister") 
	public String boardRegister(Model model,HttpServletRequest request) {
		List<Category_board> category_name_list = new ArrayList<Category_board>();
		List<Category_boardEntity> category_All_list_et = c_repository.findAll();
			for (Category_boardEntity temp : category_All_list_et) {
				category_name_list.add(c_service.toDto(temp));
			}
			model.addAttribute("items", category_name_list);
		return "boardRegister";
	}
		
	// 게시물 등록
	@RequestMapping(value = "/anbd/board_register", method =  RequestMethod.POST)
	public String boardRegister(String board_client_id, Integer board_category_no, String board_name, String board_content, String board_address,
			@RequestParam MultipartFile board_img1, @RequestParam MultipartFile board_img2, @RequestParam MultipartFile board_img3) 
					throws IllegalStateException, IOException {

		String randomimg1 = null,randomimg2 = null,randomimg3 = null;
			
		if (!board_img1.isEmpty()) {
			String originalFile1  = board_img1.getOriginalFilename();
			String originalFileExtension1 = originalFile1.substring(originalFile1.lastIndexOf("."));
			randomimg1 = UUID.randomUUID().toString() + originalFileExtension1;
		}
		if (!board_img2.isEmpty()) {
			String originalFile2  = board_img2.getOriginalFilename();
			String originalFileExtension2 = originalFile2.substring(originalFile2.lastIndexOf("."));
			randomimg2 = UUID.randomUUID().toString() + originalFileExtension2;
		}
		if (!board_img3.isEmpty()) {
			String originalFile3  = board_img3.getOriginalFilename();
			String originalFileExtension3 = originalFile3.substring(originalFile3.lastIndexOf("."));
			randomimg3 = UUID.randomUUID().toString() + originalFileExtension3;
		}
		LocalDateTime date = LocalDateTime.now();
		Board board = new Board(0, board_client_id, board_category_no, randomimg1, randomimg2, randomimg3,board_name, board_content,
				  "ing", date,0, 0, board_address);
		String path = "C:\\img";
		File upfile = null;
		if (randomimg1 != null) {
			String img = path + "\\" + randomimg1;
			upfile = new File(img);
			board_img1.transferTo(upfile);
		}
		if (randomimg2 != null) {
			String img = path + "\\" + randomimg2;
			upfile = new File(img);
			board_img2.transferTo(upfile);
		}
		if (randomimg3 != null) {
			String img = path + "\\" + randomimg3;
			upfile = new File(img);
			board_img3.transferTo(upfile);
		}

		BoardEntity entity = service.toEntity(board);
		repository.save(entity);
		return "redirect:main";
	}
		
		
		
}

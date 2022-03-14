package com.anbd.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.print.attribute.standard.MediaSize.ISO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.CategoryEntity;
import com.anbd.board.entity.Category_boardEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.HeartEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Board;
import com.anbd.board.model.Category;
import com.anbd.board.model.Category_board;
import com.anbd.board.model.Client;
import com.anbd.board.model.Heart;
import com.anbd.board.model.Product;
import com.anbd.board.repository.BoardRepository;
import com.anbd.board.repository.Category_boardRepository;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.repository.HeartRepository;
import com.anbd.board.service.BoardService;
import com.anbd.board.service.Category_boardService;
import com.anbd.board.service.ClientService;
import com.anbd.board.service.HeartService;

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
	
	@Autowired
	HeartRepository h_repository;
	
	@Autowired
	HeartService h_service;
		// 전체게시물 리스트
		@RequestMapping(value="/anbd/board")
		public String getList(int no, String ad,Model model, HttpServletRequest request) {
			Client user = (Client) request.getAttribute("client");
			String board_address = ad;
			List<Client> clientlist = new ArrayList<Client>();
			List<Category_board> categoryList = new ArrayList<Category_board>();
			List<Category_boardEntity> Cate_entity =c_repository.findAll();
			for(Category_boardEntity temp: Cate_entity) {
				categoryList.add(c_service.toDto(temp));
			}
			List<Board> boardList = new ArrayList<Board>();
			
			List<BoardEntity> board_All_list_et = new ArrayList<BoardEntity>(); // 전체 게시글 리스트
			if(no==0) {
				board_All_list_et = repository.BoardAll(board_address);
			}else {
				int board_category_board_no = no;
				board_All_list_et = repository.Board_category(board_category_board_no, board_address);
			}
			for (BoardEntity temp : board_All_list_et) {
				if(temp.getBoard_status().equals("ing")) {
					boardList.add(service.toDto(temp));
				}
			}
				for(Board temp : boardList) {
					String users = temp.getBoard_writer_client_id();
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
	
	// 게시물 수정페이지
	@RequestMapping(value = "/anbd/boardModify") 
	public String boardModify(int board_no,Model model,HttpServletRequest request) {
		List<Category_board> category_name_list = new ArrayList<Category_board>();
		List<Category_boardEntity> category_All_list_et = c_repository.findAll();
			for (Category_boardEntity temp : category_All_list_et) {
				category_name_list.add(c_service.toDto(temp));
			}
		BoardEntity entity = repository.boardDetail(board_no);	
		model.addAttribute("category", category_name_list);
		model.addAttribute("board", entity);
		return "boardModify";
	}	
	
	// 게시물 등록
	@RequestMapping(value = "/anbd/board_modify", method =  RequestMethod.POST)
	public String board_modify(int board_no, String board_client_id, Integer board_category_board_no, String board_name, String board_content, String board_address,
			@RequestParam MultipartFile board_img1, @RequestParam MultipartFile board_img2, @RequestParam MultipartFile board_img3) 
					throws IllegalStateException, IOException {
		BoardEntity entity = repository.findById(board_no).get();
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
		LocalDateTime date = LocalDateTime.now();
		
		BoardEntity update = repository.boardDetail(board_no);
		Board board =service.toDto(update);
		
		board.setBoard_category_no(board_category_board_no);
		board.setBoard_name(board_name);
		board.setBoard_content(board_content);
		board.setBoard_date(date);
		board.setBoard_address(board_address);
		
		if(randomimg1 != null) board.setBoard_img1(randomimg1);
		if(randomimg2 != null) board.setBoard_img2(randomimg2);
		if(randomimg3 != null) board.setBoard_img3(randomimg3);
		
		entity = service.toEntity(board);
		repository.save(entity);
		String no ="0";
		String ad= board.getBoard_address().substring(0,6);
		ad = URLEncoder.encode(ad, "UTF-8");
		return "redirect:board?no=" +no +"&ad=" +ad;
	}
	
	//게시물 삭제
	@RequestMapping("/anbd/boardDelete")
	public String delete(int board_no, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Client user = (Client) session.getAttribute("client");
		repository.deleteById(board_no);
		return "redirect:myboard" + "?client_id=" + user.getClient_id();
	}	
	//게시물 디테일 페이지
		@RequestMapping(value = "/anbd/board_detail", method = RequestMethod.GET)
		public String board_detail(int board_no, Model model, HttpServletRequest request,
				 HttpServletResponse response) {
			BoardEntity entity = repository.boardDetail(board_no);
			repository.updateReadCount(board_no); //조회수 증가
			Board board = service.toDto(entity);
			String board_writer =board.getBoard_writer_client_id();
			List<BoardEntity> writer = repository.findWriter(board_writer);
			HttpSession session = request.getSession();
			Client user = (Client) session.getAttribute("client");
			String client_id = user.getClient_id();
			ClientEntity c_entity = client_repository.findId(board_writer);
			HeartEntity h_entity = h_repository.findById(client_id,board_no);
			
			model.addAttribute("writer_list",writer);
			model.addAttribute("client",c_entity);
			model.addAttribute("heart",h_entity);
			model.addAttribute("board",entity);
			return "board_detail";
		}
		@SuppressWarnings("unchecked")
		@ResponseBody
		@RequestMapping(value = "/anbd/heart", method = RequestMethod.POST)
		public JSONObject heart(@RequestBody JSONObject no, HttpServletRequest request) {
			String temp = (String) no.get("no");
			int board_no = Integer.parseInt(temp);
			List<Heart> heart_list = new ArrayList<Heart>();
			List<HeartEntity> h_entity = h_repository.findByProductNo(board_no);
			for (HeartEntity h : h_entity)
				heart_list.add(h_service.toDto(h));

			Client user = (Client) request.getSession().getAttribute("client");

			int heart_cnt = service.toDto(repository.getById(board_no)).getBoard_like();
			boolean check = true;
			Heart test = null;

			for (Heart h : heart_list) {
				test = h;
				if (h.getHeart_client_id().equals(user.getClient_id())) {
					check = false; // 값 추가
					break;
				}
			}
			System.out.println(check);
			if (check) {
				// 토글
				h_repository.save(h_service.toEntity(Heart.builder().heart_board_no(board_no)
						.heart_client_id(user.getClient_id()).heart_no(0).build()));
				repository.updateLike(board_no);
				heart_cnt++;
			} else {
				h_repository.delete(h_service.toEntity(test));
				repository.updateLike(board_no);
				heart_cnt--;
			}

			JSONObject data = new JSONObject();
			data.put("idx", temp);
			data.put("heart", String.valueOf(heart_cnt));
			return data;
		}		
		
}

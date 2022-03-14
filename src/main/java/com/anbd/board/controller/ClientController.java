package com.anbd.board.controller;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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

import com.anbd.board.Hash;
import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.FavoritesEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Board;
import com.anbd.board.model.Client;
import com.anbd.board.model.Favorites;
import com.anbd.board.model.Product;
import com.anbd.board.repository.BoardRepository;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.repository.FavoritesRepository;
import com.anbd.board.repository.ProductRepository;
import com.anbd.board.service.BoardService;
import com.anbd.board.service.ClientService;
import com.anbd.board.service.FavoritesService;
import com.anbd.board.service.ProductService;

@Controller
public class ClientController {

	@Autowired
	ClientRepository repository;
	
	@Autowired
	ClientService service;
	
	@Autowired
	ProductRepository p_repository;
	
	@Autowired
	ProductService p_service;
	
	@Autowired
	FavoritesRepository f_repository;
	
	@Autowired
	FavoritesService f_service;
	
	@Autowired
	BoardRepository b_repository;
	
	@Autowired
	BoardService b_service;
	
	// 마이페이지
	@RequestMapping(value = "/anbd/mypage", method = RequestMethod.GET )
	public String mypage(String client_id, HttpServletRequest request, Model model) {
		ClientEntity user = repository.findId(client_id);
		String end = "end";
		String ing = "ing";
		List<Product> product_ing_list = new ArrayList<Product>();
		List<ProductEntity> seller = p_repository.findSellerID(client_id);
		for (ProductEntity temp : seller) {
			if(temp.getProduct_status().equals("ing")) {
				product_ing_list.add(p_service.toDto(temp));
			}
		}
		Integer p_cnt  = p_repository.ProductCnt(client_id,ing);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		Integer s_cnt  = p_repository.SalesCnt(client_id,end);
		Integer b_cnt  = p_repository.PurchaseCnt(client_id,end);
		Integer board_cnt  = b_repository.BoardCnt(client_id,ing);
		
		model.addAttribute("seller_list",product_ing_list);
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("board_cnt",board_cnt);
		model.addAttribute("s_cnt",s_cnt);
		model.addAttribute("b_cnt",b_cnt);
		model.addAttribute("f_cnt",f_cnt);
		model.addAttribute("client",user);
		return "mypage";
	}
	
	// 내 게시물 관리페이지
	@RequestMapping(value = "/anbd/myboard", method = RequestMethod.GET )
	public String myboard(String client_id, HttpServletRequest request, Model model) {
		ClientEntity user = repository.findId(client_id);
		String end = "end";
		String ing = "ing";
		List<Board> board_ing_list = new ArrayList<Board>();
		List<BoardEntity> writer = b_repository.findWriter(client_id);
		for (BoardEntity temp : writer) {
				board_ing_list.add(b_service.toDto(temp));
		}
		Integer p_cnt  = p_repository.ProductCnt(client_id,ing);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		Integer s_cnt  = p_repository.SalesCnt(client_id,end);
		Integer b_cnt  = p_repository.PurchaseCnt(client_id,end);
		Integer board_cnt  = b_repository.BoardCnt(client_id,ing);
		
		model.addAttribute("writer_list",board_ing_list);
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("board_cnt",board_cnt);
		model.addAttribute("s_cnt",s_cnt);
		model.addAttribute("b_cnt",b_cnt);
		model.addAttribute("f_cnt",f_cnt);
		model.addAttribute("client",user);
		return "myboard";
	}
	
	// 유저 페이지
	@RequestMapping(value = "/anbd/userpage")
	public String userpage(String client_id, HttpServletRequest request, Model model) {
		ClientEntity user = repository.findId(client_id);
		String product_status = "ing";
		List<ProductEntity> seller = p_repository.findSellerID(client_id);
		Integer p_cnt  = p_repository.ProductCnt(client_id,product_status);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		
		model.addAttribute("seller_list",seller);
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("f_cnt",f_cnt);
		model.addAttribute("client",user);
		return "userpage";
	}
	
	@RequestMapping(value = "/anbd/favoritesList")
	public String favoritesList(String client_id, HttpServletRequest request, Model model) {
		String product_status = "end";
		String product_ing = "ing";
		ClientEntity user = repository.findId(client_id);
		List<FavoritesEntity> favoritesID = f_repository.findFavoritesID(client_id);
		List<Favorites> result = new ArrayList<Favorites>();
		favoritesID.forEach(item-> {
			result.add(f_service.toDto(item));
		});
		
	      List<Product> ProductList = new ArrayList<Product>();
	      for(Favorites temp : result) {
	    	  ProductList.add(p_service.toDto(p_repository.getById(temp.getFavorites_product_no())));
	      };
		
	    Integer p_cnt  = p_repository.ProductCnt(client_id,product_ing);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		Integer s_cnt  = p_repository.SalesCnt(client_id,product_status);
		Integer b_cnt  = p_repository.PurchaseCnt(client_id,product_status);
		
		model.addAttribute("favorites_list",favoritesID);
		model.addAttribute("product_list",ProductList);
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("f_cnt",f_cnt);
		model.addAttribute("s_cnt",s_cnt);
		model.addAttribute("b_cnt",b_cnt);
		model.addAttribute("client",user);
		return "favoritesList";
	}
	//관심상품 삭제
	@RequestMapping("/anbd/favoritesDelete")
	public String delete(int favorites_no, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Client user = (Client) session.getAttribute("client");
		f_repository.deleteById(favorites_no);
		return "redirect:favoritesList" + "?client_id=" + user.getClient_id();
	}
	//판매내역 페이지
	@RequestMapping(value = "/anbd/salesList")
	public String salesList(String client_id, HttpServletRequest request, Model model) {
		ClientEntity user = repository.findId(client_id);
		String product_status = "end";
		String product_ing = "ing";
		List<Product> prodcut_list = new ArrayList<Product>();
		List<ProductEntity> productID = p_repository.findsalesList(client_id,product_status);
			for (ProductEntity temp : productID) {
				if(temp.getProduct_status().equals("end")) {
					prodcut_list.add(p_service.toDto(temp));
				}
			}
	    Integer p_cnt  = p_repository.ProductCnt(client_id,product_ing);
	    Integer s_cnt  = p_repository.SalesCnt(client_id, product_status);
	    Integer b_cnt  = p_repository.PurchaseCnt(client_id, product_status);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		
		model.addAttribute("product_list",prodcut_list);
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("f_cnt",f_cnt);
		model.addAttribute("s_cnt",s_cnt);
		model.addAttribute("b_cnt",b_cnt);
		model.addAttribute("client",user);
		return "salesList";
	}
	
	//구매내역 페이지
		@RequestMapping(value = "/anbd/purchaseList")
		public String purchaseList(String client_id, HttpServletRequest request, Model model) {
			ClientEntity user = repository.findId(client_id);
			String product_status = "end";
			String product_ing = "ing";
			List<Product> prodcut_list = new ArrayList<Product>();
			List<ProductEntity> productID = p_repository.findpurchaseList(client_id,product_status);
				for (ProductEntity temp : productID) {
					prodcut_list.add(p_service.toDto(temp));
				}
		    Integer p_cnt  = p_repository.ProductCnt(client_id,product_ing);
		    Integer s_cnt  = p_repository.SalesCnt(client_id, product_status);
			Integer b_cnt  = p_repository.PurchaseCnt(client_id, product_status);
			Integer f_cnt  = f_repository.FavoritesCnt(client_id);
			
			model.addAttribute("product_list",prodcut_list);
			model.addAttribute("p_cnt",p_cnt);
			model.addAttribute("f_cnt",f_cnt);
			model.addAttribute("s_cnt",s_cnt);
			model.addAttribute("b_cnt",b_cnt);
			model.addAttribute("client",user);
			return "purchaseList";
		}
	//개인정보 비밀번호 체크
	@RequestMapping("/anbd/modify")
	public String pwCheck(String client_id, HttpServletRequest request, Model model) {
		ClientEntity user = repository.findId(client_id);
		String product_status = "end";
		List<ProductEntity> seller = p_repository.findSellerID(client_id);
		Integer p_cnt  = p_repository.ProductCnt(client_id,product_status);
		Integer s_cnt  = p_repository.SalesCnt(client_id, product_status);
		Integer b_cnt  = p_repository.PurchaseCnt(client_id, product_status);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		
		model.addAttribute("seller_list",seller);
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("f_cnt",f_cnt);
		model.addAttribute("s_cnt",s_cnt);
		model.addAttribute("b_cnt",b_cnt);
		model.addAttribute("client",user);
		return "modify";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/modifyName", method = RequestMethod.POST)
	public JSONObject modifyName(@RequestBody JSONObject name, HttpServletRequest request) {
		
		String temp = (String) name.get("name");
		System.out.println(temp);
		Client user = (Client) request.getSession().getAttribute("client");

		repository.updateName(temp, user.getClient_id());
		JSONObject data = new JSONObject();
		data.put("client_name", temp);
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/modifyTel", method = RequestMethod.POST)
	public JSONObject modifyTel(@RequestBody JSONObject tel, HttpServletRequest request) {
		
		String temp = (String) tel.get("tel");
		System.out.println(temp);
		Client user = (Client) request.getSession().getAttribute("client");

		repository.updateTel(temp, user.getClient_id());
		JSONObject data = new JSONObject();
		data.put("client_tel", temp);
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/modifyEmail", method = RequestMethod.POST)
	public JSONObject modifyEmail(@RequestBody JSONObject email, HttpServletRequest request) {
		
		String temp = (String) email.get("email");
		System.out.println(temp);
		Client user = (Client) request.getSession().getAttribute("client");

		repository.updateEmail(temp, user.getClient_id());
		JSONObject data = new JSONObject();
		data.put("client_email", temp);
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/modifyNickname", method = RequestMethod.POST)
	public JSONObject modifyNickname(@RequestBody JSONObject nickname, HttpServletRequest request) {
		
		String temp = (String) nickname.get("nickname");
		System.out.println(temp);
		Client user = (Client) request.getSession().getAttribute("client");
		
		repository.updateNickname(temp, user.getClient_id());
		JSONObject data = new JSONObject();
		data.put("client_nickname", temp);
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/modifyAddress", method = RequestMethod.POST)
	public JSONObject modifyAddress(@RequestBody JSONObject address, HttpServletRequest request) {
		
		String temp = (String) address.get("address");
		System.out.println(temp);
		Client user = (Client) request.getSession().getAttribute("client");

		repository.updateAddress(temp, user.getClient_id());
		JSONObject data = new JSONObject();
		data.put("client_address", temp);
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/checkPassword", method = RequestMethod.POST)
	public JSONObject checkPassword(@RequestBody JSONObject password, HttpServletRequest request) throws NoSuchAlgorithmException {
		String temp = (String) password.get("password");
		String oldpassword = null;
		Hash hash = new Hash();
		oldpassword = hash.hashtest(temp);
		Client user = (Client) request.getSession().getAttribute("client");
		JSONObject result = new JSONObject();
		if(oldpassword.equals(user.getClient_password())) {
			result.put("data", 0);
		} else {
			result.put("data", 27);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/modifyPassword", method = RequestMethod.POST)
	public JSONObject modifyPassword(@RequestBody JSONObject new_password, HttpServletRequest request) throws NoSuchAlgorithmException {
		String temp = (String) new_password.get("new_password");
		String newpassword = null;
		Hash hash = new Hash();
		newpassword = hash.hashtest(temp);
		Client user = (Client) request.getSession().getAttribute("client");

		repository.updatePassword(newpassword, user.getClient_id());
		JSONObject data = new JSONObject();
		data.put("client_password", temp);
		return data;
	}
	
	@RequestMapping(value = "/anbd/imgModify", method = RequestMethod.POST)
	public String updateimg(@RequestParam MultipartFile client_img, String client_id, HttpSession session)
			throws IllegalStateException, IOException {
		ClientEntity entity = repository.findById(client_id).get();
		Client user = service.toDto(entity);
		Client login = (Client) session.getAttribute("client");

		if (login == null) {
			return "redirect:/anbd/main";
		}
		String randomimg = null;
		randomimg = UUID.randomUUID().toString() + client_img.getOriginalFilename();
		String path = "C:\\img";
		File upfile = null;
		if (randomimg != null) {
			String img = path + "\\" + randomimg;
			upfile = new File(img);
			client_img.transferTo(upfile);
		}

		user.setClient_img(randomimg);
		entity = service.toEntity(user);
		entity = repository.save(entity);

		session.removeAttribute("client");
		session.setAttribute("client", service.toDto(entity));

		return "redirect:modify?client_id=" + client_id;

	}
}

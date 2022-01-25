package com.anbd.board.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.FavoritesEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Client;
import com.anbd.board.model.Favorites;
import com.anbd.board.model.Product;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.repository.FavoritesRepository;
import com.anbd.board.repository.ProductRepository;
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
	// 마이페이지
	@RequestMapping(value = "/anbd/mypage")
	public String mypage(String client_id, HttpServletRequest request, Model model) {
		ClientEntity user = repository.findId(client_id);
		List<ProductEntity> seller = p_repository.findSellerID(client_id);
		Integer p_cnt  = p_repository.ProductCnt(client_id);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		
		model.addAttribute("seller_list",seller);
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("f_cnt",f_cnt);
		model.addAttribute("client",user);
		return "mypage";
	}
	@RequestMapping(value = "/anbd/userpage")
	public String userpage(String client_id, HttpServletRequest request, Model model) {
		ClientEntity user = repository.findId(client_id);
		List<ProductEntity> seller = p_repository.findSellerID(client_id);
		Integer p_cnt  = p_repository.ProductCnt(client_id);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		
		model.addAttribute("seller_list",seller);
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("f_cnt",f_cnt);
		model.addAttribute("client",user);
		return "userpage";
	}
	
	@RequestMapping(value = "/anbd/favoritesList")
	public String favoritesList(String client_id, HttpServletRequest request, Model model) {
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
		
	    Integer p_cnt  = p_repository.ProductCnt(client_id);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		
		model.addAttribute("favorites_list",favoritesID);
		model.addAttribute("product_list",ProductList);
		System.out.println(favoritesID);
		System.out.println(ProductList);
		
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("f_cnt",f_cnt);
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
}

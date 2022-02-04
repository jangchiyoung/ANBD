package com.anbd.board.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anbd.board.Hash;
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
	@RequestMapping(value = "/anbd/mypage", method = RequestMethod.GET )
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
	//개인정보 비밀번호 체크
	@RequestMapping("/anbd/pwCheck")
	public String pwCheck(String client_id, HttpServletRequest request, Model model) {
		ClientEntity user = repository.findId(client_id);
		List<ProductEntity> seller = p_repository.findSellerID(client_id);
		Integer p_cnt  = p_repository.ProductCnt(client_id);
		Integer f_cnt  = f_repository.FavoritesCnt(client_id);
		
		model.addAttribute("seller_list",seller);
		model.addAttribute("p_cnt",p_cnt);
		model.addAttribute("f_cnt",f_cnt);
		model.addAttribute("client",user);
		return "pwCheck";
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
}

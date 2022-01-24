package com.anbd.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Client;
import com.anbd.board.model.Product;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.repository.ProductRepository;
import com.anbd.board.service.ClientService;
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
	
	// 마이페이지
	@RequestMapping(value = "/anbd/mypage")
	public String mypage(String client_id, HttpServletRequest request, Model model) {
		ClientEntity user = repository.findId(client_id);
		List<ProductEntity> seller = p_repository.findSellerID(client_id);
		Integer cnt  = p_repository.ProductCnt(client_id);
		System.out.println(seller);
		
		model.addAttribute("seller_list",seller);
		model.addAttribute("cnt",cnt);
		model.addAttribute("client",user);
		return "mypage";
	}
}

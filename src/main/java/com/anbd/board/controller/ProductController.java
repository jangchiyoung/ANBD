package com.anbd.board.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
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

import com.anbd.board.entity.CategoryEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.FavoritesEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Category;
import com.anbd.board.model.Client;
import com.anbd.board.model.Favorites;
import com.anbd.board.model.Product;
import com.anbd.board.repository.CategoryRepository;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.repository.FavoritesRepository;
import com.anbd.board.repository.ProductRepository;
import com.anbd.board.service.CategoryService;
import com.anbd.board.service.ClientService;
import com.anbd.board.service.FavoritesService;
import com.anbd.board.service.ProductService;


@Controller
public class ProductController {
	
	@Autowired
	ProductRepository repository;
	
	@Autowired
	ProductService service;
	
	@Autowired
	CategoryRepository c_repository;
	
	@Autowired
	CategoryService c_service;
	
	@Autowired
	ClientRepository client_repository;
	
	@Autowired
	ClientService client_service;
	
	@Autowired
	FavoritesRepository f_repository;
	@Autowired
	FavoritesService f_service;
	// 전체게시물 리스트
	@RequestMapping({ "/anbd/main", "/anbd/", "/","/anbd" })
	public String getList(Model model, HttpServletRequest request,String search_name) {
		Client user = (Client) request.getSession().getAttribute("client");
		List<Product> product_all_list = new ArrayList<Product>();
		List<ProductEntity> product_All_list_et = repository.ProductAll(); // 전체 게시글 리스트
		// 전체 게시글 리스트
				for (ProductEntity temp : product_All_list_et) {
					product_all_list.add(service.toDto(temp));
				}
		model.addAttribute("items", product_all_list); // 전체 회원 리스트
		return "main";
	}
	// 게시물 등록 페이지
	@RequestMapping(value = "/anbd/productRegister") 
	public String productRegister(Model model,HttpServletRequest request) {
		List<Category> category_name_list = new ArrayList<Category>();
		List<CategoryEntity> category_All_list_et = c_repository.findAll();
			for (CategoryEntity temp : category_All_list_et) {
				category_name_list.add(c_service.toDto(temp));
			}
			model.addAttribute("items", category_name_list);
		return "productRegister";
	}
	// 게시물 등록
	@RequestMapping(value = "/anbd/product_register", method =  RequestMethod.POST)
	public String productRegister(String product_seller_id, Integer product_category_no, String product_name, String product_content, int product_price,
			@RequestParam MultipartFile product_img1, @RequestParam MultipartFile product_img2, @RequestParam MultipartFile product_img3,
			@RequestParam MultipartFile product_img4, @RequestParam MultipartFile product_img5) 
					throws IllegalStateException, IOException {

		String randomimg1 = null,randomimg2 = null,randomimg3 = null,randomimg4 = null,randomimg5 = null;
		
		if (!product_img1.isEmpty()) {
			String originalFile1  = product_img1.getOriginalFilename();
			String originalFileExtension1 = originalFile1.substring(originalFile1.lastIndexOf("."));
			randomimg1 = UUID.randomUUID().toString() + originalFileExtension1;
		}
		if (!product_img2.isEmpty()) {
			String originalFile2  = product_img2.getOriginalFilename();
			String originalFileExtension2 = originalFile2.substring(originalFile2.lastIndexOf("."));
			randomimg2 = UUID.randomUUID().toString() + originalFileExtension2;
		}
		if (!product_img3.isEmpty()) {
			String originalFile3  = product_img3.getOriginalFilename();
			String originalFileExtension3 = originalFile3.substring(originalFile3.lastIndexOf("."));
			randomimg3 = UUID.randomUUID().toString() + originalFileExtension3;
		}
		if (!product_img4.isEmpty()) {
			String originalFile4  = product_img4.getOriginalFilename();
			String originalFileExtension4 = originalFile4.substring(originalFile4.lastIndexOf("."));
			randomimg4 = UUID.randomUUID().toString() + originalFileExtension4;
		}
		if (!product_img5.isEmpty()) {
			String originalFile5  = product_img5.getOriginalFilename();
			String originalFileExtension5 = originalFile5.substring(originalFile5.lastIndexOf("."));
			randomimg5 = UUID.randomUUID().toString() + originalFileExtension5;
		}
		LocalDateTime date = LocalDateTime.now();
		Product product = new Product(0, product_category_no, product_name, product_content,
				product_price, randomimg1, randomimg2, randomimg3, randomimg4, randomimg5, product_seller_id, null, 0, 0, "ing", date, null);
		String path = "C:\\img";
		File upfile = null;
		if (randomimg1 != null) {
			String img = path + "\\" + randomimg1;
			upfile = new File(img);
			product_img1.transferTo(upfile);
		}
		if (randomimg2 != null) {
			String img = path + "\\" + randomimg2;
			upfile = new File(img);
			product_img2.transferTo(upfile);
		}
		if (randomimg3 != null) {
			String img = path + "\\" + randomimg3;
			upfile = new File(img);
			product_img3.transferTo(upfile);
		}
		if (randomimg4 != null) {
			String img = path + "\\" + randomimg4;
			upfile = new File(img);
			product_img4.transferTo(upfile);
		}
		if (randomimg5 != null) {
			String img = path + "\\" + randomimg5;
			upfile = new File(img);
			product_img5.transferTo(upfile);
		}

		ProductEntity entity = service.toEntity(product);
		repository.save(entity);
		return "redirect:main";
	}
	
	// 게시물 수정페이지
	@RequestMapping(value = "/anbd/productModify") 
	public String productModify(int product_no,Model model,HttpServletRequest request) {
		List<Category> category_name_list = new ArrayList<Category>();
		List<CategoryEntity> category_All_list_et = c_repository.findAll();
			for (CategoryEntity temp : category_All_list_et) {
				category_name_list.add(c_service.toDto(temp));
			}
		ProductEntity entity = repository.ProductDetail(product_no);	
		System.out.println(entity);
		System.out.println(category_name_list);
		model.addAttribute("category", category_name_list);
		model.addAttribute("product", entity);
		return "productModify";
	}
	
	// 게시물 수정하기
	@RequestMapping(value = "/anbd/product_modify", method = RequestMethod.POST)
	public String product_modify(int product_no, String product_seller_id, int product_category_no, String product_name, String product_content, int product_price,
			@RequestParam MultipartFile product_img1, @RequestParam MultipartFile product_img2, @RequestParam MultipartFile product_img3,
			@RequestParam MultipartFile product_img4, @RequestParam MultipartFile product_img5) 
					throws IllegalStateException, IOException {
		
		ProductEntity entity = repository.findById(product_no).get();
		String randomimg1 = null,randomimg2 = null,randomimg3 = null,randomimg4 = null,randomimg5 = null;
		
		if (!product_img1.isEmpty()) {
			String originalFile1  = product_img1.getOriginalFilename();
			String originalFileExtension1 = originalFile1.substring(originalFile1.lastIndexOf("."));
			randomimg1 = UUID.randomUUID().toString() + originalFileExtension1;
		}
		if (!product_img2.isEmpty()) {
			String originalFile2  = product_img2.getOriginalFilename();
			String originalFileExtension2 = originalFile2.substring(originalFile2.lastIndexOf("."));
			randomimg2 = UUID.randomUUID().toString() + originalFileExtension2;
		}
		if (!product_img3.isEmpty()) {
			String originalFile3  = product_img3.getOriginalFilename();
			String originalFileExtension3 = originalFile3.substring(originalFile3.lastIndexOf("."));
			randomimg3 = UUID.randomUUID().toString() + originalFileExtension3;
		}
		if (!product_img4.isEmpty()) {
			String originalFile4  = product_img4.getOriginalFilename();
			String originalFileExtension4 = originalFile4.substring(originalFile4.lastIndexOf("."));
			randomimg4 = UUID.randomUUID().toString() + originalFileExtension4;
		}
		if (!product_img5.isEmpty()) {
			String originalFile5  = product_img5.getOriginalFilename();
			String originalFileExtension5 = originalFile5.substring(originalFile5.lastIndexOf("."));
			randomimg5 = UUID.randomUUID().toString() + originalFileExtension5;
		}
		
		String path = "C:\\img";
		File upfile = null;
		if (randomimg1 != null) {
			String img = path + "\\" + randomimg1;
			upfile = new File(img);
			product_img1.transferTo(upfile);
		}
		if (randomimg2 != null) {
			String img = path + "\\" + randomimg2;
			upfile = new File(img);
			product_img2.transferTo(upfile);
		}
		if (randomimg3 != null) {
			String img = path + "\\" + randomimg3;
			upfile = new File(img);
			product_img3.transferTo(upfile);
		}
		if (randomimg4 != null) {
			String img = path + "\\" + randomimg4;
			upfile = new File(img);
			product_img4.transferTo(upfile);
		}
		if (randomimg5 != null) {
			String img = path + "\\" + randomimg5;
			upfile = new File(img);
			product_img5.transferTo(upfile);
		}
		LocalDateTime date = LocalDateTime.now();
		ProductEntity update = repository.ProductDetail(product_no);
		Product product = service.toDto(update);
		
		product.setProduct_category_no(product_category_no);
		product.setProduct_content(product_content);
		product.setProduct_name(product_name);
		product.setProduct_price(product_price);
		product.setProduct_date(date);
		
		if(randomimg1 != null) product.setProduct_img1(randomimg1);
		if(randomimg2 != null) product.setProduct_img2(randomimg2);
		if(randomimg3 != null) product.setProduct_img3(randomimg3);
		if(randomimg4 != null) product.setProduct_img4(randomimg4);
		if(randomimg5 != null) product.setProduct_img5(randomimg5);
		
		entity = service.toEntity(product);
		repository.save(entity);
		return "redirect:main";
	}
	//게시물 삭제
	@RequestMapping("/anbd/productDelete")
	public String delete(int product_no, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Client user = (Client) session.getAttribute("client");
		repository.deleteById(product_no);
		return "redirect:mypage" + "?client_id=" + user.getClient_id();
	}
	//게시물 디테일 페이지
	@RequestMapping(value = "/anbd/product_detail", method = RequestMethod.GET)
	public String prodouct_detail(int product_no, Model model, HttpServletRequest request) {
		ProductEntity entity = repository.ProductDetail(product_no);
		Product product = service.toDto(entity);
		String seller_client_id =product.getProduct_seller_client_id();
		List<ProductEntity> seller = repository.findSellerID(seller_client_id);
		HttpSession session = request.getSession();
		Client user = (Client) session.getAttribute("client");
		String client_id = user.getClient_id();
		ClientEntity c_entity = client_repository.findId(seller_client_id);
		FavoritesEntity f_entity = f_repository.findById(product_no,client_id);
		
		model.addAttribute("seller_list",seller);
		model.addAttribute("client",c_entity);
		model.addAttribute("favorites",f_entity);
		model.addAttribute("product",entity);
		return "product_detail";
	}
	
	@ResponseBody
	@RequestMapping(value = "/anbd/favorites", method = RequestMethod.POST)
	public JSONObject heart(@RequestBody JSONObject no, HttpServletRequest request) {
		String temp = (String) no.get("no");
		int product_no = Integer.parseInt(temp);
		List<Favorites> favorites_list = new ArrayList<Favorites>();
		List<FavoritesEntity> f_entity = f_repository.findByProductNo(product_no);
		for (FavoritesEntity f : f_entity)
			favorites_list.add(f_service.toDto(f));

		Client user = (Client) request.getSession().getAttribute("client");

		int favorites_cnt = service.toDto(repository.getById(product_no)).getProduct_like();
		boolean check = true;
		Favorites test = null;

		for (Favorites h : favorites_list) {
			test = h;
			if (h.getFavorites_client_id().equals(user.getClient_id())) {
				check = false; // 값 추가
				break;
			}
		}
		System.out.println(check);
		if (check) {
			// 토글
			f_repository.save(f_service.toEntity(Favorites.builder().favorites_product_no(product_no)
					.favorites_client_id(user.getClient_id()).favorites_no(0).build()));
			repository.updateLike(product_no);
			favorites_cnt++;
		} else {
			f_repository.delete(f_service.toEntity(test));
			repository.updateLike(product_no);
			favorites_cnt--;
		}

		JSONObject data = new JSONObject();
		data.put("idx", temp);
		data.put("favorites", String.valueOf(favorites_cnt));
		return data;
	}
	
}


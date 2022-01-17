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

import com.anbd.board.entity.CategoryEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Category;
import com.anbd.board.model.Client;
import com.anbd.board.model.Product;
import com.anbd.board.repository.CategoryRepository;
import com.anbd.board.repository.ProductRepository;
import com.anbd.board.service.CategoryService;
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

	private Object img1;
	
	@RequestMapping({ "/anbd/main", "/anbd/", "/","/anbd" })
	public String getList(Model model, HttpServletRequest request,String search_name) {
		Client user = (Client) request.getSession().getAttribute("client");
		List<Product> product_all_list = new ArrayList<Product>();
		List<ProductEntity> product_All_list_et = repository.ProductAll(); // 전체 게시글 리스트
		// 전체 게시글 리스트
				for (ProductEntity temp : product_All_list_et) {
					product_all_list.add(service.toDto(temp));
				}
				System.out.println(product_all_list);
		model.addAttribute("items", product_all_list); // 전체 회원 리스트
		return "main";
	}
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
	@RequestMapping(value = "/anbd/product_register", method =  RequestMethod.POST)
	public String productRegister(String product_seller_id, Integer product_category_no, String product_name, String product_content, int product_price,
			@RequestParam MultipartFile product_img1, @RequestParam MultipartFile product_img2, @RequestParam MultipartFile product_img3,
			@RequestParam MultipartFile product_img4, @RequestParam MultipartFile product_img5) 
					throws IllegalStateException, IOException {

		String randomimg1 = null,randomimg2 = null,randomimg3 = null,randomimg4 = null,randomimg5 = null;
		String originalFile1  = product_img1.getOriginalFilename();
		String originalFile2  = product_img2.getOriginalFilename();
		String originalFile3  = product_img3.getOriginalFilename();
		String originalFile4  = product_img4.getOriginalFilename();
		String originalFile5  = product_img5.getOriginalFilename();
		String originalFileExtension1 = originalFile1.substring(originalFile1.lastIndexOf("."));
		String originalFileExtension2 = originalFile2.substring(originalFile1.lastIndexOf("."));
		String originalFileExtension3 = originalFile3.substring(originalFile1.lastIndexOf("."));
		String originalFileExtension4 = originalFile4.substring(originalFile1.lastIndexOf("."));
		String originalFileExtension5 = originalFile5.substring(originalFile1.lastIndexOf("."));
		if (!product_img1.isEmpty())
			randomimg1 = UUID.randomUUID().toString() + originalFileExtension1;
		if (!product_img2.isEmpty())
			randomimg2 = UUID.randomUUID().toString() + originalFileExtension2;
		if (!product_img3.isEmpty())
			randomimg3 = UUID.randomUUID().toString() + originalFileExtension3;
		if (!product_img4.isEmpty())
			randomimg4 = UUID.randomUUID().toString() + originalFileExtension4;
		if (!product_img5.isEmpty())
			randomimg5 = UUID.randomUUID().toString() + originalFileExtension5;
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
}

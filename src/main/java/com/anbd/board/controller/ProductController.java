package com.anbd.board.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.mail.search.IntegerComparisonTerm;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import com.anbd.board.entity.ChatEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.FavoritesEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Category;
import com.anbd.board.model.Chat;
import com.anbd.board.model.ChatRoom;
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
	
	@RequestMapping(value="/anbd/cookieDelete", method=RequestMethod.GET)
	public String testCookie(HttpServletResponse response, String product_no){
		Cookie kc = new Cookie("product" +product_no, null); // choiceCookieName(쿠키 이름)에 대한 값을 null로 지정
		kc.setMaxAge(0); // 유효시간을 0으로 설정
		response.addCookie(kc); // 응답 헤더에 추가해서 없어지도록 함
		return "redirect:main";
	}
	// 전체게시물 리스트
	@RequestMapping({ "/anbd/main", "/anbd/", "/","/anbd" })
	public String getList(Model model, HttpServletRequest request,String search_name) {
		Client user = (Client) request.getSession().getAttribute("client");
		List<Product> product_all_list = new ArrayList<Product>();
		List<ProductEntity> product_All_list_et = repository.ProductList(); // 전체 게시글 리스트
		// 전체 게시글 리스트
				for (ProductEntity temp : product_All_list_et) {
					if(temp.getProduct_status().equals("ing")) {
						product_all_list.add(service.toDto(temp));
					}
				}
		List<ProductEntity> products = new ArrayList<ProductEntity>();
		List<Product> p = new ArrayList<Product>();
		Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (Cookie c : cookies) {
						if (c.getName().indexOf("product") != -1) {
							products = repository.cookieAdd(Integer.parseInt(c.getValue()));
							for(ProductEntity temp : products) {
								p.add(service.toDto(temp));
							}
						}
					}
				}
		int startNo = (int) request.getSession().getAttribute("startNo");		
		int endNo = (int) request.getSession().getAttribute("endNo");		
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		model.addAttribute("cookie", p);
		model.addAttribute("address", user.getClient_address()); // 로그인한 주소
		model.addAttribute("items", product_all_list); // 현재 판매중인 리스트
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
	public String productRegister(String product_seller_id, Integer product_category_no, String product_name, String product_content, int product_price, String product_address,
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
				product_price, randomimg1, randomimg2, randomimg3, randomimg4, randomimg5, product_seller_id, null, 0, 0, "ing", date, null, product_address);
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
	public String prodouct_detail(int product_no, Model model, HttpServletRequest request,
			 HttpServletResponse response) {
		ProductEntity entity = repository.ProductDetail(product_no);
		repository.updateReadCount(product_no);
		Product product = service.toDto(entity);
		String seller_client_id =product.getProduct_seller_client_id();
		List<ProductEntity> seller = repository.findSellerID(seller_client_id);
		HttpSession session = request.getSession();
		Client user = (Client) session.getAttribute("client");
		String client_id = user.getClient_id();
		ClientEntity c_entity = client_repository.findId(seller_client_id);
		FavoritesEntity f_entity = f_repository.findById(product_no,client_id);
		
		String no = String.valueOf(product_no);
		Cookie cookie = new Cookie("product"+no, no);
		if(cookie != null) {
			cookie.setValue(no);
		}
		response.addCookie(cookie);
		model.addAttribute("seller_list",seller);
		model.addAttribute("client",c_entity);
		model.addAttribute("favorites",f_entity);
		model.addAttribute("product",entity);
		return "product_detail";
	}
	
	@SuppressWarnings("unchecked")
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
	
	
	@RequestMapping(value = "/anbd/product_search", method = RequestMethod.POST)
	public String prodouct_search(String search_name, Model model, HttpServletRequest request,
			 HttpServletResponse response) {
		String product_status = "ing";
		List<Product> product_seach_list = new ArrayList<Product>();
		List<ProductEntity >entity = repository.Search(search_name);
				for (ProductEntity temp : entity) {
					if(temp.getProduct_status().equals("ing")) {
						product_seach_list.add(service.toDto(temp));
					}
				}
		int cnt = repository.SearchCnt(search_name, product_status);
		model.addAttribute("seach_list",product_seach_list);
		model.addAttribute("seach_name",search_name);
		model.addAttribute("cnt",cnt);
		return "product_seach";
	}
	
	@RequestMapping(value = "/anbd/category", method = RequestMethod.GET)
	public String category_search(String category, Model model, HttpServletRequest request,
			 HttpServletResponse response) {
		String product_status = "ing";
		int category_no = c_repository.findCategory_no(category);
		List<Product> product_seach_list = new ArrayList<Product>();
		List<ProductEntity >entity = repository.Category_list(category_no);
				for (ProductEntity temp : entity) {
					if(temp.getProduct_status().equals("ing")) {
						product_seach_list.add(service.toDto(temp));
					}
				}
		int cnt = repository.category_SearchCnt(category_no, product_status);
		model.addAttribute("seach_list",product_seach_list);
		model.addAttribute("seach_name",category);
		model.addAttribute("cnt",cnt);
		return "product_seach";
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/pageing", method = RequestMethod.POST)
	public JSONObject pageing(@RequestBody JSONObject no, HttpServletRequest request) {
		int StartNo = 0;
		int EndNo = 0;
		StartNo = Integer.parseInt(String.valueOf(no.get("startNo")));
		EndNo = Integer.parseInt(String.valueOf(no.get("endNo")));
		StartNo =StartNo+8;
		List<Product> product_all_list = new ArrayList<Product>();
		List<ProductEntity> product_All_list_et = repository.ProductPageingList(StartNo,EndNo); // 전체 게시글 리스트
		// 전체 게시글 리스트
				for (ProductEntity temp : product_All_list_et) {
					if(temp.getProduct_status().equals("ing")) {
						product_all_list.add(service.toDto(temp));
					}
				}
		Client user = (Client) request.getSession().getAttribute("client");
		request.setAttribute("startNo", StartNo);
		request.setAttribute("endNo", EndNo);
		JSONArray jsonlist = new JSONArray();
		JSONObject result = new JSONObject();
		for (Product index : product_all_list) {
			JSONObject temp = new JSONObject();
			temp.put("product_no", index.getProduct_no());
			temp.put("product_category_no", index.getProduct_category_no());
			temp.put("product_name", index.getProduct_name());
			temp.put("product_content", index.getProduct_content());
			temp.put("product_price", index.getProduct_price());
			temp.put("product_date", index.getProduct_date());
			temp.put("product_img1", index.getProduct_img1());
			jsonlist.add(temp);
		}
		result.put("list", jsonlist);
		result.put("startNo", StartNo);
		result.put("endNo", EndNo);
		return result;
	}
	@RequestMapping(value="/anbd/Crawling", method=RequestMethod.GET)
	public String testCrawling(HttpServletResponse response){
		String DaangnUrl = "https://www.daangn.com/region/서울특별시/";
        String Detail_DaangnUrl = "https://www.daangn.com/articles/";
        
       try {
    	   for(int i = 374849100; i<374849200; i++){
               String want = Detail_DaangnUrl + i; //url
               int product_category_no =28;
               String product_name = null;
               String product_content =  null;
               int product_price = 0;
               String randomimg1 =null;
               String randomimg2 =null;
               String randomimg3 =null;
               String randomimg4 =null;
               String randomimg5 =null;
               String product_seller_id = "jang123";
               LocalDateTime date = LocalDateTime.now();
               String product_address = "서울 은평구 응암동";
               URL url1 = new URL(want);
               
               HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
               if(conn.getResponseCode() != 200){
               	continue;
               } 
               System.out.println(i);
               Document doc = Jsoup.connect(want).timeout(5000).get(); //접속
               if(doc.select("article").get(0).attr("id").equals("story-content")) {
               	continue;
               }
               Element imgcontent = doc.select("#content").get(0); // 이미지 가져오기
               if(imgcontent.text().equals("게시글이 삭제되었거나 존재하지 않습니당 :(")) {
               	continue;
               }
               Elements img_container = imgcontent.select("img"); //img info
               Elements product_title = imgcontent.select("#article-title"); //유저 info
               Elements product_container = imgcontent.select("#article-detail"); //유저 info
               Elements price = imgcontent.select("#article-price");
               Elements price_nanum = imgcontent.select("#article-price-nanum");
               for(Element img_text: img_container) {
               	String url = img_text.attr("data-lazy");
               	if(url.indexOf("dnvefa72aowie") == -1) {
               		continue;
               	}
               	if(price_nanum.attr("content").equals("0.0")) {
               		product_price = 0;
               	} else {
               		product_price =  Integer.parseInt(price.attr("content").replace(".0", ""));
               	}
               	product_name = product_title.text();
               	product_content = product_container.select("p").text();
               	URL imgUrl = new URL(url);
                   BufferedImage jpg = ImageIO.read(imgUrl);
                   String random_img = UUID.randomUUID().toString();
                   if(randomimg1 ==null) {
                   	randomimg1 = random_img+".jpg";
                   }else if (randomimg2 == null) {
                   	randomimg2 = random_img+".jpg";
                   }else if (randomimg3 == null) {
                   	randomimg3 = random_img+".jpg";
                   }else if (randomimg4 == null) {
                   	randomimg4 = random_img+".jpg";
                   }else if (randomimg5 == null) {
                   	randomimg5 = random_img+".jpg";
                   }
                   File file = new File("C:\\\\img\\"+random_img+".jpg");
                   ImageIO.write(jpg, "jpg", file);
               }
               if(product_content == null) {
              		continue;
              	}
               Product product = new Product(0, product_category_no, product_name, product_content,
      				product_price, randomimg1, randomimg2, randomimg3, randomimg4, randomimg5, product_seller_id, null, 0, 0, "ing", date, null, product_address);
				System.out.println(product);
				ProductEntity entity = service.toEntity(product);
				repository.save(entity);

          }
       	
       } catch (IOException e) {
           e.printStackTrace();
       }
		
		return "redirect:main";
	}
	
}


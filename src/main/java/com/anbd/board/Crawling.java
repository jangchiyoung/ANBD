package com.anbd.board;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Product;
import com.anbd.board.repository.ProductRepository;
import com.anbd.board.service.ProductService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.imageio.ImageIO;

public class Crawling {
	@Autowired
	static
	ProductRepository repository;
	
	@Autowired
	static
	ProductService service;
	
    public static void main(String[] args) {
    	 String DaangnUrl = "https://www.daangn.com/region/서울특별시/";
         String Detail_DaangnUrl = "https://www.daangn.com/articles/";
         
        try {
        	for(int i = 374848705; i<374848900; i++){
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
                System.out.println(conn);
                if(conn.getResponseCode() != 200){
                	continue;
                } 
                System.out.println(i);
                Document doc = Jsoup.connect(want).get(); //접속
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
       		
                Product product = new Product(0, product_category_no, product_name, product_content,
       				product_price, randomimg1, randomimg2, randomimg3, randomimg4, randomimg5, product_seller_id, null, 0, 0, "ing", date, null, product_address);
				System.out.println(product);
				ProductEntity entity = service.toEntity(product);
				repository.save(entity);

           }
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

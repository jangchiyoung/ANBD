package com.anbd.board.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anbd.board.repository.ProductRepository;
import com.anbd.board.repository.Report_boardRepository;
import com.anbd.board.repository.Report_productRepository;
import com.anbd.board.service.ProductService;
import com.anbd.board.service.Report_ProductService;
import com.anbd.board.service.Report_boardService;
import com.anbd.board.entity.Report_boardEntity;
import com.anbd.board.entity.Report_productEntity;
import com.anbd.board.model.Client;
import com.anbd.board.model.Report_board;
import com.anbd.board.model.Report_product;

@Controller
public class ReportController {
	
	@Autowired
	Report_boardRepository repository;
	
	@Autowired
	Report_boardService service;
	
	@Autowired
	Report_productRepository pr_repository;
	
	@Autowired
	Report_ProductService pr_service;
	
	@Autowired
	ProductRepository p_repository;
	
	@Autowired
	ProductService p_service;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/report_board", method = RequestMethod.POST)
	public JSONObject report_board(@RequestBody JSONObject report_board, HttpServletRequest request) {
		Integer report_board_no = (int) report_board.get("board_no");
		String report_board_client_id = (String) report_board.get("board_writer");
		String report_comment = (String) report_board.get("report_comment");
		HttpSession session = request.getSession();
		Client user = (Client) session.getAttribute("client");
		String report_client_id =user.getClient_id();
		Report_boardEntity reportCheck= repository.Report_board(report_client_id, report_board_client_id, report_board_no);
		JSONObject data = new JSONObject();
		if(reportCheck == null) {
			Report_board report = new Report_board(0,report_client_id,report_board_client_id,report_board_no,report_comment);
			Report_boardEntity entity = service.toEntity(report);
			repository.save(entity);
			String success = "성공적으로 신고가 접수 되었습니다.";
			data.put("report", success);
		} else {
			String error = "이미 신고하신 게시물입니다.";
			data.put("re_error", error);
		}
		data.put("reportCheck", reportCheck);
		System.out.println("dsdadasdasdasdsadas"+data);
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/report_product", method = RequestMethod.POST)
	public JSONObject report_product(@RequestBody JSONObject report_product, HttpServletRequest request) {
		Integer report_product_no = (int) report_product.get("product_no");
		String report_product_client_id = (String) report_product.get("product_seller");
		String report_comment = (String) report_product.get("report_comment");
		HttpSession session = request.getSession();
		Client user = (Client) session.getAttribute("client");
		String report_client_id =user.getClient_id();
		Report_productEntity reportCheck= pr_repository.Report_product(report_client_id, report_product_client_id, report_product_no);
		JSONObject data = new JSONObject();
		if(reportCheck == null) {
			Report_product report = new Report_product(0,report_client_id,report_product_client_id,report_product_no,report_comment);
			Report_productEntity entity = pr_service.toEntity(report);
			pr_repository.save(entity);
			String success = "성공적으로 신고가 접수 되었습니다.";
			data.put("report", success);
		} else {
			String error = "이미 신고하신 게시물입니다.";
			data.put("re_error", error);
		}
		data.put("reportCheck", reportCheck);
		System.out.println("dsdadasdasdasdsadas"+data);
		return data;
	}
	
}

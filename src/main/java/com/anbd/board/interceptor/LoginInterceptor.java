package com.anbd.board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.anbd.board.model.Client;

public class LoginInterceptor implements HandlerInterceptor {

	//로그인 여부 - 세션 체크 - 검사하여 요청처리하기에 인터셉터 활용
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		HttpSession session = request.getSession();
		Client client = (Client) session.getAttribute("client");
		int startNo = 0;
		int endNo = 8;
		session.setAttribute("startNo", startNo);
		session.setAttribute("endNo", endNo);
		if(client == null) {
  		response.sendRedirect(request.getContextPath() +"/anbd/login?alert=y");
//		response.sendRedirect("login?alert=y");  //http://localhost:8080/board/community/login?alert=y  -> 오류
			return false;		//handler 메소드로 제어(실행)가 이동되지 않습니다.
		}else {
		//로그인 된 상태이므로 요청에 따라 handler 메소드로 이동합니다.
			return true;
		}
	}
}

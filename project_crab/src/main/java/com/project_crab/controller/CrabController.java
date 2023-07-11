package com.project_crab.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project_crab.dto.Manager;
import com.project_crab.dto.Member;
import com.project_crab.dto.Product;
import com.project_crab.service.CrabService;
import com.project_crab.util.MyUtil;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class CrabController implements WebMvcConfigurer {

	@Autowired 
	private CrabService crabService;
	
	@Autowired
	MyUtil myUtil;
	
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
		
	@RequestMapping(value = "/login")
	public String login(Model model, String status) {
		
		model.addAttribute("status", status);
		
		return "bbs/login";
	}
	
	@RequestMapping(value = "/shop", method = { RequestMethod.GET, RequestMethod.POST })
	public String shop(HttpServletRequest request, Model model) {
		try {
			String pageNum = request.getParameter("pageNum"); // 바뀌는 페이지 번호
			int currentPage = 1; // 현재 페이지 번호(디폴트는 1)

			Object member_no = model.getAttribute("member_no");
			
			if (pageNum != null) currentPage = Integer.parseInt(pageNum);
			String searchKey = request.getParameter("searchKey"); // 검색 키워드(subject, name, content)
			String searchValue = request.getParameter("searchValue");
			
			if (searchValue == null) {
				searchKey = "name"; // 검색 키워드의 디폴트는 subject
				searchValue = ""; // 검색어의 디폴트는 빈문자열
			} else {
				if (request.getMethod().equalsIgnoreCase("GET")) {
					// get 방식으로 request가 왔다면
					// 관리 파라메타의 값(searchValue)을 디코딩(사람이 알 수 있게 변환)해준다.
					searchValue = URLDecoder.decode(searchValue, "UTF-8");
				}
			}
			// 1. 전체 게시물의 갯수를 가져온다.(페이징 처리에 필요)
			int dataCount = crabService.getDataCount(searchKey, searchValue);
			
			// 2. 페이징 처리를한다.(준비 단계)
			int numPerPage = 3; // 페이지당 보여둘 게시글의 갯수(5개)
			int totalPage = myUtil.getPageCount(numPerPage, dataCount); // 페이지의 전체 갯수
			if (currentPage > totalPage)
				currentPage = totalPage; // totalPage 보다 크면 안됨

			int start = (currentPage - 1) * numPerPage + 1; // 1, 6, 11, 16 ....
			int end = currentPage * numPerPage; // 5, 10, 15, 20 ....

			// 3. 전체 게시물을 가져온다.
			List<Product> lists = crabService.getProductList(searchKey, searchValue, start, end);

			// 4. 페이징 처리를한다.
			String param = "";

			if (searchValue != null && !searchValue.equals("")) {
				// searchValue에 검색어가 있으면
				param += "&searchKey=" + searchKey;
				param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); // 컴퓨터의 언어로 인코딩
			}
			
			String shopUrl = "/shop";

			if (!param.equals(""))
				shopUrl += "?" + param;

			if(member_no == null) {
				member_no = request.getParameter("member_no");
				model.addAttribute("member_no", member_no);
			}
			
			String pageIndexList = myUtil.pageIndexList(request, currentPage, totalPage, shopUrl, member_no) ;
			
			
			String articleUrl = "/article?pageNum=" + currentPage;

			if (!param.equals("")) {
				articleUrl += "&" + param;
			}
			model.addAttribute("lists", lists); // DB에서 가져온 전체 게시물
			model.addAttribute("articleUrl", articleUrl); // 상세 페이지로 이동하기 위한 url
			model.addAttribute("pageIndexList", pageIndexList); // ◀이전 6 7 8 9 10 다음▶
			model.addAttribute("dataCount", dataCount); // 전체 게시물의 갯수
			

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 리스트를 불러오는 중 에러가 발생했습니다.");
		}
		model.addAttribute("status", request.getParameter("status"));
		return "bbs/shop";
	}
	
	@RequestMapping(value = "/check")
	public String check(HttpServletRequest request, Model model) {
		String status = request.getParameter("status");
		if (status.equals("관리자")) {
			try {
				Manager m = crabService.getManager_login(request.getParameter("id"), request.getParameter("pw"));

				if (m.getManager_name() != null) {
					model.addAttribute("manager", m);
					model.addAttribute("status", status);
					shop(request, model);
					return "bbs/shop";
				}

			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "관리자 아이디 또는 비밀번호가 옳바르지않습니다.");
			}
		}else if (status.equals("회원")){
			try {
				
				Member mem = crabService.getMember_login(request.getParameter("id"), request.getParameter("pw"));
		
				if (mem.getMember_id() != null){
					model.addAttribute("member", mem);
					model.addAttribute("status", status);
					model.addAttribute("member_no", mem.getMember_no());
					shop(request, model);
					return "bbs/shop";
				}

			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "회원 아이디 또는 비밀번호가 옳바르지않습니다.");
			}
		}
		
		model.addAttribute("status", status);
		
		return "bbs/login";
	}
	
	@RequestMapping(value = "/created")
	public String created(HttpServletRequest request, Model model) {
		model.addAttribute("status", request.getParameter("status"));
		
		return "bbs/created";
	}
	
	@RequestMapping(value = "/insert_product")
	public String insert_product(HttpServletRequest request, Model model, Product product) {
		try {
			crabService.insert_product(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("status", request.getParameter("status"));
		shop(request, model);
		
		return "bbs/shop";
	}
	
	@RequestMapping(value = "/join")
	public String join(HttpServletRequest request, Model model) {
		return "bbs/join";
	}
	
	@RequestMapping(value = "/insert_mem", method = RequestMethod.POST)
    public String insert_mem(HttpServletRequest request, Model model, Member member){
		try {
			crabService.insert_mem(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return "index";
    }
	
	@RequestMapping(value = "/mypage", method = {RequestMethod.GET, RequestMethod.POST })
	public String mypage(HttpServletRequest request, Model model) {
		try {		
			Member mem = crabService.getMypage(Integer.parseInt(request.getParameter("member_no")));
			model.addAttribute("mem", mem);
			model.addAttribute("status", request.getParameter("status"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bbs/mypage";
	}
	
	@RequestMapping(value = "/updatePage", method = RequestMethod.GET)
	public String updatePage(HttpServletRequest request, Model model) {
		try {
			Member mem = crabService.getMypage(Integer.parseInt(request.getParameter("member_no")));
			
			model.addAttribute("mem", mem);
			model.addAttribute("status", request.getParameter("status"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bbs/myPageUpdate";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Member mem, HttpServletRequest request, Model model) {
		try {			
			crabService.updateData(mem);
			
			mypage(request, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bbs/mypage";
	}

	@RequestMapping(value = "/deleted_ok", method = RequestMethod.GET)
	public String deletedOK(HttpServletRequest request, Model model) {
		
		try {
			crabService.deleteMem(Integer.parseInt(request.getParameter("member_no")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/index";
	}
	
}
	

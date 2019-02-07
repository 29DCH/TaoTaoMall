package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.LoginService;
import com.taotao.portal.service.impl.LoginServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private LoginServiceImpl loginService;
	
	/**
	 * 用户访问url之前执行
	 * <p>Title: preHandle</p>
	 * <p>Description: </p>
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1、从cookie中取TT_TOKEN
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//判断token是否为空
		if (StringUtils.isBlank(token)) {
			//跳转到登录页面，带回调的url
			response.sendRedirect(loginService.SSO_REDIRICT_URL 
					+ loginService.SSO_LOGIN_PAGE_URL + "?redirect=" + getUrl(request));
			return false;
		} else {
			//根据token取用户信息
			TbUser user = loginService.getUserByToken(token);
			if (null == user) {
				//跳转到登录页面，带回调的url
				response.sendRedirect(loginService.SSO_REDIRICT_URL 
						+ loginService.SSO_LOGIN_PAGE_URL + "?redirect=" + getUrl(request));
				return false;
			}
			//把用户信息放到request中
			request.setAttribute("user", user);
		}
		//放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	private String getUrl(HttpServletRequest request) {
		String url = request.getScheme() + "://" 
				+ request.getServerName() + ":" 
				+ request.getServerPort() 
				+ request.getContextPath() 
				+ request.getRequestURI(); 
		String url2 = request.getRequestURL().toString();
		System.out.println(url2);
		return url;
	}

}

package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_TOKEN_USER_URL}")
	private String SSO_TOKEN_USER_URL;
	@Value("${SSO_LOGIN_PAGE_URL}")
	public String SSO_LOGIN_PAGE_URL;
	@Value("${SSO_REDIRICT_URL}")
	public String SSO_REDIRICT_URL;
	
	@Override
	public TbUser getUserByToken(String token) {
		try {
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_TOKEN_USER_URL + token);
			// 把json转换成java对象
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				return user;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

}

package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;
	
	/**
	 * 数据有效性校验
	 * <p>Title: checkInfo</p>
	 * <p>Description: </p>
	 * @param data
	 * @param type
	 * @return
	 * @see com.taotao.sso.service.RegisterService#checkInfo(java.lang.String, int)
	 */
	@Override
	public TaotaoResult checkInfo(String data, int type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		//用户名
		if (type == 1) {
			criteria.andUsernameEqualTo(data);
		//电话
		} else if (type == 2) {
			criteria.andPhoneEqualTo(data);
		//email
		} else if (type == 3) {
			criteria.andEmailEqualTo(data);
		}
		List<TbUser> list = userMapper.selectByExample(example);
		//没查到可以使用
		if (list == null || list.isEmpty()) {
			return TaotaoResult.ok(true); 
		}
		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult register(TbUser user) {
		//有效性验证
		if (StringUtils.isBlank(user.getUsername())) {
			return TaotaoResult.build(400, "用户名不能为空");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(400, "密码不能为空");
		}
		//密码进行md5加密
		String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(password);
		
		//补全user对象中的信息
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//把用户信息插入到数据库
		userMapper.insert(user);
		
		return TaotaoResult.ok();
	}

}

package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

/**
 * 商品规格参数模板service
 * <p>
 * Title: ItemParamServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 入云龙
 * @date 2015年8月16日上午10:45:40
 * @version 1.0
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Override
	public TaotaoResult checkParam(long cid) {
		try {
			TbItemParamExample example = new TbItemParamExample();
			Criteria criteria = example.createCriteria();
			criteria.andItemCatIdEqualTo(cid);
			List<TbItemParam> list = itemParamMapper.selectByExample(example);
			// 判断是否查询到结果
			if (null == list || list.isEmpty()) {
				return TaotaoResult.ok();
			}
			return TaotaoResult.ok(list.get(0));
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@Override
	public TaotaoResult addItemParam(long cid, String template) {
		//创建规格模板pojo
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(template);
		tbItemParam.setCreated(new Date());
		tbItemParam.setUpdated(new Date());
		//插入到数据库
		try {
			itemParamMapper.insert(tbItemParam);
		} catch(Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult getItemParemByCid(long cid) {
		
		//创建查询条件
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if (null != list && !list.isEmpty()) {
			return TaotaoResult.ok(list.get(0));
		}
		
		return TaotaoResult.build(400, "此分类未定义规格模板");
	}

}

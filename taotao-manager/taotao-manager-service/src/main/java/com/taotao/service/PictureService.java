package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;

/**
 * 上传图片处理
 * <p>Title: PictureService</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月15日下午2:59:38
 * @version 1.0
 */
public interface PictureService {

	PictureResult uploadPicture(MultipartFile uploadFile);
}

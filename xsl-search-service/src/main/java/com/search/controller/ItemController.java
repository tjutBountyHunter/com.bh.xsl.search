package com.search.controller;

import com.search.common.XslResult;
import com.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.UnknownHostException;

/**
 * 索引库维护
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月11日下午2:50:45
 * @version 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/manager")
public class ItemController {
	
	@Autowired
	private SearchItemService itemService;

	/**
	 * 导入商品数据到索引库
	 */

	@RequestMapping("/importall")
	@ResponseBody
	public XslResult importAllItems() throws UnknownHostException {
		XslResult result = itemService.importAllItems();
 		return result;
	}
	@RequestMapping("/importhunter")
	@ResponseBody
	public XslResult importAllHunter() throws UnknownHostException {
		XslResult result = itemService.importAllHunter();
		return result;
	}
}

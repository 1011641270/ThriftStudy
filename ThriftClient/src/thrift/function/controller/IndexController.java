/*******************************************************************************
 * COPYRIGHT Beijing adsohu Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: IndexController.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-6-8
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2009-6-8    Liujiajun         Created
 *******************************************************************************/
package thrift.function.controller;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import thrift.proxy.ThriftClientProxy;
import thrift.service.UserService;


@Controller
public class IndexController {
	
	@Resource(name="userClient01")
	private ThriftClientProxy client01;
	
	@Resource(name="userClient02")
	private ThriftClientProxy client02;

	
	/**
	 * 展示首页面
	 * 
	 * @param model
	 *            数据封装
	 * @return 首页视图
	 */
	@RequestMapping("/index.do")
	public String handleIndex(Model model) {
		UserService.Client client_02 = (UserService.Client)(client02.getClient());
		String name;
		try {
			name = client_02.getUserName("zhaosi", 100);
			model.addAttribute("userName", name);
		} catch (TException e) {
			e.printStackTrace();
		}
		
		return "index";
	}

	

	
	
	
}

package com.cbhlife.mp.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import com.cbhlife.mp.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 *  前端控制器
 *
 * @author weiyunhui
 * @since 2018-06-21
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService; 
	
//	public String  login() {
//		//employeeService.select
//	}



}


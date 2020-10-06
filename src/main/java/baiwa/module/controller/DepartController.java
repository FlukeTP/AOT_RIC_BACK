package baiwa.module.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import baiwa.module.service.FwDepartService;

@Controller
@RequestMapping("api/depart")
public class DepartController {

	private static final Logger logger = LoggerFactory.getLogger(DepartController.class);
	
	@Autowired
	private FwDepartService fwDepartService;
	
}

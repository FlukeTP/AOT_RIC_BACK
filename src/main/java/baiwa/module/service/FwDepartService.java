package baiwa.module.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import baiwa.module.repository.jpa.FwDepartRepository;

@Service
public class FwDepartService {
	
	private static final Logger logger = LoggerFactory.getLogger(FwDepartService.class);
	
	@Autowired
	private FwDepartRepository fwDepartRepository;

}

package aot.sap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.sap.repository.jpa.SapRicControlRepository;

@Service
public class SapRicControlService {

	private static final Logger logger = LoggerFactory.getLogger(SapRicControlService.class);

	@Autowired
	private SapRicControlRepository sapRicControlRepository;

}

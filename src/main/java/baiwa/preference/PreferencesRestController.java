package baiwa.preference;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import baiwa.common.bean.ResponseData;
import baiwa.constant.DocumentConstants.MODULE_NAME;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;
import baiwa.support.ApplicationCache;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/preferences/reload-cache")
public class PreferencesRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(PreferencesRestController.class);
	
	@Autowired
	private ApplicationCache  applicationCache;
	
	@GetMapping
	@ApiOperation(
		tags = MODULE_NAME.PREFERENCES,
		value = "Reload Cache",
		notes = "Reload Parameter and Message into Cache"
	)
	@ApiResponses(value = {@ApiResponse(code = HttpServletResponse.SC_OK, message = "Successfully reload cache")
	})
	public ResponseEntity<?> reloadCache() {
		logger.info("reloadCache");
		applicationCache.loadCache();
		Map<String, String> map = new HashMap<>();
		map.put("status", "reload cache successful");
		
		ResponseData<Map<String, String>> response = new ResponseData<>();
		response.setData(map);
		response.setStatus(RESPONSE_STATUS.SUCCESS);
		
		return new ResponseEntity<ResponseData<Map<String, String>>>(response, HttpStatus.OK);
	}
	
}

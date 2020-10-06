package aot.util.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//import com.ptg.vrm.po.service.SendEmailSubmitPo;

@Component
public class Electric001Scheduler {

	private static final Logger logger = LoggerFactory.getLogger(Electric001Scheduler.class);


//	@Scheduled(cron = "${electric001.job.cronExpressions}")
	public void sendEmail() {
		

		
		try {
			
			logger.info("[[ Electric001Scheduler success ]]");
			System.out.print("xxxxxxxxxxxxxxxxx");
		} catch (Exception e) {
			logger.error("[[ sendEmail ]]",e);
		}

	}

	
}

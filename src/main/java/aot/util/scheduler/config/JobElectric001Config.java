package aot.util.scheduler.config;
//package com.aot.ric.scheduler.config;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
//import org.apache.commons.lang3.StringUtils;
//import org.quartz.CronScheduleBuilder;
//import org.quartz.CronTrigger;
//import org.quartz.JobBuilder;
//import org.quartz.JobDataMap;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.SchedulerFactory;
//import org.quartz.TriggerBuilder;
//import org.quartz.impl.StdSchedulerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.aot.ric.scheduler.excecute.ExecuteElectric001Service;
//import com.aot.ric.scheduler.service.SyncElectric001Service;
//
////@Configuration
////@ConditionalOnProperty(name = "electric001.job.cronExpressions", havingValue = "", matchIfMissing = false)
//public class JobElectric001Config {
//	private static final Logger log = LoggerFactory.getLogger(JobElectric001Config.class);
//
//	@Autowired
//	private SyncElectric001Service syncElectric001Service;
//
//	@Value("${electric001.job.cronExpressions}")
//	private String cronExpressions;
//
//	@PostConstruct
//	public void init() {
//		log.info(
//				"############################### JobElectric001BatchJobSchedulerConfig ###############################");
//		log.info("###  ");
//		log.info("###  ");
//		log.info("###  cronEx : " + cronExpressions);
//		log.info("###  ");
//		log.info("###  ");
//		log.info("###############################");
//	}
//
//	@Bean
//	public JobDetail electric001JobDetail() {
//		JobDataMap newJobDataMap = new JobDataMap();
//		newJobDataMap.put("syncElectric001Service", syncElectric001Service);
//		JobDetail job = JobBuilder.newJob(ExecuteElectric001Service.class)
//				.withIdentity("syncElectric001Service", "group1") // name "myJob", group "group1"
//				.usingJobData(newJobDataMap).build();
//		return job;
//	}
//
//	@Bean("electric001CronTrigger")
//	public Set<CronTrigger> electric001CronTrigger() {
//		Set<CronTrigger> cornsets = new HashSet<>();
//		String[] corns = StringUtils.split(cronExpressions, ",");
//		int i = 0;
//		for (String corn : corns) {
//			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("electric001CronTrigger" + i++, "group1")
//					.withSchedule(CronScheduleBuilder.cronSchedule(corn)).build();
//			cornsets.add(trigger);
//		}
//
//		return cornsets;
//	}
//
//	@Bean
//	public SchedulerFactory electric001SchedulerFactory() throws SchedulerException {
//		SchedulerFactory sf = new StdSchedulerFactory();
//		Scheduler sched = sf.getScheduler();
//		sched.scheduleJob(electric001JobDetail(), electric001CronTrigger(), true);
//		sched.start();
//		return sf;
//	}
//
//	@PreDestroy
//	public void destroy() throws SchedulerException {
//		log.info("systemUnworkingBatchJobSchedulerConfig.. shutdown");
//		electric001SchedulerFactory().getScheduler().shutdown();
//	}
//}

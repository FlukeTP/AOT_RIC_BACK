package aot.util.scheduler.excecute;
//package com.aot.ric.scheduler.excecute;
//
//import org.quartz.Job;
//import org.quartz.JobDataMap;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.aot.ric.scheduler.service.SyncElectric001Service;
//
//public class ExecuteElectric001Service implements Job {
//	private static final Logger logger = LoggerFactory.getLogger(ExecuteElectric001Service.class);
//
//	@Override
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//
//		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//		try {
//			logger.info("Job SystemUnworking Working ...");
//			SyncElectric001Service electric001JobService = (SyncElectric001Service) dataMap
//					.get("electric001JobService");
//			electric001JobService.syncData();
//		} catch (Exception e) {
//			logger.error("Job SystemUnworking", e);
//		}
//	}
//}

package aot.util.sapreqhelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.service.RicNoGenerator;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.request.Item;
import baiwa.util.ConvertDateUtils;

@Service
public class CommonARRequest {

	@Autowired
	private RicNoGenerator ricNoGenerator;
	// 
	public ArRequest getThreeTemplate(String busPlace, String comCode , String docType, String ricNo) {
		ArRequest arRequest = new ArRequest();
		List<Header> headerList = new ArrayList<Header>();

		Header header = new Header();
		if(StringUtils.isBlank(ricNo)) {
			ricNo = ricNoGenerator.getRicNo();
		}
		header.setTransactionNo(ricNo);
		header.setRefKeyHeader1(ricNo);
		header.setItemNo("0001");
		header.setComCode(comCode);
		header.setCurrency("THB");
		header.setDocType(docType);
		header.setPostingDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
		header.setDocDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
		header.setBusPlace(busPlace);

		Item item1 = new Item();
		Item item2 = new Item();
		Item item3 = new Item();

		// Create Item1 Template

		// Crete Item2 Template

		// Create Item3 Template

		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		itemList.add(item2);
		itemList.add(item3);
		header.setItem(itemList);
		headerList.add(header);
		arRequest.setHeader(headerList);
		return arRequest;

	}

	public ArRequest getOneTemplate(String busPlace, String comCode , String docType, String ricNo) {
		ArRequest arRequest = new ArRequest();
		List<Header> headerList = new ArrayList<Header>();
		
		Header header = new Header();
		if(StringUtils.isBlank(ricNo)) {
			ricNo = ricNoGenerator.getRicNo();
		}
		header.setTransactionNo(ricNo);
		header.setRefKeyHeader1(ricNo);
		header.setItemNo("001");
		header.setComCode(comCode);
		header.setCurrency("THB");
		header.setDocType(docType);
		header.setPostingDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
		header.setDocDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
		header.setBusPlace(busPlace);
		
		Item item1 = new Item();

		// Create Item1 Template

		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		header.setItem(itemList);
		headerList.add(header);
		arRequest.setHeader(headerList);
		return arRequest;
	}
	
//	public ArRequest getThreeTemplate2(String busPlace, String comCode , String docType, String ricNo) {
//		ArRequest arRequest = new ArRequest();
//		List<Header> headerList = new ArrayList<Header>();
//
//		Header header = new Header();
//		if(StringUtils.isBlank(ricNo)) {
//			ricNo = ricNoGenerator.getRicNo();
//		}
//		header.setTransactionNo(ricNo);
//		header.setRefKeyHeader1(ricNo);
//		header.setItemNo("0001");
//		header.setComCode(comCode);
//		header.setCurrency("THB");
//		header.setDocType(docType);
//		header.setPostingDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
//		header.setDocDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
//		header.setBusPlace(busPlace);
//
//		Item item1 = new Item();
//		Item item2 = new Item();
//		Item item3 = new Item();
//
//		// Create Item1 Template
//
//		// Crete Item2 Template
//
//		// Create Item3 Template
//
//		List<Item> itemList = new ArrayList<Item>();
//		itemList.add(item1);
//		itemList.add(item2);
//		itemList.add(item3);
//		header.setItem(itemList);
//		headerList.add(header);
//		arRequest.setHeader(headerList);
//		return arRequest;
//	}
//
//	public ArRequest getOneTemplate2(String busPlace, String comCode , String docType, String ricNo) {
//		ArRequest arRequest = new ArRequest();
//		List<Header> headerList = new ArrayList<Header>();
//		
//		Header header = new Header();
//		if(StringUtils.isBlank(ricNo)) {
//			ricNo = ricNoGenerator.getRicNo();
//		}
//		header.setTransactionNo(ricNo);
//		header.setRefKeyHeader1(ricNo);
//		header.setItemNo("001");
//		header.setComCode(comCode);
//		header.setCurrency("THB");
//		header.setDocType(docType);
//		header.setPostingDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
//		header.setDocDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
//		header.setBusPlace(busPlace);
//		
//		Item item1 = new Item();
//
//		// Create Item1 Template
//
//		List<Item> itemList = new ArrayList<Item>();
//		itemList.add(item1);
//		header.setItem(itemList);
//		headerList.add(header);
//		arRequest.setHeader(headerList);
//		return arRequest;
//
//	}
	
	public ArRequest getTemplateMutiItem(String busPlace, String comCode, String docType, int sizeArr, String ricNo) {
		ArRequest arRequest = new ArRequest();
		List<Header> headerList = new ArrayList<Header>();

		Header header = new Header();
		if(StringUtils.isBlank(ricNo)) {
			ricNo = ricNoGenerator.getRicNo();
		}
		header.setTransactionNo(ricNo);
		header.setRefKeyHeader1(ricNo);
		header.setItemNo("0001");
		header.setComCode(comCode);
		header.setCurrency("THB");
		header.setDocType(docType);
		header.setPostingDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
		header.setDocDate(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY_MM_DD,ConvertDateUtils.LOCAL_EN));// Get Current Date From Util
		header.setBusPlace(busPlace);
		
		List<Item> itemList = new ArrayList<Item>();
		Item item = null;
		for (int i = 0; i < sizeArr + 2; i++) {
			item = new Item();
			itemList.add(item);
		}
		header.setItem(itemList);
		headerList.add(header);
		arRequest.setHeader(headerList);
		return arRequest;
	}

}

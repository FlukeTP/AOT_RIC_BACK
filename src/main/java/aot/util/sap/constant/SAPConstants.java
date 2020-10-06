package aot.util.sap.constant;

import java.util.Date;

import baiwa.util.ConvertDateUtils;

public class SAPConstants {

	public static String SAP_CONNECTION_FAIL = "SAP_CONNECTION_FAIL";
	public static String SAP_CONNECTION_FAIL_MSG = "ไม่สามารถเชื่อมต่อ SAP ได้";
	public static String SAP_SUCCESS = "SAP_SUCCESS";
	public static String SAP_FAIL = "SAP_FAIL";
	public static String TRANSNO = "TRANSNO";
	public static String YEAR = "YEAR";
	public static String N_A = "N/A";

	public static String COMCODE = "1100";

	public static final String CHARGE_TYPE_LG = "ค่าประกัน";
	public static final String DEPOSIT_TH = "เงินประกัน";
	
	public static class PAYMENT_TYPE {
		public static final String CASH_TH = "เงินสด";
		public static final String BANK_GUARANTEE = "Bank guarantee";
		public static final String BANK_GUARANTEE_UPPER = "BANK_GUARANTEE";
		public static final String CASH_EN = "CASH";
		public static final String LG = "LG";
		public static final String BILLING_EN = "BILLING";
	}
	
	public static class CHARGE_RATE_TYPE {
		public static final String LG_TH = "ค่าประกัน";
		public static final String ONE_TIME_TH = "รายครั้ง";
	}
	
	public static class UNIT {
		public static final String PEOPLE_TH = "คน";
		public static final String TIME_TH = "ครั้ง";
	}
	
	public static class REQUEST_TYPE {
		public static final String PACKAGES = "ขอใช้เหมาจ่าย";
	}
	
	public static class BUSPLACE {
		public static String HEAD_OFFICE = "0000";
		public static String HAT_YAI = "0002";
		public static String FREE_ZONE = "0008";
	}
	
	public static class ELECTRIC {
		public static String TEXT = "Electricity-".concat(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MMM_YYYY_SPAC, ConvertDateUtils.LOCAL_EN));
	}
	
	public static class WATER {
		public static String TEXT = "Water-".concat(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MMM_YYYY_SPAC, ConvertDateUtils.LOCAL_EN));
	}
	
	public static class PHONE {
		public static String TEXT = "Phone-".concat(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MMM_YYYY_SPAC, ConvertDateUtils.LOCAL_EN));
	}
	
	public static class COMMUNICATE {
		public static String TEXT = "Communicate-".concat(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MMM_YYYY_SPAC, ConvertDateUtils.LOCAL_EN));
	}
	
	public static class IT {
		public static String TEXT = "It-".concat(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MMM_YYYY_SPAC, ConvertDateUtils.LOCAL_EN));
	}
	
	public static class FIREBRIGADE {
		public static String TEXT = "Firebrigade-".concat(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MMM_YYYY_SPAC, ConvertDateUtils.LOCAL_EN));
	}
	
	public static class CUTE_TRAINGING_ROOM {
		public static String TEXT = "Cute Training Room-".concat(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MMM_YYYY_SPAC, ConvertDateUtils.LOCAL_EN));
	}
	
	public static class EQUIPMENT {
		public static String TEXT = "Equipment-".concat(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MMM_YYYY_SPAC, ConvertDateUtils.LOCAL_EN));
	}
	
	public static class GARBAGEDISPOSAL {
		public static String TEXT = "Garbage Dsiposal-".concat(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MMM_YYYY_SPAC, ConvertDateUtils.LOCAL_EN));
	}
	
	public static class SPECIAL_GL {
		public static String SPGL_I = "I";
		public static String SPGL_J = "J";
		public static String SPGL_K = "K";
		public static String SPGL_H = "H";
		public static String SPGL_O = "O";
		public static String SPGL_3 = "3";
		public static String SPGL_4 = "4";
		public static String SPGL_5 = "5";
		public static String SPGL_6 = "6";
		public static String SPGL_7 = "7";
	}
	
	public static class DEPOSIT_TEXT {
		public static String DEPOSIT_ELECTRICITY = "Deposit-Electricity";
		public static String DEPOSIT_WATER = "Deposit-Water";
		public static String DEPOSIT_TELEPHONE = "Deposit-Telephone";
		public static String DEPOSIT_INTERCOM = "Deposit-Intercom";
		public static String DEPOSIT_IT = "Deposit-IT";
		public static String DEPOSIT_EQUIPMENT = "Deposit-Equipment";
		public static String DEPOSIT_GARBAGEDISPOSAL = "Deposit-GarbageDisposal";
		public static String DEPOSIT_FIREBRIGADE = "Deposit-Firebrigade";
		public static String DEPOSIT_COMMUNICATE = "Deposit-Communication";
		
	}
}

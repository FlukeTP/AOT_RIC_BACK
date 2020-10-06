package aot.common.constant;

public class CommunicateConstants {
	public class FLAG_SYNC_INFO {
		public static final String CANCEL = "CANCEL";
	}
	
	public class FLAG_CANCEL {
		public static final String TRUE = "Y";
		public static final String FALSE = "N";
		public static final String WAIT = "W";
	}

	public static class PAYMENT_TYPE {
		public static class CASH {
			public static final String DESC_EN = "CASH";
			public static final String DESC_TH = "เงินสด";
		}
		
		public static class BILL {
			public static final String DESC_EN = "BILL";
			public static final String DESC_TH = "ออกบิล";
		}

		public static class BANK_GUARANTEE {
			public static final String DESC_EN = "BANK_GUARANTEE";
			public static final String DESC_TH = "Bank guarantee";
		}
	}
}

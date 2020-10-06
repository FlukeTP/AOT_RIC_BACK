package baiwa.constant;

public class CommonConstants {
	
	public static final class FLAG {
		public static final String Y_FLAG = "Y";
		public static final String N_FLAG = "N";
		public static final String X_FLAG = "X";
	}
	
	// Spring Profiles
	public static final class PROFILE {
		// Application
		public static final String DEV = "dev";
		public static final String SIT = "sit";
		public static final String UAT = "uat";
		public static final String PROD = "prod";
		// Unit Test
		public static final String UNITTEST = "unittest";
		public static final String UNITTEST_MOCK = "unittest-mock";
	}
	
	public static final class JsonStatus {
		public static final String ERROR = "1";
		public static final String SUCCESS = "0";
	}
	
	public static final class SendStatus {
		public static final String SENT = "SENT";
		public static final String UNSENT = "UNSENT";
	}
	
}

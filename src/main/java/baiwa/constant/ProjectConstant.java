package baiwa.constant;

public class ProjectConstant {

	public enum RESPONSE_STATUS {
		SUCCESS, FAILED, DUPLICATE_DATA
	}

	public class RESPONSE_MESSAGE {
		public static final String ERROR500_CODE = "MSG_SYSTEM";
		public static final String ERROR500 = "กรุณาติดต่อผู้ดูแลระบบ";
		public static final String SUCCESS = "SUCCESS";

		public class SAVE {
			public static final String SUCCESS_CODE = "MSG_00002";
			public static final String SUCCESS = "บันทึกเรียบร้อยแล้ว";
			public static final String FAILED_CODE = "MSG_00003";
			public static final String FAILED = "บันทึกไม่สำเร็จ";
			public static final String DUPLICATE_DATA_CODE = "MSG_00004";
			public static final String DUPLICATE_DATA = "มีอยู่ในระบบแล้ว";
		}

		public class DELETE {
			public static final String SUCCESS_CODE = "MSG_00005";
			public static final String SUCCESS = "ลบเรียบร้อยแล้ว";
			public static final String FAILED_CODE = "MSG_00006";
			public static final String FAILED = "ลบไม่สำเร็จ";
		}
		
		public class SAP {
			public static final String SUCCESS = "ส่ง SAP สำเร็จ";
			public static final String FAILED = "ส่ง SAP ไม่สำเร็จ";
			public static final String CONNECTION_FAILED = "ไม่สามารถเชื่อมต่อ SAP ได้";
		}
		
		public class SYNC_DATA {
			public static final String SUCCESS = "ซิงค์ข้อมูลเรียบร้อยแล้ว";
			public static final String FAILED = "ซิงค์ข้อมูลไม่สำเร็จ";
		}
		
		public class UPLOAD {
			public static final String SUCCESS = "อัปโหลดข้อมูลเรียบร้อยแล้ว";
			public static final String FAILED = "อัปโหลดข้อมูลไม่สำเร็จ";
		}
	}

	public static final String SHORT_DATE_FORMAT = "dd/MM/yyyy";
	public static final String SHORT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm";

}

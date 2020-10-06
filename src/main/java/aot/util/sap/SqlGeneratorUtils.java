package aot.util.sap;

public class SqlGeneratorUtils {

	public static String StringLIKE(String str) {
		return "%".concat(str.trim()).concat("%");
	}
	
	public static StringBuilder genSqlOneSapControl(String tableName) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT *, "
				+ " CASE "
					+ " WHEN TABLE_A.INVOICE_NO IS NULL THEN 'X' "
					+ " WHEN SAP.REVERSE_INV = 'X' THEN 'X' "
				+ " ELSE '' "
				+ " END AS REVERSE_BTN ");
		sqlBuilder.append(" FROM ").append(tableName + " AS TABLE_A ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP "
				+ " ON TABLE_A.TRANSACTION_NO = SAP.REFKEY1 ");
		return sqlBuilder;
	}
	
	public static StringBuilder genSqlTwoSapControl(String tableA, String invoiceA, String invoiceB) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT *, "
				+ " CASE "
					+ " WHEN TABLE_A." + invoiceA + " IS NULL THEN 'X' "
					+ " WHEN SAP_A.REVERSE_INV = 'X' THEN 'X' "
				+ " ELSE '' "
				+ " END AS REVERSE_BTN_A, ");
		sqlBuilder.append(" CASE "
					+ " WHEN TABLE_A. " + invoiceB + " IS NULL THEN 'X' "
					+ " WHEN SAP_B.REVERSE_INV = 'X' THEN 'X' "
				+ " ELSE '' "
				+ " END AS REVERSE_BTN_B ");
		sqlBuilder.append(" FROM ").append(tableA + " AS TABLE_A ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP_A "
				+ " ON TABLE_A.TRANSACTION_NO = SAP_A.REFKEY1 ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP_B "
				+ " ON TABLE_A.TRANSACTION_NO = SAP_B.REFKEY1 ");
		return sqlBuilder;
	}
	
	public static StringBuilder genSqlSapRequest(String tableA, String tableB, String tableC) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT REQ.*, SAP_CH.DZDOCNO DZDOCNO_CH, SAP_LG.DZDOCNO DZDOCNO_LG, SAP_PK.DZDOCNO DZDOCNO_PACKAGE "
				+ " ,CASE "
				+ " WHEN REQ.INVOICE_NO_CASH IS NULL THEN 'X' "
				+ " WHEN SAP_CH.REVERSE_INV = 'X' THEN 'X' "
				+ " ELSE ''"
				+ " END AS REVERSE_BTN_CASH, ");
		sqlBuilder.append(" CASE "
				+ " WHEN REQ.INVOICE_NO_LG IS NULL THEN 'X' "
				+ " WHEN SAP_LG.REVERSE_INV = 'X' THEN 'X' "
				+ " ELSE '' "
				+ " END AS REVERSE_BTN_LG, ");
		sqlBuilder.append(" CASE "
				+ " WHEN REQ.INVOICE_NO_PACKAGES IS NULL THEN 'X' "
				+ " WHEN SAP_PK.REVERSE_INV = 'X' THEN 'X' "
				+ " ELSE '' "
				+ " END AS REVERSE_BTN_PK, ");
		sqlBuilder.append(" CASE"
				+ " WHEN (SAP_CH.DZDOCNO IS NOT NULL AND SAP_LG.DZDOCNO IS NOT NULL) AND (CANC.SERIAL_NO IS NULL AND CHG.OLD_SERIAL_NO IS NULL) THEN 'X' "
				+ " ELSE '' "
				+ " END AS ALL_BTN ");
		sqlBuilder.append(" FROM ").append(tableA + " AS REQ ");
		sqlBuilder.append(" LEFT JOIN "+ tableB + " AS CANC "
				+ " ON REQ.METER_SERIAL_NO = CANC.SERIAL_NO ");
		sqlBuilder.append(" LEFT JOIN "+ tableC + " AS CHG "
				+ " ON REQ.REQ_ID = CHG.REQ_ID ");
//		+ " ON REQ.METER_SERIAL_NO = CHG.OLD_SERIAL_NO ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP_CH "
				+ " ON REQ.TRANSACTION_NO_CASH = SAP_CH.REFKEY1 ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP_LG "
				+ " ON REQ.TRANSACTION_NO_LG = SAP_LG.REFKEY1 ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP_PK "
				+ " ON REQ.TRANSACTION_NO_PACKAGES = SAP_PK.REFKEY1 ");
//		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP_CC "
//				+ " ON CANC.TRANSACTION_NO_LG = SAP_CC.REFKEY1 ");
		return sqlBuilder;
	}
	
	public static StringBuilder genSqlSapReqCommunicate(String tableName) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT *, "
				+ " CASE "
					+ " WHEN TABLE_A.INVOICE_NO IS NULL THEN 'X' "
					+ " WHEN SAP.REVERSE_INV = 'X' THEN 'X' "
				+ " ELSE '' "
				+ " END AS REVERSE_BTN ");
		sqlBuilder.append(" ,CASE "
					+ " WHEN SAP.DZDOCNO IS NOT NULL AND TABLE_A.FLAG_CANCEL = 'N' THEN 'X' "
				+ " ELSE '' "
				+ " END AS CANCEL_BTN ");
		sqlBuilder.append(" ,CASE "
				+ " WHEN FORMAT (TABLE_A.END_DATE, 'yyyyMMdd') < FORMAT (getdate(), 'yyyyMMdd') THEN 'X' "
			+ " ELSE '' "
			+ " END AS FLAG_ENDDATE ");
		sqlBuilder.append(" FROM ").append(tableName + " AS TABLE_A ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP "
				+ " ON TABLE_A.TRANSACTION_NO = SAP.REFKEY1 ");
		return sqlBuilder;
	}
	
	public static StringBuilder genSqlCommunicateInfo(String tableInfo, String tableCancel) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT *, "
				+ " CASE "
					+ " WHEN INFO.INVOICE_NO IS NULL THEN 'X' "
					+ " WHEN SAP.REVERSE_INV = 'X' THEN 'X' "
				+ " ELSE '' "
				+ " END AS REVERSE_BTN ");
		sqlBuilder.append(" ,CASE "
				+ " WHEN FORMAT (CANCEL.END_DATE, 'yyyyMMdd') < FORMAT (getdate(), 'yyyyMMdd') THEN 'X' "
				+ " WHEN FORMAT (CANCEL.CANCEL_DATE, 'yyyyMMdd') < FORMAT (getdate(), 'yyyyMMdd') THEN 'X' "
			+ " ELSE '' "
			+ " END AS FLAG_ENDDATE ");
		sqlBuilder.append(" FROM ").append(tableInfo + " AS INFO ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP "
				+ " ON INFO.TRANSACTION_NO = SAP.REFKEY1 ");
		sqlBuilder.append(" LEFT JOIN ").append(tableCancel + " AS CANCEL "
				+ " ON INFO.TRANSACTION_NO_REQ = CANCEL.TRANSACTION_NO ");
		return sqlBuilder;
	}
	
//	public static void main(String[] args) {
//		System.out.println(genSqlOneSapControl("RIC_ELECTRIC_INFO"));
//		System.out.println(genSqlTwoSapControl("RIC_WATER_REQ_CHANGE", "LG_INVOICE_NO", "INVOICE_NO_IB"));
//		System.out.println(genSqlSapRequest("RIC_WATER_REQ", "RIC_WATER_REQ_CANCEL", "RIC_WATER_REQ_CHANGE"));
//	}
}

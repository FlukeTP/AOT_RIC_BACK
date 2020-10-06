package aot.sap.vo;

import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;

public class SapConnectionVo {
	private SapResponse dataRes;
	private ArRequest dataSend;
	private String tableName;
	private Long id;
	private String columnId;
	private String columnTransNo;
	private String columnInvoiceNo;
	private String columnSapJsonRes;
	private String columnSapJsonReq;
	private String columnSapError;
	private String columnSapStatus;

	public SapResponse getDataRes() {
		return dataRes;
	}

	public void setDataRes(SapResponse dataRes) {
		this.dataRes = dataRes;
	}

	public ArRequest getDataSend() {
		return dataSend;
	}

	public void setDataSend(ArRequest dataSend) {
		this.dataSend = dataSend;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnTransNo() {
		return columnTransNo;
	}

	public void setColumnTransNo(String columnTransNo) {
		this.columnTransNo = columnTransNo;
	}

	public String getColumnInvoiceNo() {
		return columnInvoiceNo;
	}

	public void setColumnInvoiceNo(String columnInvoiceNo) {
		this.columnInvoiceNo = columnInvoiceNo;
	}

	public String getColumnSapJsonRes() {
		return columnSapJsonRes;
	}

	public void setColumnSapJsonRes(String columnSapJsonRes) {
		this.columnSapJsonRes = columnSapJsonRes;
	}

	public String getColumnSapJsonReq() {
		return columnSapJsonReq;
	}

	public void setColumnSapJsonReq(String columnSapJsonReq) {
		this.columnSapJsonReq = columnSapJsonReq;
	}

	public String getColumnSapError() {
		return columnSapError;
	}

	public void setColumnSapError(String columnSapError) {
		this.columnSapError = columnSapError;
	}

	public String getColumnSapStatus() {
		return columnSapStatus;
	}

	public void setColumnSapStatus(String columnSapStatus) {
		this.columnSapStatus = columnSapStatus;
	}

}

package aot.util.sap.domain.request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header {

    @SerializedName("transactionNo")
    @Expose
    private String transactionNo;
    @SerializedName("itemNo")
    @Expose
    private String itemNo;
    @SerializedName("comCode")
    @Expose
    private String comCode;
    @SerializedName("docType")
    @Expose
    private String docType;
    @SerializedName("postingDate")
    @Expose
    private String postingDate;
    @SerializedName("docDate")
    @Expose
    private String docDate;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("docHeaderText")
    @Expose
    private String docHeaderText;
    @SerializedName("busPlace")
    @Expose
    private String busPlace;
    @SerializedName("refKeyHeader1")
    @Expose
    private String refKeyHeader1;
    @SerializedName("refKeyHeader2")
    @Expose
    private String refKeyHeader2;
    @SerializedName("parkDocument")
    @Expose
    private String parkDocument;
    @SerializedName("printReceipt")
    @Expose
    private String printReceipt;
    @SerializedName("item")
    @Expose
    private List<Item> item = null;

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDocHeaderText() {
        return docHeaderText;
    }

    public void setDocHeaderText(String docHeaderText) {
        this.docHeaderText = docHeaderText;
    }

    public String getBusPlace() {
        return busPlace;
    }

    public void setBusPlace(String busPlace) {
        this.busPlace = busPlace;
    }

    public String getRefKeyHeader1() {
        return refKeyHeader1;
    }

    public void setRefKeyHeader1(String refKeyHeader1) {
        this.refKeyHeader1 = refKeyHeader1;
    }

    public String getRefKeyHeader2() {
        return refKeyHeader2;
    }

    public void setRefKeyHeader2(String refKeyHeader2) {
        this.refKeyHeader2 = refKeyHeader2;
    }

    public String getParkDocument() {
        return parkDocument;
    }

    public void setParkDocument(String parkDocument) {
        this.parkDocument = parkDocument;
    }

    public String getPrintReceipt() {
        return printReceipt;
    }

    public void setPrintReceipt(String printReceipt) {
        this.printReceipt = printReceipt;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

}


package aot.util.sap.domain.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("postingKey")
    @Expose
    private String postingKey;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("spGL")
    @Expose
    private String spGL;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("taxCode")
    @Expose
    private String taxCode;
    @SerializedName("taxBaseAmount")
    @Expose
    private String taxBaseAmount;
    @SerializedName("alternativeRecon")
    @Expose
    private String alternativeRecon;
    @SerializedName("paymentTerm")
    @Expose
    private String paymentTerm;
    @SerializedName("pmtMethod")
    @Expose
    private String pmtMethod;
    @SerializedName("customerBranch")
    @Expose
    private String customerBranch;
    @SerializedName("taxNumber3")
    @Expose
    private String taxNumber3;
    @SerializedName("name1")
    @Expose
    private String name1;
    @SerializedName("name2")
    @Expose
    private String name2;
    @SerializedName("name3")
    @Expose
    private String name3;
    @SerializedName("name4")
    @Expose
    private String name4;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("taxNumber5")
    @Expose
    private String taxNumber5;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("WHTType1")
    @Expose
    private String wHTType1;
    @SerializedName("WHTCode1")
    @Expose
    private String wHTCode1;
    @SerializedName("WHTBaseAmount1")
    @Expose
    private String wHTBaseAmount1;
    @SerializedName("WHTType2")
    @Expose
    private String wHTType2;
    @SerializedName("WHTCode2")
    @Expose
    private String wHTCode2;
    @SerializedName("WHTBaseAmount2")
    @Expose
    private String wHTBaseAmount2;
    @SerializedName("assignment")
    @Expose
    private String assignment;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("referenceKey1")
    @Expose
    private String referenceKey1;
    @SerializedName("referenceKey2")
    @Expose
    private String referenceKey2;
    @SerializedName("referenceKey3")
    @Expose
    private String referenceKey3;
    @SerializedName("profitCenter")
    @Expose
    private String profitCenter;
    @SerializedName("paService")
    @Expose
    private String paService;
    @SerializedName("paChargesRate")
    @Expose
    private String paChargesRate;
    @SerializedName("contractNo")
    @Expose
    private String contractNo;
    @SerializedName("invoiceRef")
    @Expose
    private String invoiceRef;
    @SerializedName("fiscalYear")
    @Expose
    private String fiscalYear;
    @SerializedName("lineItem")
    @Expose
    private String lineItem;
    @SerializedName("textApplicationObject")
    @Expose
    private String textApplicationObject;
    @SerializedName("textID")
    @Expose
    private String textID;
    @SerializedName("longText")
    @Expose
    private String longText;
    @SerializedName("calculateTax")
    @Expose
    private String calculateTax;

    public String getPostingKey() {
        return postingKey;
    }

    public void setPostingKey(String postingKey) {
        this.postingKey = postingKey;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSpGL() {
        return spGL;
    }

    public void setSpGL(String spGL) {
        this.spGL = spGL;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxBaseAmount() {
        return taxBaseAmount;
    }

    public void setTaxBaseAmount(String taxBaseAmount) {
        this.taxBaseAmount = taxBaseAmount;
    }

    public String getAlternativeRecon() {
        return alternativeRecon;
    }

    public void setAlternativeRecon(String alternativeRecon) {
        this.alternativeRecon = alternativeRecon;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getPmtMethod() {
        return pmtMethod;
    }

    public void setPmtMethod(String pmtMethod) {
        this.pmtMethod = pmtMethod;
    }

    public String getCustomerBranch() {
        return customerBranch;
    }

    public void setCustomerBranch(String customerBranch) {
        this.customerBranch = customerBranch;
    }

    public String getTaxNumber3() {
        return taxNumber3;
    }

    public void setTaxNumber3(String taxNumber3) {
        this.taxNumber3 = taxNumber3;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getName4() {
        return name4;
    }

    public void setName4(String name4) {
        this.name4 = name4;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTaxNumber5() {
        return taxNumber5;
    }

    public void setTaxNumber5(String taxNumber5) {
        this.taxNumber5 = taxNumber5;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWHTType1() {
        return wHTType1;
    }

    public void setWHTType1(String wHTType1) {
        this.wHTType1 = wHTType1;
    }

    public String getWHTCode1() {
        return wHTCode1;
    }

    public void setWHTCode1(String wHTCode1) {
        this.wHTCode1 = wHTCode1;
    }

    public String getWHTBaseAmount1() {
        return wHTBaseAmount1;
    }

    public void setWHTBaseAmount1(String wHTBaseAmount1) {
        this.wHTBaseAmount1 = wHTBaseAmount1;
    }

    public String getWHTType2() {
        return wHTType2;
    }

    public void setWHTType2(String wHTType2) {
        this.wHTType2 = wHTType2;
    }

    public String getWHTCode2() {
        return wHTCode2;
    }

    public void setWHTCode2(String wHTCode2) {
        this.wHTCode2 = wHTCode2;
    }

    public String getWHTBaseAmount2() {
        return wHTBaseAmount2;
    }

    public void setWHTBaseAmount2(String wHTBaseAmount2) {
        this.wHTBaseAmount2 = wHTBaseAmount2;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReferenceKey1() {
        return referenceKey1;
    }

    public void setReferenceKey1(String referenceKey1) {
        this.referenceKey1 = referenceKey1;
    }

    public String getReferenceKey2() {
        return referenceKey2;
    }

    public void setReferenceKey2(String referenceKey2) {
        this.referenceKey2 = referenceKey2;
    }

    public String getReferenceKey3() {
        return referenceKey3;
    }

    public void setReferenceKey3(String referenceKey3) {
        this.referenceKey3 = referenceKey3;
    }

    public String getProfitCenter() {
        return profitCenter;
    }

    public void setProfitCenter(String profitCenter) {
        this.profitCenter = profitCenter;
    }

    public String getPaService() {
        return paService;
    }

    public void setPaService(String paService) {
        this.paService = paService;
    }

    public String getPaChargesRate() {
        return paChargesRate;
    }

    public void setPaChargesRate(String paChargesRate) {
        this.paChargesRate = paChargesRate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getInvoiceRef() {
        return invoiceRef;
    }

    public void setInvoiceRef(String invoiceRef) {
        this.invoiceRef = invoiceRef;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getLineItem() {
        return lineItem;
    }

    public void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }

    public String getTextApplicationObject() {
        return textApplicationObject;
    }

    public void setTextApplicationObject(String textApplicationObject) {
        this.textApplicationObject = textApplicationObject;
    }

    public String getTextID() {
        return textID;
    }

    public void setTextID(String textID) {
        this.textID = textID;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public String getCalculateTax() {
        return calculateTax;
    }

    public void setCalculateTax(String calculateTax) {
        this.calculateTax = calculateTax;
    }

}

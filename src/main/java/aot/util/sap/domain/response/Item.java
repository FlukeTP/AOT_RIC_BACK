
package aot.util.sap.domain.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("TYPE")
    @Expose
    private String tYPE;
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("NUMBER")
    @Expose
    private String nUMBER;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("LOG_NO")
    @Expose
    private String lOGNO;
    @SerializedName("LOG_MSG_NO")
    @Expose
    private String lOGMSGNO;
    @SerializedName("MESSAGE_V1")
    @Expose
    private String mESSAGEV1;
    @SerializedName("MESSAGE_V2")
    @Expose
    private String mESSAGEV2;
    @SerializedName("MESSAGE_V3")
    @Expose
    private String mESSAGEV3;
    @SerializedName("MESSAGE_V4")
    @Expose
    private String mESSAGEV4;

    public String getTYPE() {
        return tYPE;
    }

    public void setTYPE(String tYPE) {
        this.tYPE = tYPE;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getNUMBER() {
        return nUMBER;
    }

    public void setNUMBER(String nUMBER) {
        this.nUMBER = nUMBER;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public String getLOGNO() {
        return lOGNO;
    }

    public void setLOGNO(String lOGNO) {
        this.lOGNO = lOGNO;
    }

    public String getLOGMSGNO() {
        return lOGMSGNO;
    }

    public void setLOGMSGNO(String lOGMSGNO) {
        this.lOGMSGNO = lOGMSGNO;
    }

    public String getMESSAGEV1() {
        return mESSAGEV1;
    }

    public void setMESSAGEV1(String mESSAGEV1) {
        this.mESSAGEV1 = mESSAGEV1;
    }

    public String getMESSAGEV2() {
        return mESSAGEV2;
    }

    public void setMESSAGEV2(String mESSAGEV2) {
        this.mESSAGEV2 = mESSAGEV2;
    }

    public String getMESSAGEV3() {
        return mESSAGEV3;
    }

    public void setMESSAGEV3(String mESSAGEV3) {
        this.mESSAGEV3 = mESSAGEV3;
    }

    public String getMESSAGEV4() {
        return mESSAGEV4;
    }

    public void setMESSAGEV4(String mESSAGEV4) {
        this.mESSAGEV4 = mESSAGEV4;
    }

}

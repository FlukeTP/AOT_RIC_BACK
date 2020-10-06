package aot.util.sap.domain.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SAPARResponseFailObj {

    @SerializedName("TRANSNO")
    @Expose
    private String tRANSNO;
    @SerializedName("COMP")
    @Expose
    private String cOMP;
    @SerializedName("DOCNO")
    @Expose
    private String dOCNO;
    @SerializedName("YEAR")
    @Expose
    private String yEAR;
    @SerializedName("TAXNO")
    @Expose
    private String tAXNO;
    @SerializedName("RETURN")
    @Expose
    private RETURNObj rETURN;

    public String getTRANSNO() {
        return tRANSNO;
    }

    public void setTRANSNO(String tRANSNO) {
        this.tRANSNO = tRANSNO;
    }

    public String getCOMP() {
        return cOMP;
    }

    public void setCOMP(String cOMP) {
        this.cOMP = cOMP;
    }

    public String getDOCNO() {
        return dOCNO;
    }

    public void setDOCNO(String dOCNO) {
        this.dOCNO = dOCNO;
    }

    public String getYEAR() {
        return yEAR;
    }

    public void setYEAR(String yEAR) {
        this.yEAR = yEAR;
    }

    public String getTAXNO() {
        return tAXNO;
    }

    public void setTAXNO(String tAXNO) {
        this.tAXNO = tAXNO;
    }

    public RETURNObj getRETURN() {
        return rETURN;
    }

    public void setRETURN(RETURNObj rETURN) {
        this.rETURN = rETURN;
    }

}


package aot.util.sap.domain.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RETURN {

    @SerializedName("RETURN")
    @Expose
    private RETURN_ rETURN;
    @SerializedName("DETAIL_RETURN")
    @Expose
    private DETAILRETURN dETAILRETURN;

    public RETURN_ getRETURN() {
        return rETURN;
    }

    public void setRETURN(RETURN_ rETURN) {
        this.rETURN = rETURN;
    }

    public DETAILRETURN getDETAILRETURN() {
        return dETAILRETURN;
    }

    public void setDETAILRETURN(DETAILRETURN dETAILRETURN) {
        this.dETAILRETURN = dETAILRETURN;
    }

}

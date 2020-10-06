
package aot.util.sap.domain.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RETURNObj {

    @SerializedName("RETURN")
    @Expose
    private RETURN_Obj rETURN;
    
    @SerializedName("DETAIL_RETURN")
    @Expose
    private DETAILRETURNObj dETAILRETURN;

    public RETURN_Obj getRETURN() {
        return rETURN;
    }

    public void setRETURN(RETURN_Obj rETURN) {
        this.rETURN = rETURN;
    }

    public DETAILRETURNObj getDETAILRETURN() {
        return dETAILRETURN;
    }

    public void setDETAILRETURN(DETAILRETURNObj dETAILRETURN) {
        this.dETAILRETURN = dETAILRETURN;
    }

}

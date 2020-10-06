
package aot.util.sap.domain.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DETAILRETURNObj {

	@SerializedName("item")
	@Expose
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

 
}

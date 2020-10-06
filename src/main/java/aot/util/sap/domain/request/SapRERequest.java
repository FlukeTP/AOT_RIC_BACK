
package aot.util.sap.domain.request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SapRERequest {

	@SerializedName("data")
	@Expose
	private List<SapREdata> data;

	public List<SapREdata> getData() {
		return data;
	}

	public void setData(List<SapREdata> data) {
		this.data = data;
	}


}

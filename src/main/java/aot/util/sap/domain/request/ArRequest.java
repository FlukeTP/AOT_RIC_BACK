
package aot.util.sap.domain.request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArRequest {

	@SerializedName("Header")
	@Expose
	private List<Header> Header;

	public List<Header> getHeader() {
		return Header;
	}

	public void setHeader(List<Header> header) {
		Header = header;
	}

}

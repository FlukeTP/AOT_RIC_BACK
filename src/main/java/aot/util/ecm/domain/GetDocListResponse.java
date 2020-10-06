
package aot.util.ecm.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="getDocListResult" type="{http://tempuri.org/}ArrayOfNodeInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getDocListResult"
})
@XmlRootElement(name = "getDocListResponse")
public class GetDocListResponse {

    protected ArrayOfNodeInfo getDocListResult;

    /**
     * Gets the value of the getDocListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfNodeInfo }
     *     
     */
    public ArrayOfNodeInfo getGetDocListResult() {
        return getDocListResult;
    }

    /**
     * Sets the value of the getDocListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfNodeInfo }
     *     
     */
    public void setGetDocListResult(ArrayOfNodeInfo value) {
        this.getDocListResult = value;
    }

}


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
 *         &lt;element name="downloadDocResult" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
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
    "downloadDocResult"
})
@XmlRootElement(name = "downloadDocResponse")
public class DownloadDocResponse {

    protected byte[] downloadDocResult;

    /**
     * Gets the value of the downloadDocResult property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDownloadDocResult() {
        return downloadDocResult;
    }

    /**
     * Sets the value of the downloadDocResult property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDownloadDocResult(byte[] value) {
        this.downloadDocResult = value;
    }

}

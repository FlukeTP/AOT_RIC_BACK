
package aot.util.ecm.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="xappKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xAirport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xPeriod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xRICNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xdocName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xfileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xcontent" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
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
    "xappKey",
    "xAirport",
    "xCategory",
    "xPeriod",
    "xricNumber",
    "xdocName",
    "xfileName",
    "xcontent"
})
@XmlRootElement(name = "uploadDoc")
public class UploadDoc {

    protected String xappKey;
    protected String xAirport;
    protected String xCategory;
    protected String xPeriod;
    @XmlElement(name = "xRICNumber")
    protected String xricNumber;
    protected String xdocName;
    protected String xfileName;
    protected byte[] xcontent;

    /**
     * Gets the value of the xappKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXappKey() {
        return xappKey;
    }

    /**
     * Sets the value of the xappKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXappKey(String value) {
        this.xappKey = value;
    }

    /**
     * Gets the value of the xAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXAirport() {
        return xAirport;
    }

    /**
     * Sets the value of the xAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXAirport(String value) {
        this.xAirport = value;
    }

    /**
     * Gets the value of the xCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXCategory() {
        return xCategory;
    }

    /**
     * Sets the value of the xCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXCategory(String value) {
        this.xCategory = value;
    }

    /**
     * Gets the value of the xPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXPeriod() {
        return xPeriod;
    }

    /**
     * Sets the value of the xPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXPeriod(String value) {
        this.xPeriod = value;
    }

    /**
     * Gets the value of the xricNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXRICNumber() {
        return xricNumber;
    }

    /**
     * Sets the value of the xricNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXRICNumber(String value) {
        this.xricNumber = value;
    }

    /**
     * Gets the value of the xdocName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXdocName() {
        return xdocName;
    }

    /**
     * Sets the value of the xdocName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXdocName(String value) {
        this.xdocName = value;
    }

    /**
     * Gets the value of the xfileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXfileName() {
        return xfileName;
    }

    /**
     * Sets the value of the xfileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXfileName(String value) {
        this.xfileName = value;
    }

    /**
     * Gets the value of the xcontent property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getXcontent() {
        return xcontent;
    }

    /**
     * Sets the value of the xcontent property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setXcontent(byte[] value) {
        this.xcontent = value;
    }

}

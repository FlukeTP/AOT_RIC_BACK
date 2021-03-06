package aot.util.ecm.domain;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2019-07-28T15:13:54.741+07:00
 * Generated source version: 3.3.2
 *
 */
@WebService(targetNamespace = "http://tempuri.org/", name = "OTRICSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface OTRICSoap {

    @WebMethod(action = "http://tempuri.org/deleteDoc")
    @RequestWrapper(localName = "deleteDoc", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.DeleteDoc")
    @ResponseWrapper(localName = "deleteDocResponse", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.DeleteDocResponse")
    @WebResult(name = "deleteDocResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String deleteDoc(

        @WebParam(name = "xappKey", targetNamespace = "http://tempuri.org/")
        java.lang.String xappKey,
        @WebParam(name = "xAirport", targetNamespace = "http://tempuri.org/")
        java.lang.String xAirport,
        @WebParam(name = "xCategory", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategory,
        @WebParam(name = "xPeriod", targetNamespace = "http://tempuri.org/")
        java.lang.String xPeriod,
        @WebParam(name = "xRICNumber", targetNamespace = "http://tempuri.org/")
        java.lang.String xRICNumber,
        @WebParam(name = "xdocName", targetNamespace = "http://tempuri.org/")
        java.lang.String xdocName
    );

    @WebMethod(action = "http://tempuri.org/getDocLink")
    @RequestWrapper(localName = "getDocLink", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.GetDocLink")
    @ResponseWrapper(localName = "getDocLinkResponse", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.GetDocLinkResponse")
    @WebResult(name = "getDocLinkResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String getDocLink(

        @WebParam(name = "xappKey", targetNamespace = "http://tempuri.org/")
        java.lang.String xappKey,
        @WebParam(name = "xAirport", targetNamespace = "http://tempuri.org/")
        java.lang.String xAirport,
        @WebParam(name = "xCategory", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategory,
        @WebParam(name = "xPeriod", targetNamespace = "http://tempuri.org/")
        java.lang.String xPeriod,
        @WebParam(name = "xRICNumber", targetNamespace = "http://tempuri.org/")
        java.lang.String xRICNumber,
        @WebParam(name = "xdocName", targetNamespace = "http://tempuri.org/")
        java.lang.String xdocName
    );

    @WebMethod(action = "http://tempuri.org/createRICFolder")
    @RequestWrapper(localName = "createRICFolder", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.CreateRICFolder")
    @ResponseWrapper(localName = "createRICFolderResponse", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.CreateRICFolderResponse")
    @WebResult(name = "createRICFolderResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String createRICFolder(

        @WebParam(name = "xappKey", targetNamespace = "http://tempuri.org/")
        java.lang.String xappKey,
        @WebParam(name = "xAirport", targetNamespace = "http://tempuri.org/")
        java.lang.String xAirport,
        @WebParam(name = "xCategory", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategory,
        @WebParam(name = "xPeriod", targetNamespace = "http://tempuri.org/")
        java.lang.String xPeriod,
        @WebParam(name = "xRICNumber", targetNamespace = "http://tempuri.org/")
        java.lang.String xRICNumber
    );

    @WebMethod(action = "http://tempuri.org/getRICFolderLink")
    @RequestWrapper(localName = "getRICFolderLink", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.GetRICFolderLink")
    @ResponseWrapper(localName = "getRICFolderLinkResponse", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.GetRICFolderLinkResponse")
    @WebResult(name = "getRICFolderLinkResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String getRICFolderLink(

        @WebParam(name = "xappKey", targetNamespace = "http://tempuri.org/")
        java.lang.String xappKey,
        @WebParam(name = "xAirport", targetNamespace = "http://tempuri.org/")
        java.lang.String xAirport,
        @WebParam(name = "xCategory", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategory,
        @WebParam(name = "xPeriod", targetNamespace = "http://tempuri.org/")
        java.lang.String xPeriod,
        @WebParam(name = "xRICNumber", targetNamespace = "http://tempuri.org/")
        java.lang.String xRICNumber
    );

    @WebMethod(action = "http://tempuri.org/updateCategoryData")
    @RequestWrapper(localName = "updateCategoryData", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.UpdateCategoryData")
    @ResponseWrapper(localName = "updateCategoryDataResponse", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.UpdateCategoryDataResponse")
    @WebResult(name = "updateCategoryDataResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String updateCategoryData(

        @WebParam(name = "xappKey", targetNamespace = "http://tempuri.org/")
        java.lang.String xappKey,
        @WebParam(name = "xAirport", targetNamespace = "http://tempuri.org/")
        java.lang.String xAirport,
        @WebParam(name = "xCategory", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategory,
        @WebParam(name = "xPeriod", targetNamespace = "http://tempuri.org/")
        java.lang.String xPeriod,
        @WebParam(name = "xRICNumber", targetNamespace = "http://tempuri.org/")
        java.lang.String xRICNumber,
        @WebParam(name = "xdocName", targetNamespace = "http://tempuri.org/")
        java.lang.String xdocName,
        @WebParam(name = "xCategoryTemplateName", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategoryTemplateName,
        @WebParam(name = "xAttributeValues", targetNamespace = "http://tempuri.org/")
        java.lang.String xAttributeValues
    );

    @WebMethod(action = "http://tempuri.org/downloadDoc")
    @RequestWrapper(localName = "downloadDoc", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.DownloadDoc")
    @ResponseWrapper(localName = "downloadDocResponse", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.DownloadDocResponse")
    @WebResult(name = "downloadDocResult", targetNamespace = "http://tempuri.org/")
    public byte[] downloadDoc(

        @WebParam(name = "xappKey", targetNamespace = "http://tempuri.org/")
        java.lang.String xappKey,
        @WebParam(name = "xAirport", targetNamespace = "http://tempuri.org/")
        java.lang.String xAirport,
        @WebParam(name = "xCategory", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategory,
        @WebParam(name = "xPeriod", targetNamespace = "http://tempuri.org/")
        java.lang.String xPeriod,
        @WebParam(name = "xRICNumber", targetNamespace = "http://tempuri.org/")
        java.lang.String xRICNumber,
        @WebParam(name = "xdocName", targetNamespace = "http://tempuri.org/")
        java.lang.String xdocName
    );

    @WebMethod(action = "http://tempuri.org/uploadDoc")
    @RequestWrapper(localName = "uploadDoc", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.UploadDoc")
    @ResponseWrapper(localName = "uploadDocResponse", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.UploadDocResponse")
    @WebResult(name = "uploadDocResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String uploadDoc(

        @WebParam(name = "xappKey", targetNamespace = "http://tempuri.org/")
        java.lang.String xappKey,
        @WebParam(name = "xAirport", targetNamespace = "http://tempuri.org/")
        java.lang.String xAirport,
        @WebParam(name = "xCategory", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategory,
        @WebParam(name = "xPeriod", targetNamespace = "http://tempuri.org/")
        java.lang.String xPeriod,
        @WebParam(name = "xRICNumber", targetNamespace = "http://tempuri.org/")
        java.lang.String xRICNumber,
        @WebParam(name = "xdocName", targetNamespace = "http://tempuri.org/")
        java.lang.String xdocName,
        @WebParam(name = "xfileName", targetNamespace = "http://tempuri.org/")
        java.lang.String xfileName,
        @WebParam(name = "xcontent", targetNamespace = "http://tempuri.org/")
        byte[] xcontent
    );

    @WebMethod(action = "http://tempuri.org/getDocList")
    @RequestWrapper(localName = "getDocList", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.GetDocList")
    @ResponseWrapper(localName = "getDocListResponse", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.GetDocListResponse")
    @WebResult(name = "getDocListResult", targetNamespace = "http://tempuri.org/")
    public aot.util.ecm.domain.ArrayOfNodeInfo getDocList(

        @WebParam(name = "xappKey", targetNamespace = "http://tempuri.org/")
        java.lang.String xappKey,
        @WebParam(name = "xAirport", targetNamespace = "http://tempuri.org/")
        java.lang.String xAirport,
        @WebParam(name = "xCategory", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategory,
        @WebParam(name = "xPeriod", targetNamespace = "http://tempuri.org/")
        java.lang.String xPeriod,
        @WebParam(name = "xRICNumber", targetNamespace = "http://tempuri.org/")
        java.lang.String xRICNumber
    );

    @WebMethod(action = "http://tempuri.org/deleteRICFolder")
    @RequestWrapper(localName = "deleteRICFolder", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.DeleteRICFolder")
    @ResponseWrapper(localName = "deleteRICFolderResponse", targetNamespace = "http://tempuri.org/", className = "com.aot.ecm.wsdlgen.DeleteRICFolderResponse")
    @WebResult(name = "deleteRICFolderResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String deleteRICFolder(

        @WebParam(name = "xappKey", targetNamespace = "http://tempuri.org/")
        java.lang.String xappKey,
        @WebParam(name = "xAirport", targetNamespace = "http://tempuri.org/")
        java.lang.String xAirport,
        @WebParam(name = "xCategory", targetNamespace = "http://tempuri.org/")
        java.lang.String xCategory,
        @WebParam(name = "xPeriod", targetNamespace = "http://tempuri.org/")
        java.lang.String xPeriod,
        @WebParam(name = "xRICNumber", targetNamespace = "http://tempuri.org/")
        java.lang.String xRICNumber
    );
}

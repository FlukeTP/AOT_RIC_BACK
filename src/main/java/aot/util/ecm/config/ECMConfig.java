package aot.util.ecm.config;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import aot.common.constant.RICConstants;
import aot.util.ecm.domain.OTRICSoap;
import baiwa.common.util.paramconfig.ParamConfig;

@Configuration
public class ECMConfig {
	
	@Autowired
	private ParamConfig paramConfig;
	
	@Bean(name = "wsECM")
	public OTRICSoap wsECM() {
		
		final String uriEndpoint = paramConfig.getParamConfig(RICConstants.ECM_URL);
		System.out.println("wsECM :"+uriEndpoint);
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(OTRICSoap.class);
		jaxWsProxyFactoryBean.setAddress(uriEndpoint);

		OTRICSoap wsProxy = (OTRICSoap) jaxWsProxyFactoryBean.create();

		Client client = ClientProxy.getClient(wsProxy);
		HTTPConduit http = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(36000);
		httpClientPolicy.setReceiveTimeout(36000);
		httpClientPolicy.setAllowChunking(false);
		http.setClient(httpClientPolicy);
		
		client.getInInterceptors().add(new LoggingInInterceptor());
		client.getOutInterceptors().add(new LoggingOutInterceptor());

		return wsProxy;
	}
}

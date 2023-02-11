package io.antmedia.plugin;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.antmedia.AppSettings;
import io.antmedia.datastore.db.DataStoreFactory;
import io.antmedia.db.CRUDMongoStore;

@Component(value="apps.crud")
public class CRUDMain implements ApplicationContextAware{

	public static final String BEAN_NAME = "apps.crud";
	CRUDMongoStore store;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		//AppSettings appSettings = (AppSettings) applicationContext.getBean(AppSettings.BEAN_NAME);
		
		DataStoreFactory dsf = (DataStoreFactory) applicationContext.getBean(DataStoreFactory.BEAN_NAME);
		store = new CRUDMongoStore("172.26.0.2"/*dsf.getDbHost()*/, ""/*dsf.getDbUser()*/, "" /*TODOdsf.getDbUser()*/, "CRUDstore");
	}

	public CRUDMongoStore getStore() {
		return store;
	}

}

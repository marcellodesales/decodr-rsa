/*
 * Created on 28/11/2003
 * Cryptography on the Web
 */
package net.jsurfer.cryptonline.i18n;

import java.util.Locale;

import net.jsurfer.i18n.tmx.XMLResourceBundle;
import net.jsurfer.i18n.tmx.XMLPropertyResourceBundle;

/**
 * @author Marcello Junior on 28/11/2003
 * Cryptography on the Web
 * This class is an Abstract Factory for XMLResourceBundle class. It builds 
 * different XMLResourceBundles according to the type of message.
 */
public class XMLResourceBundleFactory {

	XMLPropertyResourceBundle usedBundle;
	
	private final String resourcesDir = "resources/";
	
	private final String exceptions = this.resourcesDir+"exceptions.tmx";
	private final String general = this.resourcesDir+"general.tmx";
	
	public XMLResourceBundleFactory(String type, Locale currentLocale){
		
		type = (type.toLowerCase()).trim();
		
		if (type.equals("exception")){
			usedBundle = XMLResourceBundle.getBundle(this.resourcesDir+"exceptions.tmx", currentLocale);
		} else
		if (type.equals("general")){
			usedBundle = XMLResourceBundle.getBundle("resources/exceptions.tmx", currentLocale);
		}
	}
	
	public String getMessage(String key){
		return (String)this.usedBundle.handleGetObject(key);
	}
}

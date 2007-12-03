package net.jsurfer.cryptonline.persistence;

import java.util.Locale;

import net.jsurfer.cryptonline.i18n.XMLResourceBundleFactory;
//import net.sf.hibernate.HibernateException;

/**
 * $Id: PersistenceLayerException.java,v 1.1 2003/12/01 04:29:56 cryptonline Exp $
 *
 * @author Marcello Junior
 * @version $Revision: 1.1 $
 */
public class PersistenceLayerException extends Exception {
		
	String key;
	
	public PersistenceLayerException(String message, Throwable throwable){
		super(message,throwable);
	}
	
	public PersistenceLayerException(String key){
		super("Error on the Persistence Layer");
		this.key = key;		
	}
	
	public String getLocalizedMessage(Locale currentLocale){
		XMLResourceBundleFactory exceptionsBundle = new XMLResourceBundleFactory("exception",currentLocale);
		return exceptionsBundle.getMessage(this.key);
	}
	
	public String getMessage(){
		return this.getLocalizedMessage(Locale.getDefault());
	}
}


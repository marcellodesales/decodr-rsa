/*
 * Criado em 19/11/2003
 * 
 *
 * Marcello Junior Marcello Junior
 * javaman@moomia.com
 */
package net.jsurfer.cryptonline.persistence;

import java.util.Map;

/**
 * PatternBox:Adapter: "Target" implementation.
 * <ul>
 *   <li>defines the domain-specific interface that Client uses.</li>
 * </ul>
 * 
 * @author <a href="mailto:dirk.ehms@patternbox.com">Dirk Ehms</a>
 * @author Marcello Junior
 * @version 1.2
 * 
 */
public interface PersistenceLayer {

	/**
	 * Saves the current state of the the @param persistentObject
	 * in the persistent layer.    
	 * @param persistentObject: a persistent object mapped to a persistence layer
	 * @throws PersistenceLayerException
	 */
	public void saveObject(Object persistentObject) throws PersistenceLayerException;
		
	/**
	 * Removes the reference of the persistenObject from the persistent 
	 * layer.
	 * @param persistentObject
	 * @throws PersistenceLayerException 
	 */
	public boolean deleteObject(Object persistentObject) throws PersistenceLayerException;;
	
	
	/** 
	 * Finds a persistent object instance with the object reference identification.
	 * @param oid: It's the reference of the persistent object.
	 * @param classe: the class the objects is from.
	 * @return An object instance with the correct type class. It must be cast.
	 * @throws PersistenceLayerException: In case the object is not found.
	 */
	public Object find(String oid, Class classe) throws PersistenceLayerException;
	
	/**
	 * Finds a persistent object instance with a given attribute=value.
	 * @param attribute: It's a property the required class has. 
	 * @param value: the value the property must have.
	 * @param classe: the class the object is from. 
	 * @return An object instance with the correct class. It must be cast 
	 * @throws PersistenceLayerException: In case the object is not found. 
	 */
	public Object find(String attribute, Object value, Class classe) throws PersistenceLayerException;
	
	/**
	 * Finds a persistent object instance with a given attribute=value
	 * set, mapped in a Hashtable.
	 * @param valueSet: a map implementation. It can be a Hashtable.
	 * @param classe: the class the object is from. 
	 * @return An object instance with the correct class. It must be cast 
	 * @throws PersistenceLayerException: In case the object is not found. 
	 */
	public Object find(Map valueSet, Class classe) throws PersistenceLayerException;
}

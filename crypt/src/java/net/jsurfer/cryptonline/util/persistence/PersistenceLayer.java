/*
 * Criado em 19/11/2003
 * 
 *
 * Marcello Junior Marcello Junior
 * javaman@moomia.com
 */
package net.jsurfer.cryptonline.util.persistence;

import java.util.List;
import java.util.Map;

/**
 * @author Marcello Junior
 * @version 1.2
 * 
 */
public interface PersistenceLayer {

	/**
	 * Saves the current state of the given persistentObject
	 * in the persistent layer.    
	 * @param persistentObject: a persistent object mapped to a persistence layer
	 * @throws PersistenceLayerException
	 */
	public void saveObject(Object persistentObject) throws PersistenceLayerException;

    /**
     * Updates the current state of the given persistent object
     * in the persistent layer.    
     * @param persistentObject: a persistent object mapped to a persistence layer
     * @throws PersistenceLayerException
     */
	public void updateObject(Object persistentObject) throws PersistenceLayerException;
	
	/**
	 * Removes the reference of the persistenObject from the persistent 
	 * layer.
	 * @param persistentObject
	 * @throws PersistenceLayerException 
	 */
	public boolean deleteObject(Object persistentObject) throws PersistenceLayerException;
	
	
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
	
    /**
     * @param attribute
     * @param value
     * @param classe
     * @return
     * @throws PersistenceLayerException
     */
    public List findList(String attribute, Object value, Class classe) throws PersistenceLayerException;

}

package net.jsurfer.cryptonline.util.persistence.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jsurfer.cryptonline.util.persistence.PersistenceLayer;
import net.jsurfer.cryptonline.util.persistence.PersistenceLayerException;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;

/**
/*
 * Criado em 20/11/2003
 *
 * @author Marcello Junior
 */
public class HibernatePersistenceAdapter implements PersistenceLayer {

	private final HibernatePersistence hibernatorPersistenceAdaptee;

	private static HibernatePersistenceAdapter sInstance = new HibernatePersistenceAdapter();

	private HibernatePersistenceAdapter() {
		hibernatorPersistenceAdaptee = HibernatePersistence.getInstance();
	}

	/**
	 * Get the unique instance of this class.
	 */
	public static synchronized HibernatePersistenceAdapter getInstance() {
		return sInstance;
	}

	/* (n�o-Javadoc)
	 * @see br.ufal.cryptonline.designpattern.PersistenceLayer#saveObject(java.lang.Object)
	 */
	public void saveObject(Object persistentObject) throws PersistenceLayerException {
		Session session = null;
		Transaction tx = null;
		try{
			session = this.hibernatorPersistenceAdaptee.getSession();
			tx = session.beginTransaction();
			session.save(persistentObject);
			tx.commit();

		} catch (HibernateException he) {
		    //he.printStackTrace();
			if (tx != null) {
				try {
					tx.rollback();
					throw new PersistenceLayerException(he.getMessage(), he);
				} catch (HibernateException ohe) {
					throw new PersistenceLayerException(ohe.getMessage(), ohe);
				}
			}
		}
//		} finally {
//			try {
//				session.close();
//			} catch (HibernateException e) {
//			    throw new PersistenceLayerException("Trying to close the database session", e);
//			}
//		}
	}

	   public void updateObject(Object persistentObject) throws PersistenceLayerException {
	        Session session = null;
	        Transaction tx = null;
	        try {
	            session = this.hibernatorPersistenceAdaptee.getSession();
	            tx = session.beginTransaction();
	            session.update(persistentObject);
	            tx.commit();

	        } catch (HibernateException he) {
	            he.printStackTrace();
	            if (tx != null) {
	                try {
	                    tx.rollback();
	                    throw new PersistenceLayerException(he.getMessage(), he);
	                } catch (HibernateException ohe) {
	                    throw new PersistenceLayerException(ohe.getMessage(), ohe);
	                }
	            }
	        }
//	        } finally {
//	            try {
//	                session.close();
//	            } catch (HibernateException e) {
//	                throw new PersistenceLayerException("Trying to close the database session", e);
//	            }
//	        }
	    }


	/* (n�o-Javadoc)
	 * @see br.ufal.cryptonline.designpattern.PersistenceLayer#deleteObject(java.lang.Object)
	 */
	public boolean deleteObject(Object persistentObject) throws PersistenceLayerException{
//		try {
//			Session ss = this.hibernatorPersistenceAdaptee.getSession();
//			ss.beginTransaction();
//			ss.delete(persistentObject);
//			ss.flush();
//			return true;
//		} catch (HibernateException he) {
//			throw new PersistenceLayerException(he.getMessage(), he);
//		}
        Session session = null;
        Transaction tx = null;
        try {
            session = this.hibernatorPersistenceAdaptee.getSession();
            tx = session.beginTransaction();
            session.delete(persistentObject);
            tx.commit();
            return true;
        } catch (HibernateException he) {
            he.printStackTrace();
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (HibernateException ohe) {
                    throw new PersistenceLayerException(ohe.getMessage(), ohe);
                }
                return true;
            }
            return true;
        }
	}

	/* (n�o-Javadoc)
	 * @see br.ufal.cryptonline.designpattern.PersistenceLayer#find(java.lang.String, java.lang.Class)
	 */
	public Object find(String oid, Class classe) throws PersistenceLayerException {
		try{
			Object objFound = null;
			Session session = this.hibernatorPersistenceAdaptee.getSession();
			objFound = session.load(classe, oid);
			return objFound;

		}catch(HibernateException he){
			throw new PersistenceLayerException("There is no object with the identifier "+oid+", of "+classe, he);
		}
	}

	/* (n�o-Javadoc)
	 * @see br.ufal.cryptonline.designpattern.PersistenceLayer#find(java.lang.String, java.lang.Object, java.lang.Class)
	 */
	public Object find(String attribute, Object value, Class classe) throws PersistenceLayerException {
		try {
			Session session = this.hibernatorPersistenceAdaptee.getSession();
			Criteria creteria = session.createCriteria(classe).add(Expression.eq(attribute, value));
			List instances = creteria.list();
			Object foundObject = null;
			if (instances.size() == 1){
				foundObject = instances.get(0);
			}
			return foundObject;
		} catch (HibernateException he){
			throw new PersistenceLayerException(he.getMessage(), he);
		}
	}

	/* (n�o-Javadoc)
	 * @see br.ufal.cryptonline.designpattern.PersistenceLayer#find(java.util.Map, java.lang.Class)
	 */
	public Object find(Map valueSet, Class classe) throws PersistenceLayerException {
		try{
			Session session = this.hibernatorPersistenceAdaptee.getSession();
			Criteria creteria = session.createCriteria(classe);

			Iterator iter = valueSet.keySet().iterator();
			String key = "";
			String value = "";

			while (iter.hasNext()) {
				key   = (String)iter.next();
				value = (String)valueSet.get(key);
				creteria.add(Expression.eq(key, value));
			}

			List instances = creteria.list();
			Object instance = null;
			if (instances.size() == 1){
				instance = instances.get(0);
			}
			return instance;
		} catch (HibernateException he){
			throw new PersistenceLayerException(he.getMessage(), he);
		}
	}

	   /* (n�o-Javadoc)
     * @see br.ufal.cryptonline.designpattern.PersistenceLayer#find(java.lang.String, java.lang.Object, java.lang.Class)
     */
    public List findList(String attribute, Object value, Class classe) throws PersistenceLayerException {
        List<Object> objects;
        try {
            Session session = this.hibernatorPersistenceAdaptee.getSession();
            Criteria creteria = session.createCriteria(classe).add(Expression.eq(attribute, value));
            List instances = creteria.list();
            objects = new ArrayList<Object>(instances.size());
            for (int i = 0; i < instances.size(); i++) {
                objects.add(instances.get(i));
            }

            return objects;

        } catch (HibernateException he){
            throw new PersistenceLayerException(he.getMessage(), he);
        }
    }
}

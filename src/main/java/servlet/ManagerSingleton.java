package servlet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ManagerSingleton {
	
	private static EntityManager manager;
	private static EntityManagerFactory factory;
	private static Object objetSynchrone__;
	private static ManagerSingleton instance;

	public static ManagerSingleton getInstance() {
		if (null == manager && null == factory) { // Premier appel
			synchronized (objetSynchrone__) {
				if (null == manager && null == factory) {
					instance = new  ManagerSingleton();
					manager = factory.createEntityManager();
					factory = Persistence.createEntityManagerFactory("example");
				}
			}
		}
		return instance;
	}
	
	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		ManagerSingleton.manager = manager;
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

	public void setFactory(EntityManagerFactory factory) {
		ManagerSingleton.factory = factory;
	}
}

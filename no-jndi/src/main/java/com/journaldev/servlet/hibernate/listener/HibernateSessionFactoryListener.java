package com.journaldev.servlet.hibernate.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;

@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {

	public final Logger logger = Logger.getLogger(HibernateSessionFactoryListener.class);
	

	 public void contextInitialized(ServletContextEvent event) {  
		 logger.info("SessionFactory created successfully");
	        HibernateUtil.getSessionFactory(); // Just call the static initializer of that class      
	    }  
	  
	    public void contextDestroyed(ServletContextEvent event) {  
	    	logger.info("Closing sessionFactory");
	        HibernateUtil.getSessionFactory().close(); // Free all resources  
	    }  
}

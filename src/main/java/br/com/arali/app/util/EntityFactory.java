package br.com.arali.app.util;
import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class EntityFactory extends LocalContainerEntityManagerFactoryBean{
    public static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getInstance(){
        if(entityManagerFactory == null || !entityManagerFactory.isOpen()){
            EntityFactory self = new EntityFactory();
            self.setPersistenceUnitName("arali");
            self.setPersistenceXmlLocation("file:config.xml");
            entityManagerFactory = self.createNativeEntityManagerFactory();
        }
        return entityManagerFactory;
    }

    public static void setEntityManagerFactory(EntityManagerFactory emf){
        entityManagerFactory = emf;
    }
    public static void close(){
        entityManagerFactory.close();
    }
}
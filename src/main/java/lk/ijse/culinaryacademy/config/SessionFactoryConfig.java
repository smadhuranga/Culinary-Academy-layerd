package lk.ijse.culinaryacademy.config;

import lk.ijse.culinaryacademy.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryConfig {

    private static SessionFactoryConfig factoryConfig;

    private SessionFactoryConfig(){}

    public static SessionFactoryConfig getInstance() {
        return (factoryConfig == null) ? factoryConfig = new SessionFactoryConfig() : factoryConfig;
    }

    public Session getSession() {
        // Step 01 - Create StandardServiceRegistry Object
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .loadProperties("hibernate.properties").build();

        // Step 02 - Create Metadata Object
        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Enrolment.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(User.class)
                .getMetadataBuilder().build();

        // Step 03 - Create SessionFactory Object
        SessionFactory sessionFactory = metadata.buildSessionFactory();

        return sessionFactory.openSession();
    }

}
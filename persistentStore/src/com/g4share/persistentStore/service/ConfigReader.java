package com.g4share.persistentStore.service;

import com.g4share.persistentStore.pojo.RssLocation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: gm
 */
public interface ConfigReader {
    public Map<String, String> read(String prefix);

    @Service
    class PropertyConfigReader implements ConfigReader {
        private String propertyFileName;

        @Autowired
        public PropertyConfigReader(@Value("${hibernate.properties}") String propertyFileName) {
            this.propertyFileName = propertyFileName;
        }

        @Override
        public Map<String, String> read(String prefix){
            Map<String, String> lines = new HashMap<>();

    /*        Properties pro = new Properties();
            try{
                InputStream resourceUrl = getClass().getResourceAsStream(propertyFileName);
                pro.load(resourceUrl);
                Enumeration em = pro.keys();
                while(em.hasMoreElements()){
                    String str = (String)em.nextElement();
                    if (str.startsWith(prefix)){
                        lines.put(str, (String)pro.get(str));
                    }
                }
            }
            catch(IOException e){
                //todo
            }
      */
            lines.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
            lines.put("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
            lines.put("hibernate.connection.url","jdbc:mysql://192.168.1.10:3306/jNewsDB");
            lines.put("hibernate.connection.username","gm");
            lines.put("hibernate.connection.password","pWd");
            lines.put("hibernate.connection.pool_size","1");
            return lines;
        }
    }

    class HibernateSession
    {
        private final SessionFactory sessionFactory;

        public HibernateSession(ConfigReader configReader){

            Configuration configuration = new Configuration();

            for (Map.Entry<String,String> entry : configReader.read("hibernate.").entrySet()) {
                configuration.setProperty(entry.getKey(), entry.getValue());

            }
            configuration.addAnnotatedClass(RssLocation.class);


            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }


        public <T> T[] getList(Class<T> clazz, Criterion where) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(clazz);
            if (where != null){
                criteria.add(where);
            }
            List result = criteria.list();
            session.getTransaction().commit();
            session.close();

            return (T[]) result.toArray((T[]) Array.newInstance(clazz, result.size()));
        }

        public <T> void update(T obj) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(obj);
            session.getTransaction().commit();
            session.close();
            return;
        }

        public <T> void add(T obj) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
            session.close();
            return;
        }
    }
}

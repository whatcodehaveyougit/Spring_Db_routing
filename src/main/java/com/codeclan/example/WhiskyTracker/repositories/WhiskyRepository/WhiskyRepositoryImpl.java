package com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;


import com.codeclan.example.WhiskyTracker.models.Whisky;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class WhiskyRepositoryImpl implements WhiskyRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public List<Whisky> findWhiskyFromYear(int year){
        List<Whisky> result = null;

        Session session = entityManager.unwrap(Session.class);

        try{
            Criteria cr = session.createCriteria(Whisky.class);
            cr.add(Restrictions.eq("year", year));

            result = cr.list();
        }
        catch (HibernateException ex){
            ex.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    @Transactional
    public List<Whisky> findByDistilleryAndAge(Long distillery, int age){
        List<Whisky> result = null;
        Session session = entityManager.unwrap(Session.class);

        Criteria cr = session.createCriteria(Whisky.class);

        // Why distillery - that is not the table name??
        cr.createAlias("distillery", "distilleryAlias");
        cr.add(Restrictions.eq("distilleryAlias.id", distillery));
        cr.add(Restrictions.eq("age", age));

        result = cr.list();

        return result;
    }

}

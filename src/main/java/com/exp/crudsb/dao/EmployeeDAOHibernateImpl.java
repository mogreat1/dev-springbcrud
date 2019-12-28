package com.exp.crudsb.dao;

import com.exp.crudsb.entity.Employee;
import com.exp.crudsb.errorHandling.EmployeeNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {
    // define field for entityManager
    private EntityManager entityManager;
    // set up constructor injection

    @Autowired
    public EmployeeDAOHibernateImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        // get the current hibernate session
        Session session = entityManager.unwrap(Session.class);
        // create a query
       Query<Employee> query = session.createQuery("from Employee", Employee.class);
        // execute query and get result list
        List<Employee> employees = query.getResultList();
        // return the results
        return employees;
    }

    @Override
    public Employee findById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Employee employee = session.get(Employee.class, id);
        return employee;
    }

    @Override
    public void save(Employee employee) {
        Session session = entityManager.unwrap(Session.class);
        session.save(employee);
    }

    @Override
    public void update(Employee employee) {
        Session session = entityManager.unwrap(Session.class);
        List<Integer> list = verifyId();
        if(!list.contains(employee.getId())){
            throw new EmployeeNotFoundException("Employee id not found - " + employee.getId());
        }
        session.update(employee);
    }

    @Override
    public void deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("delete from Employee where id=:employeeId");
        query.setParameter("employeeId", id);
        query.executeUpdate();
    }

    public List<Integer> verifyId(){
        // method verifies if id exists in table for future UPDATE
        List<Integer> list = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("select id from Employee");
        list = query.getResultList();
        return list;
    }
}

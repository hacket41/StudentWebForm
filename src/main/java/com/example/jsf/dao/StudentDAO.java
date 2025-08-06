package com.example.jsf.dao;

import com.example.jsf.entity.Student;
import com.example.jsf.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class StudentDAO {

    public void save(Student student) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
        em.close();
    }

   
    public void update(Student student) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();

    Student managedStudent = em.find(Student.class, student.getId());
    if (managedStudent != null) {
        managedStudent.setName(student.getName());
        managedStudent.setAddress(student.getAddress());
        managedStudent.setStudentClass(student.getStudentClass());
        managedStudent.setSubject(student.getSubject());
    } else {
        // In case student not found, you could throw exception or save as new
        em.persist(student);
    }

    em.getTransaction().commit();
    em.close();
}

    public void delete(Student student) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        Student s = em.find(Student.class, student.getId());
        if (s != null) em.remove(s);
        em.getTransaction().commit();
        em.close();
    }

    public List<Student> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        List<Student> list = em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        em.close();
        return list;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Student;
import model.StudentTable;

/**
 *
 * @author Theerakan
 */
@WebService(serviceName = "StudentWebService")
public class StudentWebService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("componentBaseW13_Ex2PU");

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findStudentById")
    public Student findStudentById(@WebParam(name = "id") int id) {
       EntityManager em = emf.createEntityManager();
       Student edu = em.find(Student.class, id);
        return edu;
    }
    /**
     * Web service operation
     */
    @WebMethod(operationName = "insertStudent")
    public int insertStudent(@WebParam(name = "id") int id, @WebParam(name = "name") String name, @WebParam(name = "gpa") double gpa) {
        
         Student temp = new Student(id, name, gpa);
         EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student target = em.find(Student.class, temp.getId());
            if (target != null) {
                return 0;
            }
            em.persist(temp);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            
        }
        finally {
            em.close();
        }
        return 1;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateStudent")
    public String updateStudent(@WebParam(name = "id") int id, @WebParam(name = "name") String name, @WebParam(name = "gpa") double gpa) {
         EntityManager em = emf.createEntityManager();
         
        Student std = new Student(id, name, gpa);
        try {
            em.getTransaction().begin();
            Student target = em.find(Student.class, std.getId());
            if (target == null) {
                return "Id does'nt exist";
            }
            target.setName(std.getName());
            target.setGpa(std.getGpa());
            em.persist(target);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            
        }
        finally {
            em.close();
        }
        return "SUCCESS";
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "removeStudent")
    public String removeStudent(@WebParam(name = "id") int id) {
          EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student target = em.find(Student.class, id);
            if (target == null) {
                return "Id does'nt exist";
            }
            em.remove(target);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            
        }
        finally {
            em.close();
        }
        return "REMOVE SUCESS";
    }
    
   
     
    
    
    

    private void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findAllStudent")
    public List<Student> findAllStudent() {
        EntityManager std = emf.createEntityManager();
        List<Student> stdList = null;
        try {
            stdList = (List<Student>) std.createNamedQuery("Student.findAll").getResultList();         
        } catch (Exception e) {
            throw new RuntimeException(e);
            
        }
        finally {
            std.close();
        }
        return stdList;
    }

    

    

    
    
}

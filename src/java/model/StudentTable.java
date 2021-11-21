/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Theerakan
 */
public class StudentTable {
      private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("componentBAseW11_Ex2PU");
    
    public static List<Student> findAllStudent() {
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
    
        public static int insertStudent(Student temp) {
        EntityManager std = emf.createEntityManager();
        try {
            std.getTransaction().begin();
            Student target = std.find(Student.class, temp.getId());
            if (target != null) {
                return 0;
            }
            std.persist(temp);
            std.getTransaction().commit();
        } catch (Exception e) {
            std.getTransaction().rollback();
            
        }
        finally {
            std.close();
        }
        return 1;
    }
        
    public static Student findStudentById(int id) {
        EntityManager em = emf.createEntityManager();
        Student std = null;
        try {
            std = em.find(Student.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            em.close();
        }
        return std;
    }
    
    public static int removeStudent(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student target = em.find(Student.class, id);
            if (target == null) {
                return 0;
            }
            em.remove(target);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            
        }
        finally {
            em.close();
        }
        return 1;
    }
    
    public static int updateStudent(Student std) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student target = em.find(Student.class, std.getId());
            if (target == null) {
                return 0;
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
        return 1;
        
    }
    
}

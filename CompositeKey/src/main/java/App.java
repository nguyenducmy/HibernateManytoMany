import entities.Employee;
import entities.EmployeeProject;
import entities.EmployeeProjectId;
import entities.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
// Project relate to MANY-TO-MANY Relationship hibernate use composite key as EmployeeProjectId
public class App {
    final static Logger logger = Logger.getLogger(App.class);
    public static void main(String[] args) {
        testCompositeKey();
    }
    public static void testApp(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        logger.info("Created table finished !!!!!!....");
       /* Employees employees = new Employees();
        employees.setCreatedAt(Date.valueOf("2021-10-10"));
        employees.setFirstName("A");
        employees.setLastName("NGUYEN VAN");
        employees.setUpdatedAt(Date.valueOf("2021-10-12"));
        session.save(employees);*/
        transaction.commit();
    }
    public static void testCompositeKey(){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session ss = sf.getCurrentSession();
        Transaction ts = ss.beginTransaction();

        Employee e1 = new Employee();
        e1.setCreatedAt(java.sql.Date.valueOf("2021-02-11"));
        e1.setFirstName("XYZ");
        e1.setLastName("NGUYEN VAN");
        e1.setUpdatedAt(java.sql.Date.valueOf("2021-03-02"));
        ss.persist(e1);

        Employee e2 = new Employee();
        e2.setCreatedAt(java.sql.Date.valueOf("2021-04-11"));
        e2.setFirstName("ABC");
        e2.setLastName("NGUYEN VAN");
        e2.setUpdatedAt(java.sql.Date.valueOf("2021-06-02"));
        ss.persist(e2);

        Project p1 = new Project();
        p1.setCreatedAt(java.sql.Date.valueOf("2021-07-11"));
        p1.setUpdatedAt(java.sql.Date.valueOf("2021-08-11"));
        p1.setTitle("project JAVA");
        ss.persist(p1);

        Project p2 = new Project();
        p2.setCreatedAt(java.sql.Date.valueOf("2021-06-11"));
        p2.setUpdatedAt(java.sql.Date.valueOf("2021-07-11"));
        p2.setTitle("project1 PYTHON");
        ss.persist(p2);

        Set<EmployeeProject> employeeProjectSet1 = new HashSet<EmployeeProject>();
        Set<EmployeeProject> employeeProjectSet2 = new HashSet<EmployeeProject>();

        EmployeeProject employeeProject1 = new EmployeeProject();
        EmployeeProjectId employeeProjectId1 = new EmployeeProjectId(e1, p1);
        employeeProject1.setEmployeeProjectId(employeeProjectId1);

        EmployeeProject employeeProject2 = new EmployeeProject();
        EmployeeProjectId employeeProjectId2 = new EmployeeProjectId(e1, p2);
        employeeProject2.setEmployeeProjectId(employeeProjectId2);

        EmployeeProject employeeProject3 = new EmployeeProject();
        EmployeeProjectId employeeProjectId3 = new EmployeeProjectId(e2, p1);
        employeeProject3.setEmployeeProjectId(employeeProjectId3);

        employeeProjectSet1.add(employeeProject1);
        employeeProjectSet1.add(employeeProject2);
        employeeProjectSet2.add(employeeProject3);

        e1.setEmployeeProjects(employeeProjectSet1);
        e2.setEmployeeProjects(employeeProjectSet2);

        ss.persist(e1);
        ss.persist(e2);
        ts.commit();
        ss.close();
        String a= "next App";
        logger.info("FINISHED !!!!!...." + a);


    }
}

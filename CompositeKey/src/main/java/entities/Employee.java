package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEES")
@Getter @Setter
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPLOYEE_ID", columnDefinition = "int", nullable = false)
    private int id;

    @Column(name = "CREATED_AT", columnDefinition = "datetime", nullable = false)
    private Date createdAt;

    @Column(name = "UPDATED_AT", columnDefinition = "datetime", nullable = false)
    private Date updatedAt;

    @Column(name = "FIRST_NAME", columnDefinition = "nvarchar(50)", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", columnDefinition = "nvarchar(50)", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "employees", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<EmployeeProject> employeeProjects;

}

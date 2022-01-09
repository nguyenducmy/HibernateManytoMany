package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "PROJECTS")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID", columnDefinition = "int", nullable = false)
    private int id;

    @Column(name = "CREATED_AT", columnDefinition = "datetime", nullable = false)
    private Date createdAt;

    @Column(name = "UPDATED_AT", columnDefinition = "datetime", nullable = false)
    private Date updatedAt;

    @Column(name = "TITLE", columnDefinition = "nvarchar(50)", nullable = false)
    private String title;

    @OneToMany(mappedBy = "projects", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<EmployeeProject> employeeProjects;
}

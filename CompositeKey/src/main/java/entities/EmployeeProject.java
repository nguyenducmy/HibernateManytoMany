package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE_PROJECT")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProject implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    EmployeeProjectId employeeProjectId;

    @ManyToOne(optional = true)
    private Employee employee;

    @ManyToOne(optional = true)
    private Project project;

    @ManyToOne(optional = false)
    private Employee employees;

    @ManyToOne(optional = false)
    private Project projects;

}

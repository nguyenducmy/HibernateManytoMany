package entities;
//CompositeKey class

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeeProjectId implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "EPLOYEE_ID")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

}

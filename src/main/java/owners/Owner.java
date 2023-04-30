package owners;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "owners")
public class Owner {
    @Id
    @GeneratedValue
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // annotation can be on same line or multiple lines, most important is
    // it comes before the item / field/ class it applies to
    @Column(name = "ownerName") private String name;
    private Integer age;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}


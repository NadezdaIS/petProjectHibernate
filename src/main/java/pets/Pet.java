package pets;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import owners.Owner;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "pet")
public class Pet {
    @Id
    @GeneratedValue
    private Long id;
    private String petName;
    private Integer age;
    private Double weight;
    @OneToOne
    private Owner owner;
    @OneToOne
    private PetType petType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "meal_type")
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    /* Example one to Many; i.e for one owner with multiple pets
     * such code can but used to get tht list of pet connected to owner
     * this sample is useful in owner class
     * */
    /* @OneToMany
    @JoinColumn(referencedColumnName = "owner_id")
    private ArrayList<Owner> listOfOwners;*/
}



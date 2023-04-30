import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import owners.Owner;
import pets.Gender;
import pets.MealType;
import pets.Pet;
import pets.PetType;

import java.util.List;


public class Main {
    SessionFactory sessionFactory;
    public static void main(String[] args) {

        Main main = new Main();

        main.sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Owner.class)
                .addAnnotatedClass(PetType.class)
                .addAnnotatedClass(Pet.class)
                // connect your entities e.g .addAnnotatedClass(Owner.class)
                .buildSessionFactory();

        Session session = main.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        /*
         * every code in between beginTransaction and transaction.commit
         * happens inside an actual sql transaction and can be rolled back
         * */

        Owner owner = new Owner();
        owner.setName("Zino");
        owner.setAge(145);

        PetType petType = new PetType();
        petType.setType("Fish");

        // saving or creating row to table in database;
        session.persist(owner);
        session.persist(petType);

        Pet pet = new Pet();
        pet.setPetName("Rudy");
        pet.setAge(7);
        pet.setGender(Gender.FEMALE);
        pet.setWeight(5.63D);
        pet.setMealType(MealType.FISH);
        pet.setOwner(owner);
        pet.setPetType(petType);

        session.persist(pet);

        System.out.println(owner);
        System.out.println(petType);
        System.out.println(pet);


        /*Finding data by Id*/
        Long ownerId = owner.getId();
        Owner foundOwner = session.find(Owner.class, ownerId);

        if (foundOwner != null) {
            System.out.println("Owner Found! " + foundOwner);
        } else {
            System.out.println("Cannot find owner with id "  + ownerId);
        }

        /*Updating value in database */
        Long updateOwnerId = owner.getId();
        Owner ownerToUpdate = session.find(Owner.class, updateOwnerId);

        ownerToUpdate.setName("Rita");
        session.merge(ownerToUpdate);

        Owner anotherOwner = new Owner(null, "Some Owner", 56, null, null);
        session.persist(anotherOwner);

        PetType anotherPetType = new PetType(null, "Another Pet Type", null, null);
        session.persist(anotherPetType);

        Pet foundPet = session.find(Pet.class, pet.getId());
        foundPet.setPetType(anotherPetType);
        foundPet.setOwner(anotherOwner);
        foundPet.setAge(223);
        foundPet.setPetName(owner.getName() + "'s Pet");

        Pet updatedPet = (Pet) session.merge(foundPet);
        System.out.println("Updated pet >>" + updatedPet);

        /* deleting item from database */
        PetType petTypeToDelete = new PetType(null, "I will be deleted", null, null);
        session.persist(petTypeToDelete);

        session.remove(petTypeToDelete);
        session.remove(foundPet);

        Pet findPetAfterDelete = session.find(Pet.class, pet.getId());
        System.out.println("Pet found after deleted: " + findPetAfterDelete);

        /*Find by some other conditions and complicated queries*/
        List<Pet> petsOlderThan3 = session.createQuery("FROM pet WHERE age >= 3", Pet.class).getResultList();
        List<Pet> allPets = session.createQuery("FROM pet", Pet.class).getResultList();
        List<Pet> allPetsThatAreFemale = session.createQuery("FROM pet WHERE gender = 'FEMALE'", Pet.class).getResultList();

        System.out.println("petsOlderThan3" + petsOlderThan3);
        System.out.println("allPetsThatAreFemale" + allPetsThatAreFemale);
        System.out.println("allPets" + allPets);
        // transaction.rollback(); you can choose to roll back all changes oif you want
        transaction.commit();
        session.close();
    }
}

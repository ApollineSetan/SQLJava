import com.apolline.sql.db.Bdd;
import com.apolline.sql.model.User;
import com.apolline.sql.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        Bdd.getConnection();
        User newUser = new User(
                "Apolline",
                "Setan",
                "test@gmail.com",
                "Hhhfnu284D"
        );

        // UserRepository.save(newUser); pour enregistrer qlq en bdd
        boolean exist = UserRepository.isExist("test@gmail.com");
        if (exist) {
            System.out.println("Le compte existe");
        } else {
            System.out.println("Le compte n'existe pas");
        }

        User foundUser = UserRepository.findByEmail("test@gmail.com");
        if (foundUser != null) {
            System.out.println(foundUser);
        } else {
            System.out.println("User not found");
        }

    }
}

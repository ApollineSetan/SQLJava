import com.apolline.sql.db.Bdd;
import com.apolline.sql.model.User;
import com.apolline.sql.repository.UserRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bdd.getConnection();
//        User newUser = new User(
//                "Apolline",
//                "Setan",
//                "test@gmail.com",
//                "Hhhacfnu284D",
//                "ADMIN"
//        );
//
//        UserRepository.save(newUser); pour enregistrer qlq en bdd

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

        List<User> allUsers = UserRepository.findAll();
        for (User user : allUsers){
            System.out.println(user);
        }

//        User userToUpdate = new User(
//                "Sophie",
//                "Hervieu",
//                "test2@gmail.com",
//                "Hhhacfnu284D",
//                "USER"
//        );
//
//        UserRepository.save(newUser); pour enregistrer qlq en bdd
//
//        User updatedUser = UserRepository.update(userToUpdate, "test2@gmail.com");
//        if (updatedUser != null){
//            System.out.println("User updated successfully " + updatedUser);
//        } else {
//            System.out.println("User with email " + userToUpdate + " not found");
//        }
    }
}

package com.apolline.sql.repository;

import com.apolline.sql.db.Bdd;
import com.apolline.sql.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository {

    private static Connection connection = Bdd.getConnection();

    public static User save(User addUser){
            User newUser = null;
        try {
            // Requête
            String sql = "INSERT INTO users(firstname, lastname, email, password) VALUE(?,?,?,?)";

            // Préparer la requête
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Bind les paramètres
            preparedStatement.setString(1, addUser.getFirstname());
            preparedStatement.setString(2, addUser.getLastname());
            preparedStatement.setString(3, addUser.getEmail());
            preparedStatement.setString(4, addUser.getPassword());

            // Exécuter la requête
            int nbrRows = preparedStatement.executeUpdate();

            // Vérifier si la requête est passée
            if(nbrRows > 0){
                newUser = new User(
                        addUser.getFirstname(),
                        addUser.getLastname(),
                        addUser.getEmail(),
                        addUser.getPassword()
                );
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return newUser;
    }

    public static User findByEmail(String email) {
        User getUser = null;
        try {
            String sql = "SELECT id, firstname, lastname, email FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                getUser = new User();
                getUser.setId(resultSet.getInt("id"));
                getUser.setFirstname(resultSet.getString("firstname"));
                getUser.setLastname(resultSet.getString("lastname"));
                getUser.setEmail(resultSet.getString("email"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return getUser;
    }

    public static boolean isExist(String email){
        boolean getUser = false;
        try {
            String sql = "SELECT id FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                getUser = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getUser;
    }

}

package com.apolline.sql.repository;

import com.apolline.sql.db.Bdd;
import com.apolline.sql.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public static List<User> findAll () {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT id, firstname, lastname, email FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User update(User updateUser, String email){
        User modifiedUser = null;
        try {
            if (isExist(email)) {
                String sql = "UPDATE users SET firstname=?, lastname=?, email=?, password=? WHERE email=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, updateUser.getFirstname());
                preparedStatement.setString(2, updateUser.getLastname());
                preparedStatement.setString(3, updateUser.getEmail());
                preparedStatement.setString(4, updateUser.getPassword());
                preparedStatement.setString(5, email);

                int nbrRows = preparedStatement.executeUpdate();
                if(nbrRows > 0){
                    modifiedUser = updateUser;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modifiedUser;
    }

}
package com.apolline.sql.repository;

import com.apolline.sql.db.Bdd;
import com.apolline.sql.model.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RolesRepository {
    private static Connection connection = Bdd.getConnection();

    public static Roles save(Roles addRoles){
        Roles newRole = null;
        try {
            String sql = "INSERT INTO roles(roles_name) VALUE(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addRoles.getRolesName());
            int nbrRows = preparedStatement.executeUpdate();
            if(nbrRows > 0){
                newRole = new Roles(
                        addRoles.getRolesName()
                );
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return newRole;
    }

    public static boolean isExist(String rolesName){
        boolean getRole = false;
        try {
            String sql = "SELECT id FROM roles WHERE roles_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, rolesName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                getRole = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRole;
    }

    public static Roles findByName(String rolesName) {
        Roles findRole = null;
        try {
            String sql = "SELECT id, roles_name FROM roles WHERE roles_name = ?";
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setString(1, rolesName);
            ResultSet resultSet = prepare.executeQuery();
            if(resultSet.next()){
                findRole = new Roles();
                findRole.setId(resultSet.getInt("id"));
                findRole.setRolesName(resultSet.getString("roles_name"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return findRole;
    }

    public static List<Roles> findAll () {
        List<Roles> roles = new ArrayList<>();
        try {
            String sql = "SELECT id, roles_name FROM roles";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Roles role = new Roles();
                role.setId(resultSet.getInt("id"));
                role.setRolesName(resultSet.getString("roles_name"));
                roles.add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
}

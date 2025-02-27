package com.apolline.sql.repository;

import com.apolline.sql.db.Bdd;
import com.apolline.sql.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private static Connection connection = Bdd.getConnection();

    public static Category save(Category addCategory){
        Category newCategory = null;
        try {
            // Requête
            String sql = "INSERT INTO category(category_name) VALUE(?)";

            // Préparer la requête
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Bind les paramètres
            preparedStatement.setString(1, addCategory.getCategoryName());

            // Exécuter la requête
            int nbrRows = preparedStatement.executeUpdate();

            // Vérifier si la requête est passée
            if(nbrRows > 0){
                newCategory = new Category(
                        addCategory.getCategoryName()
                );
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return newCategory;
    }

    public static boolean isExist(String categoryName){
        boolean getCategory = false;
        try {
            String sql = "SELECT id FROM category WHERE category_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                getCategory = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getCategory;
    }

    public static Category findByName(String categoryName) {
        Category findCategory = null;
        try {
            String sql = "SELECT id, category_name FROM category WHERE category_name = ?";
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setString(1, categoryName);
            ResultSet resultSet = prepare.executeQuery();
            if(resultSet.next()){
                findCategory = new Category();
                findCategory.setId(resultSet.getInt("id"));
                findCategory.setCategoryName(resultSet.getString("category_name"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return findCategory;
    }

    public static List<Category> findAll () {
        List<Category> categories = new ArrayList<>();
        try {
            String sql = "SELECT id, category_name FROM category";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("category_name"));
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

}

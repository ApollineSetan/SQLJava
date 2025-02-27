package com.apolline.sql.repository;

import com.apolline.sql.db.Bdd;
import com.apolline.sql.model.Category;
import com.apolline.sql.model.Roles;
import com.apolline.sql.model.Task;
import com.apolline.sql.model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private static Connection connection = Bdd.getConnection();

    public static Task save(Task addTask){
        Task newTask = null;
        try {
            String sql = "INSERT INTO task(title, content, create_at, end_date, status, users_id) " +
                    "VALUES(?, ?, NOW(), ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newTask.getTitle());
            preparedStatement.setString(2, newTask.getContent());
            preparedStatement.setDate(3, new java.sql.Date(newTask.getEndDate().getTime()));
            preparedStatement.setBoolean(4, newTask.isStatus());
            preparedStatement.setInt(5, newTask.getUser().getId());
            int nbrRows = preparedStatement.executeUpdate();
            if(nbrRows > 0){
                newTask = addTask;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return newTask;
    }

    public static boolean isExist(String title, Date CreateAt){
        boolean getTask = false;
        try {
            String sql = "SELECT id FROM task WHERE title = ? AND create_at = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setDate(2, CreateAt);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                getTask = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getTask;
    }

    public static Task findById(int taskId) {
        Task getTask = null;
        try {
            String sql = "SELECT t.id AS tId, t.title, t.content, t.create_at, t.end_date, t.`status`, " +
                    "u.id AS uId, u.firstname, u.lastname, r.id AS rId, r.roles_name AS rName, " +
                    "GROUP_CONCAT(c.id) AS catId, GROUP_CONCAT(c.category_name) AS catName " +
                    "FROM task_category AS tc " +
                    "INNER JOIN task AS t ON tc.task_id = t.id " +
                    "INNER JOIN category AS c ON tc.category_id = c.id " +
                    "INNER JOIN users AS u ON t.users_id = u.id " +
                    "INNER JOIN roles AS r ON u.roles_id = r.id " +
                    "WHERE t.id = ? " +
                    "GROUP BY t.id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                getTask = new Task();
                getTask.setId(resultSet.getInt("tId"));
                getTask.setTitle(resultSet.getString("title"));
                getTask.setContent(resultSet.getString("content"));
                getTask.setCreateAt(resultSet.getDate("create_at"));
                getTask.setEndDate(resultSet.getDate("end_date"));
                getTask.setStatus(resultSet.getBoolean("status"));

                User user = new User();
                user.setId(resultSet.getInt("uId"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));

                Roles role = new Roles();
                role.setId(resultSet.getInt("rId"));
                role.setRolesName(resultSet.getString("rName"));
                user.setRoles(role);
                getTask.setUser(user);

                String [] catNames = resultSet.getString("catName").split(",");
                String [] catIds = resultSet.getString("catId").split(",");

                for (int i = 0; i < catIds.length; i++){
                    Category category = new Category();
                    category.setCategoryName(catNames[i]);
                    category.setId(Integer.parseInt(catIds[i]));
                    getTask.addCategory(category);
                }
            }

    } catch (Exception e) {
        e.printStackTrace();
    }
        return getTask;
}

public static List<Task> findAll() {
    List<Task> tasks = new ArrayList<>();
    try {
        String sql = "SELECT t.id AS tId, t.title, t.content, t.create_at, t.end_date, t.`status`, " +
                "u.id AS uId, u.firstname, u.lastname, r.id AS rId, r.roles_name AS rName, " +
                "GROUP_CONCAT(c.id) AS catId, GROUP_CONCAT(c.category_name) AS catName " +
                "FROM task_category AS tc " +
                "INNER JOIN task AS t ON tc.task_id = t.id " +
                "INNER JOIN category AS c ON tc.category_id = c.id " +
                "INNER JOIN users AS u ON t.users_id = u.id " +
                "INNER JOIN roles AS r ON u.roles_id = r.id " +
                "GROUP BY t.id";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getInt("tId"));
            task.setTitle(resultSet.getString("title"));
            task.setContent(resultSet.getString("content"));
            task.setCreateAt(resultSet.getDate("create_at"));
            task.setEndDate(resultSet.getDate("end_date"));
            task.setStatus(resultSet.getBoolean("status"));

            User user = new User();
            user.setId(resultSet.getInt("uId"));
            user.setFirstname(resultSet.getString("firstname"));
            user.setLastname(resultSet.getString("lastname"));

            Roles role = new Roles();
            role.setId(resultSet.getInt("rId"));
            role.setRolesName(resultSet.getString("rName"));

            user.setRoles(role);
            task.setUser(user);

            String [] catNames = resultSet.getString("catName").split(",");
            String [] catIds = resultSet.getString("catId").split(",");

            for (int i = 0; i < catIds.length; i++){
                Category category = new Category();
                category.setCategoryName(catNames[i]);
                category.setId(Integer.parseInt(catIds[i]));
                task.addCategory(category);
            }

            tasks.add(task);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return tasks;
}

}

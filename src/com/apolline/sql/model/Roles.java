package com.apolline.sql.model;

public class Roles {
    // Attributs
    private int id;
    private String rolesName;

    // Constructeurs
    public Roles(){}

    public Roles(String rolesName){
        this.rolesName = rolesName;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolesName() {
        return rolesName;
    }

    public void setRolesName(String rolesName) {
        this.rolesName = rolesName;
    }

    // Méthode permettant de retourner une chaîne et non pas un pointeur de la mémoire dans la console

    @Override
    public String toString() {
        return "Roles{" +
                "rolesName='" + rolesName + '\'' +
                '}';
    }
}

package ca.calvinli.recipebook;

import java.io.Serializable;
import java.util.ArrayList;

public class Users implements Serializable {
    private String fullName;
    private String email;
    private ArrayList<Recipe> recipeArrayList;

    public Users (String fullName, String email)
    {
        this.fullName = fullName;
        this.email = email;
        this.recipeArrayList = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Recipe> getRecipeArrayList() {
        return recipeArrayList;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRecipeArrayList(ArrayList<Recipe> recipeArrayList) {
        this.recipeArrayList = recipeArrayList;
    }
}

package ca.calvinli.recipebook;

import android.media.Image;

import java.io.Serializable;

public class Recipe implements Serializable {


    private String name;
    private String description;
    private String ingredients;
    private Image image;
    private String instructions;

    public Recipe(String name, String description, String ingredients, String instructions)
    {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public Image getImage() {
        return image;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}

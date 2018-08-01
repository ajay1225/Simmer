package com.example.lenovo.simmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetails implements Serializable {
    String id="";
    String name="";
    String servings="";
    String image="";
    List<IngredientsDetails> ingredientsList=new ArrayList<>();
    List<StepDetails> stepsList=new ArrayList<>();

    public List<IngredientsDetails> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<IngredientsDetails> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<StepDetails> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<StepDetails> stepsList) {
        this.stepsList = stepsList;
    }



    public RecipeDetails(String id, String name, String servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }
    public RecipeDetails(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static class IngredientsDetails implements Serializable{
        String quantity="";
        String measure="";
        String ingredient="";

        public IngredientsDetails(String quantity, String measure, String ingredient) {
            this.quantity = quantity;
            this.measure = measure;
            this.ingredient = ingredient;
        }
        public IngredientsDetails(){}

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }
    }
    public static class StepDetails implements Serializable {
        String stepId="";
        String shortDescription="";
        String description="";
        String videoURL="";
        String thumbnailURL="";

        public StepDetails(String stepId, String shortDescription, String description, String videoURL, String thumbnailURL) {
            this.stepId = stepId;
            this.shortDescription = shortDescription;
            this.description = description;
            this.videoURL = videoURL;
            this.thumbnailURL = thumbnailURL;
        }

        public StepDetails(){}

        public String getStepId() {
            return stepId;
        }

        public void setStepId(String stepId) {
            this.stepId = stepId;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public void setVideoURL(String videoURL) {
            this.videoURL = videoURL;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        public void setThumbnailURL(String thumbnailURL) {
            this.thumbnailURL = thumbnailURL;
        }
    }
}

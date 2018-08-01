package com.example.lenovo.simmer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeAsync extends AsyncTask<Void, Void, Void> {
    public String data = "";
    public ProgressDialog progressDialog;
    //public List<RecipeDetails.IngredientsDetails> ingredientsList=new ArrayList<>();
    private List<RecipeDetails> recipesList = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    Context context;

    RecipeAsync(Context context) {
        this.context = context;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            MainActivity.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else{

            MainActivity.recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        }
        MainActivity.recyclerView.setAdapter(new RecipeAdapter(context, recipesList));
        progressDialog.dismiss();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading.. Please wait");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String JSONLink = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
            URL url = new URL(JSONLink);
            Log.i("doInBackground: ", url.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                data = data + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("JSON data", data);
        try {

            JSONArray mainArray = new JSONArray(data);
            for (int i = 0; i < mainArray.length(); i++) {
                RecipeDetails recipeDetails = new RecipeDetails();
                JSONObject jsonObject = mainArray.getJSONObject(i);
                recipeDetails.setId(jsonObject.optString("id"));
                recipeDetails.setName(jsonObject.optString("name"));
                recipeDetails.setServings(jsonObject.optString("servings"));
                Log.i("JSONARRAY: ", recipeDetails.getId() + "    " + recipeDetails.getName() + "    " + recipeDetails.getServings());
                JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
                List<RecipeDetails.IngredientsDetails> ingredientsList = new ArrayList<>();
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    RecipeDetails.IngredientsDetails ingredientsDetails = new RecipeDetails.IngredientsDetails();
                    JSONObject ingredientObject = ingredientsArray.getJSONObject(j);
                    ingredientsDetails.setIngredient(ingredientObject.optString("ingredient"));
                    ingredientsDetails.setMeasure(ingredientObject.optString("measure"));
                    ingredientsDetails.setQuantity(ingredientObject.optString("quantity"));
                    ingredientsList.add(ingredientsDetails);
              }
                JSONArray stepsArray = jsonObject.getJSONArray("steps");
                List<RecipeDetails.StepDetails> stepsList = new ArrayList<>();
                for (int j = 0; j < stepsArray.length(); j++) {
                    JSONObject stepObject = stepsArray.getJSONObject(j);
                    RecipeDetails.StepDetails stepDetails = new RecipeDetails.StepDetails();
                    stepDetails.setDescription(stepObject.optString("description"));
                    stepDetails.setShortDescription(stepObject.optString("shortDescription"));
                    stepDetails.setStepId(stepObject.optString("id"));
                    stepDetails.setVideoURL(stepObject.optString("videoURL"));
                    stepDetails.setThumbnailURL(stepObject.optString("thumbnailURL"));
                    stepsList.add(stepDetails);
                }
                Log.i("ingredientsList", ingredientsList.toString());
                Log.i("StepsList", stepsList.toString());
                recipeDetails.setIngredientsList(ingredientsList);
                recipeDetails.setStepsList(stepsList);
                recipesList.add(recipeDetails);

            }
            Log.i("receipeList", recipesList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Activity getActivity() {
        Context context2 = context;
        while (context2 instanceof ContextWrapper) {
            if (context2 instanceof Activity) {
                return (Activity) context2;
            }
            context2 = ((ContextWrapper) context2).getBaseContext();
        }
        return null;
    }


}

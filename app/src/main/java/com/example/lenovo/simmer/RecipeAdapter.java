package com.example.lenovo.simmer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyHolder> {
    Context context;
    List<RecipeDetails> list=new ArrayList<>();

    public RecipeAdapter(Context context,List<RecipeDetails> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        int id=R.layout.recipe_list;
        LayoutInflater inflater=LayoutInflater.from(context);
        final View view=inflater.inflate(id,parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.title.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title=itemView.findViewById(R.id.id_tv_recipeTitle);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            RecipeDetails resultDetails=list.get(position);
            List<RecipeDetails.StepDetails> stepList=resultDetails.getStepsList();
            List<RecipeDetails.IngredientsDetails> ingredientsList=resultDetails.getIngredientsList();
            Intent intent=new Intent(context,ItemListActivity.class);
            intent.putExtra("resultData", resultDetails);
            context.startActivity(intent);

        }

    }
}

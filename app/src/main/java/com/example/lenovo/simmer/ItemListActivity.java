package com.example.lenovo.simmer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lenovo.simmer.dummy.DummyContent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ItemListActivity extends AppCompatActivity {
    TextView textViewingre;

    private boolean mTwoPane;
    List<RecipeDetails.StepDetails> stepList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        textViewingre=findViewById(R.id.id_tv_ingre);


       RecipeDetails recipeDetails= (RecipeDetails) getIntent().getSerializableExtra("resultData");
        setTitle(recipeDetails.getName());
       stepList=recipeDetails.getStepsList();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(recipeDetails.getName());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        List<RecipeDetails.IngredientsDetails> resultIngre=recipeDetails.getIngredientsList();
        String ingre="";
        for(int i=0;i<resultIngre.size();i++){
            ingre+=(i+1)+". "+resultIngre.get(i).getQuantity()+" "+resultIngre.get(i).getMeasure()+" of "+resultIngre.get(i).getIngredient()+"\n";
        }
        SimmerWidgetService.seeviceCall(this,ingre);

        textViewingre.setText(ingre);
        Log.i("result",resultIngre.get(1).getIngredient());
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        recyclerView.setFocusable(false);// Not to set recycle position  directly when activity opened
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, stepList, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<RecipeDetails.StepDetails> mValues;
        private final boolean mTwoPane;

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<RecipeDetails.StepDetails> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mIdView.setText(mValues.get(position).getShortDescription());
           holder.mIdView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   RecipeDetails.StepDetails resultStep=  mValues.get(position);

                   if (mTwoPane) {
                       Bundle arguments = new Bundle();
                       arguments.putSerializable("resultStep",  resultStep);
                       ItemDetailFragment fragment = new ItemDetailFragment();
                       fragment.setArguments(arguments);
                       mParentActivity.getSupportFragmentManager().beginTransaction()
                               .replace(R.id.item_detail_container, fragment)
                               .commit();
                   } else {
                       Intent intent=new Intent(v.getContext(),ItemDetailActivity.class);
                       intent.putExtra("resultStep",  resultStep);
                       v.getContext().startActivity(intent);
                   }
               }
           });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder  {
            final Button mIdView;

            ViewHolder(View view) {
                super(view);
                mIdView =  view.findViewById(R.id.id_text);
            }

        }
    }
}

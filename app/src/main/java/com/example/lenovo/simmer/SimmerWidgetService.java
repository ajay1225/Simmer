package com.example.lenovo.simmer;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class SimmerWidgetService extends IntentService {

    public SimmerWidgetService() {
        super("asdf");
    }
    public static void seeviceCall(Context context,String ingre){
        Intent intent=new Intent(context,SimmerWidgetService.class);
        intent.putExtra("Ingredients",ingre);
        intent.setAction("action");
        context.startService(intent);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent.getAction().equals("action")){
            String ingredients=intent.getStringExtra("Ingredients");
            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);
            int[] appWidgetids=appWidgetManager.getAppWidgetIds(new ComponentName(this,SimmerWidget.class));
            SimmerWidget.onUpdateIngredients(this,appWidgetManager,appWidgetids,ingredients);


        }

    }
}

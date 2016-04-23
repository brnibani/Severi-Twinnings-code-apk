package com.app.gabriele.severitwinnings;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gabriele.severitwinnings.data.Articolo;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import severitwinnings.gabriele.app.com.severitwinnings.R;


/**
 * Created by Gabriele on 07/05/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<Articolo> {
    private LayoutInflater inflater;
    private ArrayList<Articolo> datas;


    public MyArrayAdapter(Context context, int textViewResourceId,
                          ArrayList<Articolo> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        inflater = ((Activity) context).getLayoutInflater();
        datas = objects;
    }

    static class ViewHolder {
        TextView postTitleView;
        ImageView postThumbView;
        TextView dateView;
        TextView categoryView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.postThumbView = (ImageView) convertView.findViewById(R.id.list_image);
            viewHolder.postTitleView = (TextView) convertView.findViewById(R.id.text2);
            viewHolder.categoryView = (TextView)convertView.findViewById(R.id.textcategory);
            viewHolder.dateView = (TextView) convertView.findViewById(R.id.textdate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if(datas.get(position).getCategory().equals("Generale")){
            viewHolder.postThumbView.setImageResource(R.drawable.stemma);
            viewHolder.categoryView.setText("Generale");
        }
        else if (datas.get(position).getCategory().equals("Studenti americani in Italia")){
            viewHolder.postThumbView.setImageResource(R.drawable.italy);
            viewHolder.categoryView.setText("Studenti americani in Italia");
        }else if (datas.get(position).getCategory().equals("Focus Palestina")){
            viewHolder.postThumbView.setImageResource(R.drawable.cuore);
            viewHolder.categoryView.setText("Focus Palestina");
        }else if (datas.get(position).getCategory().equals("Studenti italiani in America")){
            viewHolder.postThumbView.setImageResource(R.drawable.usa);
            viewHolder.categoryView.setText("Studenti italiani in America");
        }else if (datas.get(position).getCategory().equals("Focus USA")){
            viewHolder.postThumbView.setImageResource(R.drawable.palla);
            viewHolder.categoryView.setText("Focus USA");
        }else if (datas.get(position).getCategory().equals("La parola agli studenti")){
            viewHolder.postThumbView.setImageResource(R.drawable.stemma);
            viewHolder.categoryView.setText("La parola agli studenti");
        }else{
            viewHolder.postThumbView.setImageResource(R.drawable.stemma);
            viewHolder.categoryView.setText("Generale");
        }

        viewHolder.postTitleView.setText(datas.get(position).getTitle());

        //Adesso convertiamo il formato della data del feed RSS in dd/MM/yyyy
        String old_format = "EEE, dd MMM yyyy HH:mm:ss Z";
        //String old_date = "Mon, 16 Nov 2015 20:23:11 +0000";
        String old_date = datas.get(position).getDate();

        DateFormat originalFormat = new SimpleDateFormat(old_format, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = null;
        try{
            Date date = originalFormat.parse(old_date);
            formattedDate = targetFormat.format(date);
        }catch(Exception e){
            Log.e("SeveriTwinnings", e.getMessage());
        }
        viewHolder.dateView.setText(formattedDate);
        return convertView;
    }

/*
    private boolean isTablet(){
        WindowManager windowManager = (WindowManager)this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        int width = dm.widthPixels / dm.densityDpi;
        int height = dm.heightPixels / dm.densityDpi;
        double ScreenDiagonal = Math.sqrt((width*width)+(height*height));
        return (ScreenDiagonal >= 6.8);
    }
*/
}

package com.app.gabriele.severitwinnings;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gabriele.severitwinnings.data.RssAtomItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import severitwinnings.gabriele.app.com.severitwinnings.R;


/**
 * Created by Gabriele on 07/05/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<RssAtomItem> {
    private LayoutInflater inflater;
    private ArrayList<RssAtomItem> datas;

    public MyArrayAdapter(Context context, int textViewResourceId,
                          ArrayList<RssAtomItem> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        inflater = ((Activity) context).getLayoutInflater();
        datas = objects;
    }

    static class ViewHolder {
        TextView postTitleView;
        ImageView postThumbView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            if(isTablet()) {
                convertView = inflater.inflate(R.layout.list_item_tab, null);
            }
            else {
                convertView = inflater.inflate(R.layout.list_item2, null);
            }
            viewHolder = new ViewHolder();
            viewHolder.postThumbView = (ImageView) convertView.findViewById(R.id.list_image);
            viewHolder.postTitleView = (TextView) convertView.findViewById(R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if(datas.get(position).getCategory().equals("Generale")){
            viewHolder.postThumbView.setImageResource(R.drawable.stemma);
        }
        else if (datas.get(position).getCategory().equals("Studenti americani in Italia")){
            viewHolder.postThumbView.setImageResource(R.drawable.italy);
        }else if (datas.get(position).getCategory().equals("Focus Palestina")){
            viewHolder.postThumbView.setImageResource(R.drawable.cuore);
        }else if (datas.get(position).getCategory().equals("Studenti italiani in America")){
            viewHolder.postThumbView.setImageResource(R.drawable.usa);
        }else if (datas.get(position).getCategory().equals("Focus USA")){
            viewHolder.postThumbView.setImageResource(R.drawable.palla);
        }else if (datas.get(position).getCategory().equals("La parola agli studenti")){
            viewHolder.postThumbView.setImageResource(R.drawable.stemma);
        }else{
            viewHolder.postThumbView.setImageResource(R.drawable.stemma);
        }

        viewHolder.postTitleView.setText(datas.get(position).getTitle());

        return convertView;
    }


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

}

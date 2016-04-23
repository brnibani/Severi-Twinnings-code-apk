package com.app.gabriele.severitwinnings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.app.gabriele.severitwinnings.data.Articolo;
import com.app.gabriele.severitwinnings.listeners.ListListener;
import com.app.gabriele.severitwinnings.util.FeedReader;

import java.util.ArrayList;
import java.util.List;

import severitwinnings.gabriele.app.com.severitwinnings.R;

/**
 * Created by Gabriele on 03/09/2015.
 */
public class GetRSSDataTask extends AsyncTask<String, Void, List<Articolo> > {

    ProgressDialog pd;
    private ListView itcItems;
    Activity a;
    View v;

    public GetRSSDataTask(Activity context, View rootview) {
        this.a = context;
        this.v = rootview;
    }


    @Override
    protected List<Articolo> doInBackground(String... urls) {

        try {
            FeedReader rssReader = new FeedReader(urls[0]);
            return rssReader.getItems();
        } catch (Exception e) {
            Log.e("SeveriTwinnings", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        try {
            pd= new ProgressDialog(a);
            pd.setMessage("Caricamento...");
            pd.show();
        } catch (Exception e) {
            Log.e("Severi Twinnings", e.getMessage());
        }
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(List<Articolo> result) {

        pd.dismiss();
        itcItems = (ListView)v.findViewById(R.id.list);
        ArrayList<Articolo> ARresult = new ArrayList<Articolo>(result);
        MyArrayAdapter adapter = new MyArrayAdapter(a, R.layout.list_item, ARresult);
        itcItems.setAdapter(adapter);//mSchedule is adapter
        itcItems.setOnItemClickListener(new ListListener(result, a));


    }

}

package com.app.gabriele.severitwinnings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.gabriele.severitwinnings.data.RssAtomItem;
import com.app.gabriele.severitwinnings.listeners.ListListener;
import com.app.gabriele.severitwinnings.util.RssAtomReader;

import java.util.ArrayList;
import java.util.List;

import severitwinnings.gabriele.app.com.severitwinnings.R;

/**
 * Created by Gabriele on 03/09/2015.
 */
public class GetRSSDataTask extends AsyncTask<String, Void, List<RssAtomItem> > {
    ProgressDialog pd;
    //ProgressWheel pw;
    private ListView itcItems;
    Activity a;
    View v;
   // LayoutInflater inflater;
   // ViewGroup container;
  //  View rootView;

    public GetRSSDataTask(Activity context, View rootview) {
        this.a = context;
        this.v = rootview;
     //   this.inflater = inflater;
     //   this.container = container;
    }


    @Override
    protected List<RssAtomItem> doInBackground(String... urls) {

        try {
            //              setRefreshActionButtonState(true);

          //   pw.incrementProgress();
            // Create RSS reader
            RssAtomReader rssReader = new RssAtomReader(urls[0]);

            // Parse RSS, get items
            return rssReader.getItems();

        } catch (Exception e) {
            Log.e("SeveriTwinnings", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        // pd.setMessage("Caricamento...");
        // pd.show();
              //  pw = (ProgressWheel)v.findViewById(R.id.pw_spinner);
        try {
            pd= new ProgressDialog(a);
            pd.setMessage("Caricamento...");
            pd.show();
             //          pw.spin();
             //          pw.setText("Caricamento...");
        } catch (Exception e) {
            Log.e("Severi Twinnings", e.getMessage());
               // pw.setText("Non Connesso");
        }
//            pw.incrementProgress();
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(List<RssAtomItem> result) {

     //   pw.stopSpinning();

        //     a.setContentView(R.layout.);

     //   rootView = inflater.inflate(R.layout.fragment_tutte, container, false);

        pd.dismiss();

        // Get a ListView from main view
        itcItems = (ListView)v.findViewById(R.id.list);

        ArrayList<RssAtomItem> ARresult = new ArrayList<RssAtomItem>(result);

        MyArrayAdapter adapter = new MyArrayAdapter(a, R.layout.list_item2, ARresult);

        itcItems.setAdapter(adapter);//mSchedule is adapter

        itcItems.setOnItemClickListener(new ListListener(result, a));







    }

}

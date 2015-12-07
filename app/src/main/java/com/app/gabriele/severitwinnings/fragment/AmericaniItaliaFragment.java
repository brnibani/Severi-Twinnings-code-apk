package com.app.gabriele.severitwinnings.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.app.gabriele.severitwinnings.DetailsActivity;
import com.app.gabriele.severitwinnings.GetRSSDataTask;
import com.app.gabriele.severitwinnings.data.RssAtomItem;
import com.app.gabriele.severitwinnings.listeners.ListListener;
import com.app.gabriele.severitwinnings.util.RssAtomReader;

import java.util.ArrayList;
import java.util.List;

import severitwinnings.gabriele.app.com.severitwinnings.R;

public class AmericaniItaliaFragment extends Fragment {

    private GetRSSDataTask task = null;

    public AmericaniItaliaFragment() {
        // Required empty public constructor
    }

//    public AmericaniItaliaFragment(Activity act) {
 //       a = act;
 //   }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set view
      //  a.setContentView(R.layout.progress);
      //  GetRSSDataTask task = new GetRSSDataTask();
      //  task.execute("https://severitwinnings.wordpress.com/feed/");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tutte, container, false);
        task = new GetRSSDataTask(getActivity(),rootView);
        try{
            do_task();
        }catch(Exception e){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Connessione di rete assente");
            builder.setMessage("La connessione ad Internet risulta assente.");

            builder.setNegativeButton("Riprova", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    dialog.dismiss();
                    do_task();
                }

            });

            builder.setPositiveButton("Esci", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                    getActivity().finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }

        // Inflate the layout for this fragment
        return rootView;
    }



    public void do_task(){
        task.execute("https://severitwinnings.wordpress.com/category/studenti-americani-in-italia/feed");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onDetach() {
        super.onDetach();
    }




}

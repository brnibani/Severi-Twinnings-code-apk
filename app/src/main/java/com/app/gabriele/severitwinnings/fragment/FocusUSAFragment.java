package com.app.gabriele.severitwinnings.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gabriele.severitwinnings.GetRSSDataTask;
import com.app.gabriele.severitwinnings.*;

import severitwinnings.gabriele.app.com.severitwinnings.R;

public class FocusUSAFragment extends Fragment {

    private GetRSSDataTask task = null;
    private View rootView = null;

    public FocusUSAFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tutte, container, false);
        task = new GetRSSDataTask(getActivity(),rootView);
        try{
            do_task();
        }catch (Exception e){
            //In caso di connessione assente, deve apparire un messaggio di errore che consente
            //all'utente di uscire dall'applicazione oppure riprovare ad aprire l'articolo (o l'applicazione)

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
        task.execute("https://severitwinnings.wordpress.com/category/focus-usa/feed");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

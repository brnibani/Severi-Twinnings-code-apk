package com.app.gabriele.severitwinnings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import severitwinnings.gabriele.app.com.severitwinnings.R;

import com.app.gabriele.severitwinnings.fragment.AmericaniItaliaFragment;
import com.app.gabriele.severitwinnings.fragment.FocusPalestinaFragment;
import com.app.gabriele.severitwinnings.fragment.FocusUSAFragment;
import com.app.gabriele.severitwinnings.fragment.GeneraleFragment;
import com.app.gabriele.severitwinnings.fragment.ItalianiAmericaFragment;
import com.app.gabriele.severitwinnings.fragment.TutteFragment;
import com.app.gabriele.severitwinnings.navigationdrawer.FragmentDrawer;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    // A reference to the local object
    public MainActivity local;
    // private Menu optionsMenu;
    private static boolean showspinner;
    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;


    public void spinnerOn(){
        showspinner = true;
        setProgressBarIndeterminateVisibility(true);
    }

    public void spinnerOff(){
        showspinner = false;
        setProgressBarIndeterminateVisibility(false);
    }

    protected void onResume(){
        super.onResume();
        if(showspinner)
            spinnerOn();
        else
            spinnerOff();
    }

    protected void onStart(){
        super.onStart();
    }

    /**
     * This method creates main application view
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        // Set view
        setContentView(R.layout.activity_main);

        try{
            mToolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            drawerFragment = (FragmentDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout), mToolbar);
            drawerFragment.setDrawerListener(this);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e){
            Log.e("TOOLBAR", e.getMessage());
        }

        // Set reference to this activity
        local = this;

        try_to_connect(0);

    }

    /**
     * Metodo che si occupa di controllare se è presente la connessione ad Internet utilizzando
     * il metodo isConnected() definito sempre nella classe MainActivity.
     * Se la connessione ad Internet è presente, allora richiama il metodo displayView(display_position) per
     * poter avviare correttamente l'applicazione.
     * In caso di mancata connessione, viene visualizzato un AlertDialog box che permette all'utente
     * di uscire dall'applicazione o ritentare la connessione.
     * @param display_position è un numero intero che rappresenta la categoria di notizie che vogliamo visualizzare
     * @return true (se è presente la connessione) oppure false (se non è presente la connessione)
     */
    private boolean try_to_connect(int display_position) {
        if(isConnected()){
            displayView(display_position);             // display the first navigation drawer view on app launch
            getSupportActionBar().setTitle("SeveriTwinnings");
            return true;
        }
        final int param = display_position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Connessione di rete assente");
        builder.setMessage("La connessione ad Internet risulta assente.");

        builder.setNegativeButton("Riprova", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                dialog.dismiss();
                try_to_connect(param);
            }

        });

        builder.setPositiveButton("Esci", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
                MainActivity.this.finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return false;
    }

    @Override
    public void onDrawerItemSelected(View view, int pos) {

        try_to_connect(pos);
    }

    private void displayView(int position) {
        android.support.v4.app.Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new TutteFragment();
                title = getString(R.string.title_tutte);
                break;
            case 1:
                fragment = new GeneraleFragment();
                title = getString(R.string.title_generale);
                break;
            case 2:
                fragment = new FocusPalestinaFragment();
                title = getString(R.string.title_focus_palestina);
                break;
            case 3:
                fragment = new FocusUSAFragment();
                title = getString(R.string.title_focus_usa);
                break;
            case 4:
                fragment = new AmericaniItaliaFragment();
                title = getString(R.string.title_americani_italia);
                break;
            case 5:
                fragment = new ItalianiAmericaFragment();
                title = getString(R.string.title_italiani_america);
                break;
            default:
                break;
        }

        getSupportActionBar().setTitle(title);

        if (fragment != null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public boolean isConnected(){
        ConnectivityManager conn = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();

        // Checks the user prefs and the network connection. Based on the result, decides whether
        // to refresh the display or keep the current display.
        // If the userpref is Wi-Fi only, checks to see if the device has a Wi-Fi connection.
        if (networkInfo != null) {
            return true;
        } else {
            return false;
        }

    }






}

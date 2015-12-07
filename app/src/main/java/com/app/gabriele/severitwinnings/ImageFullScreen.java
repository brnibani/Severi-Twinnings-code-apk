package com.app.gabriele.severitwinnings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import severitwinnings.gabriele.app.com.severitwinnings.R;

/**
 * Created by Gabriele on 14/10/2015.
 */
public class ImageFullScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);
        Toast.makeText(ImageFullScreen.this, "Caricamento dell'immagine in corso...", Toast.LENGTH_LONG).show();
        String ImUrl = getIntent().getExtras().getString("urlfullscreen");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(ImageFullScreen.this)
                .load(ImUrl)
                .into(imageView);
    }
}
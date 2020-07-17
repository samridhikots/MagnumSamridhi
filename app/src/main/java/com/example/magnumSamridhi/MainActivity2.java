package com.example.magnumSamridhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity2 extends AppCompatActivity {

    private EditText term;
    private Button searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main2);

        term = findViewById (R.id.searchTerm);
        searchBtn = findViewById (R.id.search);
        View view = findViewById (android.R.id.content);
        //hrdfsdc
        if (isConnected()) {
            //Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
            Snackbar.make(view,"Internet Connected",Snackbar.LENGTH_LONG).show();
        } else {
            //Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            Snackbar.make(view,"No Internet, you are offline",Snackbar.LENGTH_LONG).show();
            return;
        }
        searchBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String termString = term.getText ().toString ().trim ();

                if(termString.isEmpty () || termString.length () == 0){
                    Toast.makeText (getApplicationContext (), "Enter the search term please",Toast.LENGTH_LONG).show ();
                }else{
                    Intent i = new Intent (getApplicationContext (), MainActivity.class);
                    i.putExtra ("term", termString);
                    startActivity (i);
                }
            }
        });


    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}
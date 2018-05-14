package com.arm.mercurydroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    CardView  githubButton, linkedInButton, facebookButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        githubButton = (CardView) findViewById(R.id.gitHubButton);
        linkedInButton = (CardView) findViewById(R.id.linkedInButton);
        facebookButton = (CardView) findViewById(R.id.facrbookButton);

        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlGithub = "https://github.com/avimallik";
                Intent gitHub = new Intent(Intent.ACTION_VIEW);
                gitHub.setData(Uri.parse(urlGithub));
                startActivity(gitHub);
            }
        });

        linkedInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urllinkedIn = "https://www.linkedin.com/in/arunav-mallik-avi-31a34b152/";
                Intent linkedIn = new Intent(Intent.ACTION_VIEW);
                linkedIn.setData(Uri.parse(urllinkedIn));
                startActivity(linkedIn);
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlfacebook = "https://www.facebook.com/prolog.fortron";
                Intent facebook = new Intent(Intent.ACTION_VIEW);
                facebook.setData(Uri.parse(urlfacebook));
                startActivity(facebook);
            }
        });

     }

}

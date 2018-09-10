package edu.gatech.mas.letsteach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void getMainScreen(View view) {


        String role = getIntent().getStringExtra("role");

        if(role.equals("volunteer"))
        {
            Intent intent = new Intent(this, RequestFeedVol.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, HomePageOrg.class);
            startActivity(intent);
        }

    }
}

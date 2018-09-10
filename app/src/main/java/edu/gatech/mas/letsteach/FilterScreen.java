package edu.gatech.mas.letsteach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FilterScreen extends AppCompatActivity {

    String domain = "";
    String location = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_screen);

        //Dropdown Menu
        Spinner spinner = (Spinner) findViewById(R.id.filterspinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.domains_array, android.R.layout.simple_spinner_item);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar);

//         Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        final EditText locationFilter =  (EditText) findViewById(R.id.filterlocation);
        final Spinner domainFilter = (Spinner) findViewById(R.id.filterspinner);

        Button filterButton = (Button) findViewById(R.id.filterButton);


        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = locationFilter.getText().toString();
                domain = domainFilter.getSelectedItem().toString();
                Intent i = new Intent(FilterScreen.this, RequestFeedVol.class);
                i.putExtra("location", location);
                i.putExtra("domain", domain);
                startActivity(i);
            }
        });
    }
}

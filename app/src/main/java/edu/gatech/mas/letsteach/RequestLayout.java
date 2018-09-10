package edu.gatech.mas.letsteach;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RequestLayout extends AppCompatActivity {

    int[] IMAGES = {R.drawable.ac, R.drawable.tfa, R.drawable.sfc};
    String[] NAMES = {"Americorp","Teach For America","Stand for Children"};
    String[] DESCRIPTIONS = {"National NGO that helps those in need.","National NGO that helps teach.","NGO that helps children."};
    String[] CONTACTS = {"123-456-7890","495-183-2043","927-193-5302"};
    String[] DOMAINS = {"Math","Science","History"};
    String[] COUNTS = {"3","5","9"};
    String[] WEBSITES = {"https://www.nationalservice.gov/programs/americorps","https://www.teachforamerica.org/","http://stand.org/"};
    String[] ADDRESSES = {"12 Peachtree Street, Atl, GA","23 MLK Drive, Sea., WA"," 45 Lincoln Avenue, NYC, NY"};



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listView11);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);


    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView testView_name = (TextView) view.findViewById(R.id.textView_name);
            TextView testView_description = (TextView) view.findViewById(R.id.textView_description);
            TextView contactView = (TextView) view.findViewById(R.id.contact);
            TextView domainView = (TextView) view.findViewById(R.id.domain);
            TextView countView = (TextView) view.findViewById(R.id.count);
            TextView websiteView = (TextView) view.findViewById(R.id.website);
            TextView addressView = (TextView) view.findViewById(R.id.address);

            imageView.setImageResource(IMAGES[i]);
            testView_name.setText(NAMES[i]);
            testView_description.setText(DESCRIPTIONS[i]);
            contactView.setText("Contact: " + CONTACTS[i]);
            domainView.setText("Domain: " + DOMAINS[i]);
            countView.setText("Count: " + COUNTS[i]);
            websiteView.setText("Website: " + WEBSITES[i]);
            addressView.setText("Address: " + ADDRESSES[i]);

            return view;
        }
    }

}


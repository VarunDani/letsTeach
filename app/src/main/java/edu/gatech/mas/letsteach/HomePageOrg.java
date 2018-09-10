package edu.gatech.mas.letsteach;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import edu.gatech.mas.letsteach.utility.ListBean;
import edu.gatech.mas.letsteach.utility.MainListAdapter;

public class HomePageOrg extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    ArrayList<ListBean> mainListObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_org);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Search Volunteers");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Filter will be here Including options", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Additioni of Main Activity By Varun Dani
        final RecyclerView mainLists = (RecyclerView) findViewById(R.id.listViewOrg);
        mainLists.setHasFixedSize(true);
        LinearLayoutManager layMngr = new LinearLayoutManager(this);
        mainLists.setLayoutManager(layMngr);

        //ImageView imgView=(ImageView) findViewById(R.id.img_icon);
        Drawable drawable  = getResources().getDrawable(R.drawable.team1);
        //imgView.setImageDrawable(drawable);

        //DBHandler db = new DBHandler(MainActivity.this);
        mainListObj = new ArrayList<ListBean>();
        mainListObj.add(new ListBean(1,"Varun Dani","Available for Java Development",drawable));
        mainListObj.add(new ListBean(2,"Nikita Juneja","Maths/Technical Volunteership",drawable));
        mainListObj.add(new ListBean(3,"Anushree","Algengra/machine Learning Volunteer",drawable));
        mainListObj.add(new ListBean(4,"Sai","Social Science Volunteer",drawable));


        final MainListAdapter adapter = new MainListAdapter(this, mainListObj);
        mainLists.setAdapter(adapter);

        mainLists.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new MainListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Do after Integration from John - on click show John's Screen

                Intent i = new Intent(HomePageOrg.this, ProfileFragment.class);
                i.putExtra("list_id", mainListObj.get(position).getListId());
                startActivity(i);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //TODO
            }

            @Override
            public void onOverflowMenuClick(View v, final int position) {
                PopupMenu popup = new PopupMenu(getActivity(), v);
                /*popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


                });*/
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_context_popup, popup.getMenu());
                popup.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page_org, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final ArrayList<ListBean> filteredModelList = filter(mainListObj, query);

        //finding Recycler View and Notify Its Adapter after Filter
        final RecyclerView mainLists = (RecyclerView) findViewById(R.id.listViewOrg);
        MainListAdapter adapter = (MainListAdapter) mainLists.getAdapter();
        adapter.setListForAdapter(filteredModelList);
        adapter.notifyDataSetChanged();
        mainLists.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static ArrayList<ListBean> filter(ArrayList<ListBean> actualList, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final ArrayList<ListBean> filteredList = new ArrayList<>();
        for (ListBean model : actualList) {
            final String text = model.getListName().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredList.add(model);
            }
        }
        return filteredList;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, HomePageOrg.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, ProfileFragmentOrg.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        else if (id == R.id.nav_requestList) {
            Intent intent = new Intent(this, RequestLayout.class);
            startActivity(intent);

        }else if (id == R.id.nav_request) {
            Intent intent = new Intent(this, RequestFormActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    final public Activity getActivity() {
        return this;
    }

}

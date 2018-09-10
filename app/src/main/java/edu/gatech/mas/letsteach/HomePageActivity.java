package edu.gatech.mas.letsteach;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class HomePageActivity extends AppCompatActivity {


    RequestLayout requestFragment;
    ProfileFragment profileFragment;
    OrganizationFragment orgFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    myDeviceClick();
                    return true;
                case R.id.navigation_dashboard:
                    myProfileClick();
                    return true;
                case R.id.navigation_notifications:
                    myLoginClick();
                    return true;
            }
            return false;
        }

    };

    private FragmentTransaction fragmentTransaction;

    public void myDeviceClick() {
        if (requestFragment == null) {
            requestFragment = new RequestLayout();
        }
       /* if (!requestFragment.isAdded()) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, requestFragment);
//            fragmentTransaction.addToBackStack(RequestLayout.class.getSimpleName());
            fragmentTransaction.commit();
        }*/
    }


    public void myProfileClick() {
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
        }
      /*  if (!profileFragment.isAdded()) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, profileFragment);
//            fragmentTransaction.addToBackStack(ProfileFragment.class.getSimpleName());
            fragmentTransaction.commit();
        }*/
    }



    public void myLoginClick() {
        if (orgFragment == null) {
            orgFragment = new OrganizationFragment();
        }
        /*if (!orgFragment.isAdded()) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, orgFragment);
//            fragmentTransaction.addToBackStack(OrganizationFragment.class.getSimpleName());
            fragmentTransaction.commit();
        }*/
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (requestFragment == null) {
            requestFragment = new RequestLayout();
        }
       /* if (!requestFragment.isAdded()) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, requestFragment);
            fragmentTransaction.commit();
        }*/

    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("backStackCount", "" + backStackCount);
        if (backStackCount != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}

package edu.gatech.mas.letsteach;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.android.GsonFactory;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;
import edu.gatech.mas.letsteach.ai.TTS;
import edu.gatech.mas.letsteach.databinding.ActivityOnboardingPlaceholderBinding;
import edu.gatech.mas.letsteach.databinding.ItemCardBinding;
import edu.gatech.mas.letsteach.feed.Feed;
import edu.gatech.mas.letsteach.feed.FeedAdapter;
import edu.gatech.mas.letsteach.itemanimator.ItemAnimatorFactory;

public class RequestFeedVol extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , SearchView.OnQueryTextListener,AIListener {


    private int mContentViewHeight;

    private ActivityOnboardingPlaceholderBinding mBinding;
    private RecyclerAdapter mAdapter = new RecyclerAdapter();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FeedAdapter adapter;
    private ArrayList<Feed> feedList;

    //AI
    private AIService aiService;
    public static final String TAG = RequestFeedVol.class.getName();
    private Gson gson = GsonFactory.getGson();


    //JSON
    private Map<String, String> mparams;
    private JSONArray objJson = null;
    private String res = "";

    private String searchName = "";
    private String searchDomain = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_feed_vol);


        // Fake a long startup time
        /*new Handler().postDelayed(this::onFakeCreate, getResources()
                .getInteger(android.R.integer.config_mediumAnimTime));*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Request Feed");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        feedList = new ArrayList<>();
        adapter = new FeedAdapter(this, feedList);

        // sets our layout manager and recycler view
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);




        // create a new object feed to be inserted in our adapter.

        final AIConfiguration config = new AIConfiguration("73b0933f76a74515bc2e4f8089baf2be",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        searchName = "";
        searchDomain = "";
        try{
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
               String name = bundle.getString("location");
               String domain = bundle.getString("domain");
                searchName = name!=null ? name : "";
                searchDomain = domain!=null ? domain:"";

                System.out.println("Location: " + name + " Domain: " + domain);
            } else {
                System.out.println("Empty");
            }
        }
        catch(Exception ee)
        {
            Log.e("Problems","",ee);
        }
        try{

            //JSONArray ss = getObjectRequest("https://test-45590.firebaseio.com/requirements.json",new HashMap<String,String>());
            //Log.d("TAG", ss.toString());
            HttpCustomRequest h = new HttpCustomRequest();
            h.execute("https://test-45590.firebaseio.com/requirements.json");
            System.out.println(res);
            //getFromDb();
        }
        catch(Exception ee)
        {
            Log.e("Problems","",ee);
        }

    }



  /*  public void getFromDb() {
        final OkHttpClient client = new OkHttpClient();
        try {
            String url = "https://test-45590.firebaseio.com/volunteers.json";
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (Exception e) {

        }

    }*/

    public void listenButtonOnClick(final View view) {
        aiService.startListening();
    }


    public void contactOrg(View v) {
        Intent i = getPackageManager().getLaunchIntentForPackage("com.androstock.smsapp");
        startActivity(i);
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
        getMenuInflater().inflate(R.menu.home_page_req, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
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
        if (id == R.id.action_filters) {
            Intent intent = new Intent(this, FilterScreen.class);
            startActivity(intent);
        }
        if (id == R.id.action_match) {
            multipleFiltersAND("java,sunday : evening");
            return true;
        }
        if (id == R.id.action_reset) {
            try{
                feedList = new ArrayList<>();

                HttpCustomRequest h = new HttpCustomRequest();
                h.execute("https://test-45590.firebaseio.com/requirements.json");
                System.out.println(res);

            }
            catch(Exception ee)
            {
                Log.e("Problems","",ee);
            }

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
            Intent intent = new Intent(this, RequestFeedVol.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, ProfileFragment.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, HomePageVol.class);
            startActivity(intent);
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



    @Override
    public boolean onQueryTextChange(String query) {
        final ArrayList<Feed> filteredModelList = filter(feedList, query);

        //finding Recycler View and Notify Its Adapter after Filter
        final RecyclerView mainLists = (RecyclerView) findViewById(R.id.recycler_view);
        FeedAdapter mlAdapter = (FeedAdapter) mainLists.getAdapter();
        //adapter = new FeedAdapter(this, filteredModelList);
        mlAdapter.setListForAdapter(filteredModelList);
        mlAdapter.notifyDataSetChanged();
        mainLists.scrollToPosition(0);
        return true;
    }

    public boolean requirementFilter(String query) {
        final ArrayList<Feed> filteredModelList = filter22(feedList, query);

        //finding Recycler View and Notify Its Adapter after Filter
        final RecyclerView mainLists = (RecyclerView) findViewById(R.id.recycler_view);
        FeedAdapter mlAdapter = (FeedAdapter) mainLists.getAdapter();
        //adapter = new FeedAdapter(this, filteredModelList);
        mlAdapter.setListForAdapter(filteredModelList);
        mlAdapter.notifyDataSetChanged();
        mainLists.scrollToPosition(0);
        return true;
    }

    public boolean multipleFiltersAND(String query) {
        final ArrayList<Feed> filteredModelList = filter33(feedList, query);

        //finding Recycler View and Notify Its Adapter after Filter
        final RecyclerView mainLists = (RecyclerView) findViewById(R.id.recycler_view);
        FeedAdapter mlAdapter = (FeedAdapter) mainLists.getAdapter();
        //adapter = new FeedAdapter(this, filteredModelList);
        mlAdapter.setListForAdapter(filteredModelList);
        mlAdapter.notifyDataSetChanged();
        mainLists.scrollToPosition(0);
        return true;
    }
    public boolean multipleFiltersCustom(String query,String orgName) {
        final ArrayList<Feed> filteredModelList = filter44(feedList, query,orgName);

        //finding Recycler View and Notify Its Adapter after Filter
        final RecyclerView mainLists = (RecyclerView) findViewById(R.id.recycler_view);
        FeedAdapter mlAdapter = (FeedAdapter) mainLists.getAdapter();
        //adapter = new FeedAdapter(this, filteredModelList);
        mlAdapter.setListForAdapter(filteredModelList);
        mlAdapter.notifyDataSetChanged();
        mainLists.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static ArrayList<Feed> filter(ArrayList<Feed> actualList, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final ArrayList<Feed> filteredList = new ArrayList<>();
        for (Feed model : actualList) {
            final String text = model.getName().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredList.add(model);
            }
        }
        return filteredList;
    }
    private static ArrayList<Feed> filter22(ArrayList<Feed> actualList, String query) {
        final String lowerCaseQuery = query.trim().toLowerCase().trim();

        final ArrayList<Feed> filteredList = new ArrayList<>();
        for (Feed model : actualList) {
            final String text = model.getDescription().toLowerCase();
            if (text.toLowerCase().contains(lowerCaseQuery)) {
                filteredList.add(model);
            }
        }
        return filteredList;
    }

    private static ArrayList<Feed> filter33(ArrayList<Feed> actualList, String query) {
        final String lowerCaseQuery = query.trim().toLowerCase().trim();

        String[] words = lowerCaseQuery.split(",");

        final ArrayList<Feed> filteredList = new ArrayList<>();
        for (Feed model : actualList) {
            final String text = model.getDescription().toLowerCase();
            if (checkAnd(words,text)) {
                filteredList.add(model);
            }
        }
        return filteredList;
    }

    private static ArrayList<Feed> filter44(ArrayList<Feed> actualList, String query,String orgName) {
        final String lowerCaseQuery = query.trim().toLowerCase().trim();

        String[] words = lowerCaseQuery.split(",");

        final ArrayList<Feed> filteredList = new ArrayList<>();
        for (Feed model : actualList) {
            final String text = model.getDescription().toLowerCase();
            final String text2 = model.getName().toLowerCase();
            if (checkAnd(words,text)||text2.equalsIgnoreCase(orgName)) {
                filteredList.add(model);
            }
        }
        return filteredList;
    }

    private static boolean checkOr(String[] words,String text)
    {
        for(String s : words)
        {
            if(text.toLowerCase().contains(s))return true;
        }
        return false;
    }
    private static boolean checkAnd(String[] words,String text)
    {
        for(String s : words)
        {
            if(!text.toLowerCase().contains(s))return false;
        }
        return true;
    }

    private void startCollapseToolbarAnimation(Runnable onCollapseEnd) {
        final ValueAnimator valueHeightAnimator = ValueAnimator
                .ofInt(mContentViewHeight, getToolbarHeight(this));

        valueHeightAnimator.addUpdateListener(animation -> {
            /*ViewGroup.LayoutParams lp = mBinding.toolbar.getLayoutParams();
            lp.height = (Integer) animation.getAnimatedValue();
            mBinding.toolbar.setLayoutParams(lp);*/
        });

        valueHeightAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onCollapseEnd.run();
            }
        });

        valueHeightAnimator.start();
    }



    private void onFakeCreate() {
        //setTheme(R.style.AppTheme);

        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_onboarding_placeholder);

        ViewCompat.animate(mBinding.appBar112)
                .alpha(1)
                .start();

        mBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recycler.setItemAnimator(ItemAnimatorFactory.slidein());
        mBinding.recycler.setAdapter(mAdapter);

        mBinding.toolbar.post(() -> {
            mContentViewHeight = mBinding.toolbar.getHeight();

            mAdapter.addAll(ModelItem.fakeItems());

            // Animate fab
            ViewCompat.animate(mBinding.fab)
                    .setStartDelay(getResources().getInteger(android.R.integer.config_mediumAnimTime))
                    .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                    .scaleY(1)
                    .scaleX(1)
                    .start();

            startCollapseToolbarAnimation(() -> {
                // Fire the recycler's ItemAnimator
                mAdapter.addAll(ModelItem.fakeItems());

                // Animate fab
                ViewCompat.animate(mBinding.fab)
                        .setStartDelay(getResources().getInteger(android.R.integer.config_mediumAnimTime))
                        .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                        .scaleY(1)
                        .scaleX(1)
                        .start();
            });
        });
    }


    static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
        private final List<ModelItem> mItems = new ArrayList<>();

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final ItemCardBinding binding = ItemCardBinding.inflate(LayoutInflater
                    .from(parent.getContext()), parent, false);

            return new RecyclerViewHolder(binding);
        }

        void addAll(List<ModelItem> items) {
            int pos = getItemCount();
            mItems.addAll(items);
            notifyItemRangeInserted(pos, mItems.size());
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.bind(mItems.get(position));
        }

        @Override
        public int getItemCount()    {
            return mItems.size();
        }


        static class RecyclerViewHolder extends RecyclerView.ViewHolder {
            private final ItemCardBinding mBinding;

            RecyclerViewHolder(ItemCardBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            void bind(ModelItem modelItem) {
                mBinding.setItem(modelItem);
                mBinding.imgSampleimage.setImageResource(modelItem.imgId);
            }
        }
    }

    private static int getToolbarHeight(Context context) {
        final TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
    }


    @Override
    public void onError(final AIError error) {
        System.out.print("Erorrrr"+error.getMessage());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*View parentLayout = findViewById(R.id.content_request_feed_vol);
                Snackbar.make(parentLayout,"Errorrr ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //Problem
                System.out.print("Erorrrr");
            }
        });
    }

    @Override
    public void onListeningStarted() {}

    @Override
    public void onListeningCanceled() {}

    @Override
    public void onListeningFinished() {}

    @Override
    public void onAudioLevel(final float level) {}

    @Override
    public void onResult(final AIResponse response) {
        Result result = response.getResult();

       /* // Get parameters
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }

        // Show results in TextView.
        System.out.print("Query:" + result.getResolvedQuery() +
                "\nAction: " + result.getAction() +
                "\nParameters: " + parameterString);*/

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onResult");

                //resultTextView.setText(gson.toJson(response));
                Log.i(TAG, gson.toJson(response));

                Log.i(TAG, "Received success response");

                // this is example how to get different parts of result object
                final Status status = response.getStatus();
                Log.i(TAG, "Status code: " + status.getCode());
                Log.i(TAG, "Status type: " + status.getErrorType());

                final Result result = response.getResult();
                Log.i(TAG, "Resolved query: " + result.getResolvedQuery());

                Log.i(TAG, "Action: " + result.getAction());

                final String speech = result.getFulfillment().getSpeech();
                Log.i(TAG, "Speech: " + speech);
                TTS.speak(speech);

                final Metadata metadata = result.getMetadata();
                if (metadata != null) {
                    Log.i(TAG, "Intent id: " + metadata.getIntentId());
                    Log.i(TAG, "Intent name: " + metadata.getIntentName());
                }

                final HashMap<String, JsonElement> params = result.getParameters();
                if (params != null && !params.isEmpty()) {
                    Log.i(TAG, "Parameters: ");
                    for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                        Log.i(TAG, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
                    }
                }

                String keyword = params.get("subject").toString().replace('"',' ').trim();

                char[] str = keyword.toCharArray();
                char[] newStr = new char[keyword.length()];
                int i = 0;
                for(char c : str)
                {
                    if(Character.isAlphabetic(c))newStr[i++]=c;
                }
                String newString = new String(newStr);
                requirementFilter(newString.replace(" ","").trim());
            }

        });
    }



    private class HttpCustomRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result;
            String inputLine;

            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);

                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(Exception e){
                e.printStackTrace();
                result = null;
            }

            return result;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
            res = result;
            Drawable drawable  = getResources().getDrawable(R.drawable.a7);
            try{
                JSONObject jsnobject = new JSONObject(result);

                System.out.println(res);


                Iterator<String> iterator = jsnobject.keys();

                while (iterator.hasNext()) {
                    String aa = iterator.next();
                    System.out.println("value= " + aa);
                    //System.out.println("What is "+jsnobject.getJSONArray(aa).toString());
                    //System.out.println(jsnobject.getJSONArray(aa).toString());
                    JSONObject ss = jsnobject.getJSONObject(aa);
                    System.out.println("Harry "+ss);

                    String name = (String)ss.get("organisation");
                    String description  = "Required Volunteer for "+ss.get("domain");
                    String total  = "No of Requirements : "+ss.get("count");

                    JSONObject avail = ((JSONObject)ss.getJSONObject("availability").get("days"));
                    Iterator<String> ii = avail.keys();
                    String avab = "";
                    while (ii.hasNext()) {
                        String key = ii.next();
                        JSONArray val = avail.getJSONArray(key);
                        String ava = "";
                        for(int x = 0;x<val.length();x++)
                        {
                            ava+=val.get(x)+" ";
                        }
                        avab+= "\n"+key +" : "+ava;
                        System.out.println(ava);
                    }
                    System.out.println();

                    Feed feed = new Feed(name,
                            total,
                            "",
                            "\n"+description+"\n"+avab,
                            "1 DAYS AGO",drawable);
                    feedList.add(0, feed);
                }

                adapter.notifyDataSetChanged();

            }
            catch(Exception e )
            {
                System.out.println(e);

                Feed feed = new Feed("AmeriCorps",
                        "National NGO that helps those in need",
                        "",
                        "Volunteer Required to teach Mathematics to Primary schools and have prior " +
                                "experience in teaching in school or Maths/Algebra. Flexible with timings. ",
                        "3 DAYS AGO",drawable);
                feedList.add(feed);

                feed = new Feed("AmeriCorps",
                        "National NGO that helps those in need",
                        "",
                        "Volunteer Required to teach Mathematics to Primary schools and have prior " +
                                "experience in teaching in school or Maths/Algebra. Flexible with timings. ",
                        "1 DAY AGO",drawable);
                feedList.add(feed);

                feed = new Feed("AmeriCorps",
                        "National NGO that helps those in need",
                        "",
                        "Volunteer Required to teach Mathematics to Primary schools and have prior " +
                                "experience in teaching in school or Maths/Algebra. Flexible with timings. ",
                        "10 HOURS AGO",drawable);
                feedList.add(feed);

                feed = new Feed("varun",
                        "This is test entry",
                        "",
                        "Volunteer Required For Java \n Availability : Sunday : Evening ",
                        "26 MINUTES AGO",drawable);
                feedList.add(0, feed);

                //call this every time you're going to add new items in your recycler view
                adapter.notifyDataSetChanged();
            }

            final RecyclerView mainLists = (RecyclerView) findViewById(R.id.recycler_view);
            FeedAdapter mlAdapter = (FeedAdapter) mainLists.getAdapter();
            //adapter = new FeedAdapter(this, filteredModelList);
            mlAdapter.setListForAdapter(feedList);
            mlAdapter.notifyDataSetChanged();
            mainLists.scrollToPosition(0);

            if(!searchName.equals("") || !searchDomain.equals("")){
                multipleFiltersCustom(searchDomain,searchName);
                searchName = "";
                searchDomain="";
            }


        }

    }
}

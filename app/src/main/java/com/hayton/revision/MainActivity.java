package com.hayton.revision;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.support.v7.widget.SearchView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends BaseActivity {

    private FragmentAdapter adapter;
    private ViewPager pager;
    private ArrayList<Integer> defaultImg;
    private ArrayList<String> imageUrl;
    private ProgressBar progressBar;
    private RelativeLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onBaseCreate();

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        pager = (ViewPager) findViewById(R.id.pager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressLayout = (RelativeLayout) findViewById(R.id.progresslayout);
        progressLayout.setVisibility(View.GONE);

        Intent intent = getIntent();
        handleIntent(intent);

        imageUrl = new ArrayList<>();
        defaultImg = new ArrayList<>();
        defaultImg.add(R.drawable.default_first);
        defaultImg.add(R.drawable.default_second);
        defaultImg.add(R.drawable.default_third);
        defaultImg.add(R.drawable.default_fourth);
        defaultImg.add(R.drawable.default_fifth);

        adapter = new FragmentAdapter(getSupportFragmentManager(), imageUrl, defaultImg);

        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu_base, menu);

         SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
         SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
         searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
         searchView.setIconifiedByDefault(true);
         searchView.setSubmitButtonEnabled(true);
         return true;
     }

    /*
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
    }*/
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            progressLayout.setVisibility(View.VISIBLE);
            progressLayout.bringToFront();
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(MainActivity.this,
                    SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            String url = null;
            try {
                url = cseParams.myurl+"&key="+cseParams.key+"&cx="+cseParams.cx+
                        "&imageSize="+cseParams.imgsz+"&fileType="
                        +cseParams.as_filetype+"&imgSize="+cseParams.imgsz+"&num="+cseParams.init_num
                        +"&q="+ URLEncoder.encode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            System.out.println("fucking url:> "+url);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    System.out.println("fucking response:> " + jsonObject);
                    imageUrl.clear();
                    try {
                        JSONArray result = jsonObject.getJSONArray("items");
                        for (int i=0;i<result.length();i++){
                            imageUrl.add(result.getJSONObject(i).getJSONObject("pagemap")
                                    .getJSONArray("cse_image").getJSONObject(0).getString("src"));
                        }
                        System.out.println("fucking image url:> "+imageUrl);
                        adapter = new FragmentAdapter(getSupportFragmentManager(), imageUrl, defaultImg);

                        pager.setAdapter(adapter);
                        progressLayout.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            //RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
            VolleySingleton.getInstance(this).addToRequestQueue(request);

        }
    }


}

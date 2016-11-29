package com.hayton.revision;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


public class BaseActivity extends AppCompatActivity {

    //@Override
    protected void onBaseCreate() {
        //super.onBaseCreate(savedInstanceState);
        //setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);

        //Intent intent = getIntent();
        //handleIntent(intent);

    }

    /*@Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }*/

    /*private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(BaseActivity.this,
                    SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            JSONObject object = new JSONObject();
            try {
                object.put("q", query);
                object.put("cx", cseParams.cx);
                object.put("fileType",cseParams.as_filetype);
                object.put("imgSize", cseParams.imgsz);
                object.put("num", cseParams.init_num);
                object.put("key", cseParams.key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = cseParams.myurl+"&key="+cseParams.key+"&cx="+cseParams.cx+"&fileType="
                    +cseParams.as_filetype+"&imgSize="+cseParams.imgsz+"&num="+cseParams.init_num
                    +"&q="+query;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    System.out.println("fucking response:> "+jsonObject);
                    ArrayList<String> imageUrl = new ArrayList<>();
                    try {
                        JSONArray result = jsonObject.getJSONArray("items");
                        for (int i=0;i<result.length();i++){
                            imageUrl.add(result.getJSONObject(i).getJSONObject("pagemap")
                                    .getJSONArray("cse_image").getJSONObject(0).getString("src"));
                        }
                        System.out.println("fucking image url:> "+imageUrl);
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
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);

        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);*/
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
        if (id == android.R.id.home){
            Toast.makeText(BaseActivity.this, "navigation icon pressed", Toast.LENGTH_SHORT).show();
        }
        //if (id == R.id.action_search){

        //}

        return super.onOptionsItemSelected(item);
    }
}

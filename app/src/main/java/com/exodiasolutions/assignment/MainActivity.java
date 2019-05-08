package com.exodiasolutions.assignment;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exodiasolutions.assignment.Adapter.ImagesAdapter;
import com.exodiasolutions.assignment.Model.ImagesData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    int scrollPosition = 0;
    RecyclerView recyclerView;
    ImagesAdapter adapter;
    int width;
    int save;
    EditText editText;
    int page =1;
    Store store;
    Gson gson;
    ProgressBar progressBar;
    ArrayList<ImagesData> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        store = new Store(MainActivity.this);
        gson = new Gson();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        save = width/2;
        progressBar = findViewById(R.id.pBar);
        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    page++;
                    if(isNetworkConnected())
                    fetchimages(editText.getText().toString(),page);

                }
            }
        });
        if(isNetworkConnected())
        fetchimages("",1);
        else{
            Type type = new TypeToken<ArrayList<ImagesData>>(){}.getType();
            if(store.getValue("")!=null){
            arrayList = gson.fromJson(store.getValue(""),type);
            adapter = new ImagesAdapter(MainActivity.this, arrayList,width/2);
            recyclerView.setAdapter(adapter);
            }
        }

    }


    private void fetchimages(final String query,int page){
        progressBar.setVisibility(View.VISIBLE);
        String link;
        if(query.equalsIgnoreCase("")) {
            link = "https://api.unsplash.com/photos/?page="+page+"&client_id=271b116bb5dbe1f8253a5bf43d2fd9175139b47a81ab301fa1dd6f53cb2d8397";
        } else{
            link = "https://api.unsplash.com/search/photos/?page="+page+"&query="+query+"&client_id=271b116bb5dbe1f8253a5bf43d2fd9175139b47a81ab301fa1dd6f53cb2d8397";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET,link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.i("Response", "["+response+"]");
                        if(response!=null) {
                            try {
                                JSONArray jsonArray = null;
                                if(query.equalsIgnoreCase("")) {
                                     jsonArray = new JSONArray(response);
                                }
                                else{
                                    jsonArray = new JSONArray(new JSONObject(response).getString("results"));
                                }
                                for(int i= 0;i<jsonArray.length();i++){
                                    String url = new JSONObject(jsonArray.getJSONObject(i).getString("urls")).getString("small");
                                    arrayList.add(new ImagesData(url,null));
                                }
                                store.setValue(query,gson.toJson(arrayList));
                                if(arrayList.size()<=10){
                                adapter = new ImagesAdapter(MainActivity.this, arrayList,save);
                                recyclerView.setAdapter(adapter);
                                }
                                else{
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}}},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                })
            ;
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void two(View v){
       setview(2);
    }
    public void three(View v){
        setview(3);
    }
    public void four(View v){
        setview(4);
    }

    public void setview(int num){
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,num));
        adapter = new ImagesAdapter(MainActivity.this, arrayList,width/num);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(scrollPosition);
        save = width/num;
    }

    public void search(View v){
        arrayList.clear();
        if(isNetworkConnected())
        fetchimages(editText.getText().toString(),1);
        else {
            Type type = new TypeToken<ArrayList<ImagesData>>() {
            }.getType();
            if (store.getValue(editText.getText().toString()) != null) {
                arrayList = gson.fromJson(store.getValue(editText.getText().toString()), type);
                adapter = new ImagesAdapter(MainActivity.this, arrayList, save);
                recyclerView.setAdapter(adapter);
            }
        }
        page =1;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

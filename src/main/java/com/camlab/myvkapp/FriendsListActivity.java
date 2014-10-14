package com.camlab.myvkapp;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by alex on 14.10.2014.
 */
public class FriendsListActivity extends ListActivity {

    private final List<Friend> friends = new ArrayList<Friend>();
    private ArrayAdapter<Friend> listAdapter;
    private String vkAccessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends);

        vkAccessToken = getIntent().getStringExtra("VKAccessToken");

        listAdapter = new ImageAndTextListAdapter (this,  friends);
        setListAdapter(listAdapter);

        startLoading();


    }

    private void startLoading() {
        new NetworkLoading().execute();
    }
    class NetworkLoading extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost request = new HttpPost("https://api.vk.com/method/friends.get?");
                List<NameValuePair> qparams = new ArrayList<NameValuePair>();

                qparams.add(new BasicNameValuePair("fields", "last_name, photo"));
                qparams.add(new BasicNameValuePair("access_token", vkAccessToken));
                request.setEntity(new UrlEncodedFormEntity(qparams, HTTP.UTF_8));
                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();
                String responseText = EntityUtils.toString(entity);

                friendsNamesParser(responseText);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        private void friendsNamesParser(String jsonStr) {
            try {
                // Create JSON
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray jsonArr = jsonObj.getJSONArray("response");
                for (int i=0; i< jsonArr.length(); i++){
                    JSONObject friendJSONObj = jsonArr.getJSONObject(i);
                    // Get full name
                    String friendFullName = "";
                    friendFullName += friendJSONObj.getString("first_name");
                    friendFullName +=" ";
                    friendFullName += friendJSONObj.getString("last_name");
                    // Get Avatar URL
                    String friendAvatarURLStr = friendJSONObj.getString("photo");
                    // Save
                    friends.add(new Friend (friendFullName,friendAvatarURLStr));
                }


            }
            catch (JSONException e) {
                e.printStackTrace();

            }
        }
        protected void onPostExecute(Void params) {
            //Update
            listAdapter.notifyDataSetChanged();
        }
    }
}


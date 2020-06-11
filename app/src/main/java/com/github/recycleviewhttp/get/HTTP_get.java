package com.github.recycleviewhttp.get;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.github.recycleviewhttp.R;
import com.github.recycleviewhttp.RecycleAdapter;
import com.github.recycleviewhttp.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class HTTP_get extends AsyncTask<String,Void,String> {

    public Activity activity;
    public RecyclerView recyclerView;
    public RecycleAdapter adapter;
    public ArrayList<User> listUser;

    public HTTP_get(Activity activity){
        this.activity = activity;
        recyclerView = this.activity.findViewById(R.id.recycleView);
        listUser = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(activity, "SUCCESS", Toast.LENGTH_SHORT).show();
        adapter = new RecycleAdapter(activity, activity, listUser);
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected String doInBackground(String... voids) {
        String result = "";

        String namaProject = "http://192.168.100.4:8080/GitHub/Http/get.php";

            try {

                URL url = new URL(namaProject);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                // MENGAMBIL DATA
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }

                JSONArray parent = new JSONArray(result);

                int i =0;

                while (i <= parent.length()){
                    JSONObject child = parent.getJSONObject(i);
                    String id = child.getString("id");
                    String name = child.getString("name");
                    String stats = child.getString("stats");
                    String picture = child.getString("picture");
                    listUser.add(new User(id,name,stats,picture));
                    i++;
                }

                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return result;
    }
}

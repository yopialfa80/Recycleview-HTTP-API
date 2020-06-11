package com.github.recycleviewhttp.CRUD.delete;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.github.recycleviewhttp.R;
import com.github.recycleviewhttp.RecycleAdapter;
import com.github.recycleviewhttp.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class HTTP_delete extends AsyncTask<String,Void,String> {
    public ArrayList<User> listUser;
    public Activity activity;
    public RecyclerView recyclerView;
    public RecycleAdapter adapter;

    public HTTP_delete(Activity activity)
    {
        this.activity = activity;
        recyclerView = this.activity.findViewById(R.id.recycleView);
    }

    @Override
    protected void onPreExecute() {

    }



    @Override
    protected void onPostExecute(String result) {
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(activity, "success", duration).show();
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected String doInBackground(String... voids) {
        listUser = new ArrayList<>();
        adapter = new RecycleAdapter(activity, activity, listUser);
        String result = "";
        String id = voids[0];

        try {
            URL url = new URL("http://192.168.100.4:8080/GitHub/Http/delete.php");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            http.connect();

            // MENGIRIM DATA
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
            String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();
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
                id = child.getString("id");
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
            Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            result = e.getMessage();
            Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}

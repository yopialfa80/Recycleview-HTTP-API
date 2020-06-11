package com.github.recycleviewhttp.post;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class HTTP_post extends AsyncTask<String,Void,String> {

    AlertDialog dialog;
    Context context;
    public Boolean login = false;
    public HTTP_post(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }
    @Override
    protected void onPostExecute(String result) {
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, result, duration).show();
    }
    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String name = voids[0];
        String stats = voids[1];
        String namaPicture = voids[2];
        String picture = voids[3];

        String namaProject = "http://192.168.100.4:8080/GitHub/Http/post.php";

            try {
                URL url = new URL("http://192.168.100.4:8080/GitHub/Http/post.php");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                http.connect();

                // MENGIRIM DATA
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        +"&&"+URLEncoder.encode("stats", "UTF-8") + "=" + URLEncoder.encode(stats, "UTF-8")
                        +"&&"+URLEncoder.encode("namaPicture", "UTF-8") + "=" + URLEncoder.encode(namaPicture, "UTF-8")
                        +"&&"+URLEncoder.encode("picture", "UTF-8") + "=" + URLEncoder.encode(picture, "UTF-8");
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
                reader.close();
                ips.close();
                http.disconnect();

                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                result = e.getMessage();
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

        }
        return result;
    }
}


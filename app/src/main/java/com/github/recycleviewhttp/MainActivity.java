package com.github.recycleviewhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.github.recycleviewhttp.CRUD.add.Add;
import com.github.recycleviewhttp.get.HTTP_get;
import com.github.recycleviewhttp.post.HTTP_post;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private static final int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    String namaPicture;
    ImageView userPicture;
    private SearchView searchView;
    RecycleAdapter adapter;
    private ArrayList<User> listUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton action = (FloatingActionButton) findViewById(R.id.action);
        listUser = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        get();

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(MainActivity.this);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        get();
    }

    public void get() {
        new HTTP_get(this).execute();
    }

    public void showBottomSheetDialog(final Context context) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        Button get = view.findViewById(R.id.get);
        Button post = view.findViewById(R.id.post);
        Button postandget = view.findViewById(R.id.postandget);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.action_add, null);

                BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(view);
                dialog.show();

                final EditText edName = view.findViewById(R.id.edName);
                final EditText edStats = view.findViewById(R.id.edStats);
                userPicture = view.findViewById(R.id.userPicture);
                Button send = view.findViewById(R.id.btnSend);

                userPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFileChooser();
                    }
                });

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = edName.getText().toString();
                        String stats = edStats.getText().toString();
                        String picture = getStringImage(bitmap);
                        HTTP_post httpPost = new HTTP_post(context);
                        httpPost.execute(name, stats, namaPicture, picture);


                    }
                });
            }
        });

        postandget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == MainActivity.RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            try {
                String getImageRequestName = String.valueOf(data); // Nama Default
                String compressImage = (getImageRequestName.substring(getImageRequestName.lastIndexOf("/") + 1)); // Ambil Setelah Slash ('/')
                compressImage = (compressImage.substring(0,compressImage.lastIndexOf("."))); // Ambil Sebelum Titik ('.')
                namaPicture = compressImage;
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                userPicture.setImageBitmap(bitmap);
            }

            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
}
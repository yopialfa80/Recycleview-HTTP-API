package com.github.recycleviewhttp.CRUD.edit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.recycleviewhttp.MainActivity;
import com.github.recycleviewhttp.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class Edit extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    String namaPicture, id;
    EditText edName, edStats;
    ImageView userPicture;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_edit);

        Intent intent = getIntent();
        id  = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String stats = intent.getStringExtra("stats");
        String picture = intent.getStringExtra("picture");

        edName = findViewById(R.id.edName);
        edStats = findViewById(R.id.edStats);
        userPicture = findViewById(R.id.userPicture);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        userPicture.setOnClickListener(this);

        edName.setText(name);
        edStats.setText(stats);
        Glide.with(this)
                .load(picture)
                .into(userPicture);
    }

    public void edit(){
        String name = edName.getText().toString();
        String stats = edStats.getText().toString();
        String picture = getStringImage(bitmap);
        HTTP_edit http_edit = new HTTP_edit(Edit.this);
        http_edit.execute(id, name, stats, namaPicture, picture);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSend:
                edit();
                break;

            case R.id.userPicture:
                showFileChooser();
                break;
        }
    }
}

package com.moviles.dispositivos.segundaevaluacion;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_PHOTO_FOR_AVATAR =3;
    private ArrayList<Imagen> imagenArrayList;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 1 ;
    private Button btnAgregar;
    private adaptadorImagen adaptadorImagen;

    Animation anim_zoomIN;
    Animation anim_zoomOut;

    Boolean   es_zoomIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnAgregar= (Button) findViewById(R.id.btnAgregar);
        imagenArrayList= new ArrayList<>();
        //inicializamos el adaptador
        adaptadorImagen= new adaptadorImagen(this,  imagenArrayList);
        //inicializando el listview
        ListView listView= (ListView) findViewById(R.id.lstImagenes);
        //seteando el adaptador al listview
        listView.setAdapter(adaptadorImagen);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            //ask for permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_CODE);
            }
        }
        this.btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        //zoom
        anim_zoomIN = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        anim_zoomIN.setDuration(1000);
        anim_zoomIN.setFillAfter(true);

        anim_zoomOut = AnimationUtils.loadAnimation(this,R.anim.zoom_out);
        anim_zoomOut.setDuration(1000);
        anim_zoomOut.setFillAfter(true);
    }
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                if(bmp!=null) {
                    String ruta = data.getData().getPath();
                    imagenArrayList.add(new Imagen(ruta, bmp));
                    adaptadorImagen.notifyDataSetChanged();
                }
            } catch (IOException e) {
                Toast.makeText(this,"Error loading image",Toast.LENGTH_SHORT);
                e.printStackTrace();
            }

        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}


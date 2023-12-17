package com.example.a29_pr;

import static android.content.ClipData.newIntent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    File directory;
    final int TYPE_PHOTO = 1;
    final int TYPE_VIDEO = 2;
    final int REQUEST_CODE_PHOTO = 1;
    final int REQUEST_CODE_VIDEO = 2;
    final String TAG = "myLogs";
    ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDirectory();
        ivPhoto = findViewById(R.id.ivPhoto);
    }

    public void onClickPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }
    public void onClickVideo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_VIDEO));
        startActivityForResult(intent, REQUEST_CODE_VIDEO);
    }
    // !!!!!!!!!!!!!!   Карты   !!!!!!!!!!!!!! \\
    public void onClickMaps(View view) {
        // Создание URI для открытия Google Карт с конкретным местоположением
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=place");

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Приложение Google Карт не установлено", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickBrowser(View view) {
        // Создание интента для открытия браузера с определенным URL-адресом
        String url = "https://www.google.com"; // Замените на ваш URL
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Проверка наличия приложения браузера на устройстве
        if (browserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(browserIntent); // Запуск интента для открытия браузера
        } else {
            Toast.makeText(this, "Браузер не установлен", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickYouTube(View view) {
        // Создание интента для открытия YouTube с определенным видео
        String videoId = "d4e39-QGDR4"; // Идентификатор видео из ссылки
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));

        // Проверка наличия приложения YouTube на устройстве
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent); // Запуск интента для открытия YouTube
        } else {
            // Если приложение YouTube не установлено, открываем ссылку в браузере
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));
            startActivity(intent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE_PHOTO) {
            // ... your existing photo handling logic
        }

        if (requestCode == REQUEST_CODE_VIDEO) {
            // ... your existing video handling logic
        }
    }
    private Uri generateFileUri(int type) {
        File file = null;
        switch (type) {
            case TYPE_PHOTO:
                file = new File(directory.getPath() + "/" + "photo_"
                        + System.currentTimeMillis() + ".jpg");
                break;
            case TYPE_VIDEO:
                file = new File(directory.getPath() + "/" + "video_"
                        + System.currentTimeMillis() + ".mp4");
                break;
        }
        Log.d(TAG, "fileName = " + file);
        return Uri.fromFile(file);
    }
    private void createDirectory() {
        directory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyFolder");
        if (!directory.exists())
            directory.mkdirs();
    }
}

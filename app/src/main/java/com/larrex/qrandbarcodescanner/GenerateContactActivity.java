package com.larrex.qrandbarcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.larrex.qrandbarcodescanner.databinding.ActivityGenerateContactBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateContactActivity extends AppCompatActivity {

    ActivityGenerateContactBinding binding;
    Bitmap bitmap;
    String name;
    String number;

    String format;

    private static final String TAG = "GenerateContactActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_contact);

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.download) {
                    if (ActivityCompat.checkSelfPermission(GenerateContactActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                            ActivityCompat.checkSelfPermission(GenerateContactActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        ActivityCompat.requestPermissions(GenerateContactActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23);

                    } else {

                        if (bitmap != null) {

//                            QRGSaver qrgSaver = new QRGSaver();
//                            String path = Environment.getExternalStorageDirectory()+ File.separator+"Easy Decoder";
//                            qrgSaver.save(path, url, bitmap, QRGContents.ImageType.IMAGE_JPEG);
////                            qrgSaver.


                            try {
                                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Easy Decoder" + File.separator + name + ".png");

                                if (file.exists()) {
                                    file.delete();
                                }

                                if (file.getParentFile().exists()) {
                                    file.getParentFile().mkdir();
                                }

                                if (Build.VERSION.SDK_INT <= 21){
                                    if (!file.exists()) {
                                        file.mkdirs();
                                    }
                                }



                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
//                                fileOutputStream.flush();
                                fileOutputStream.close();

                                Snackbar.make(binding.getRoot(), "Image saved successfully", Snackbar.LENGTH_LONG).show();
                                MediaScannerConnection.scanFile(GenerateContactActivity.this,new String[]{file.toString()},null,null );


                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                intent.setData(Uri.fromFile(file));
                                sendBroadcast(intent);


//                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
//                                        Uri.parse("file://"
//                                                + Environment.getExternalStorageDirectory())));

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Log.d(TAG, "onMenuItemClick: "+e.getLocalizedMessage());
                                Snackbar.make(binding.getRoot(), e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                                Snackbar.make(binding.getRoot(), e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

                            }

                        }
                    }
                }

                return true;
            }
        });

//        setContentView();
    }

    public void GenerateUrlQrCode(View view) {
        name = binding.name.getText().toString();
        number = binding.number.getText().toString();

        if (!name.trim().isEmpty() && !number.trim().isEmpty()) {
            binding.proBar.setVisibility(View.VISIBLE);

            format = name.toUpperCase() + "\n\n" + number;

            QRGEncoder qrgEncoder = new QRGEncoder(format, null, QRGContents.Type.TEXT, 500);
            bitmap = qrgEncoder.getBitmap();
            binding.image.setImageBitmap(bitmap);
            binding.proBar.setVisibility(View.GONE);
        }
    }
}
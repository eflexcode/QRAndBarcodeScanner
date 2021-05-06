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
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.larrex.qrandbarcodescanner.databinding.ActivityGenerateSmsBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateSmsActivity extends AppCompatActivity {

    ActivityGenerateSmsBinding binding;
    String number;
    String sms;
    String format;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();

        binding = DataBindingUtil.setContentView(this,R.layout.activity_generate_sms);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.download) {
                    if (ActivityCompat.checkSelfPermission(GenerateSmsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                            ActivityCompat.checkSelfPermission(GenerateSmsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        ActivityCompat.requestPermissions(GenerateSmsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23);

                    } else {

                        if (bitmap != null) {

                            try {
                                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Easy Decoder" + File.separator + number+".png");
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

                                MediaScannerConnection.scanFile(GenerateSmsActivity.this,new String[]{file.toString()},null,null );
                                Snackbar.make(binding.getRoot(), "Image saved successfully", Snackbar.LENGTH_LONG).show();

                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                intent.setData(Uri.fromFile(file));
                                sendBroadcast(intent);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
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


    }

    public void GenerateUrlQrCode(View view) {
        number = binding.number.getText().toString();
        sms = binding.sms.getText().toString();

        if (!number.trim().isEmpty() && !sms.trim().isEmpty()) {
            binding.proBar.setVisibility(View.VISIBLE);

            format = number+"\n"+sms;

            QRGEncoder qrgEncoder = new QRGEncoder(format, null, QRGContents.Type.TEXT, 500);
            bitmap = qrgEncoder.getBitmap();
            binding.image.setImageBitmap(bitmap);
            binding.proBar.setVisibility(View.GONE);
        }
    }

}
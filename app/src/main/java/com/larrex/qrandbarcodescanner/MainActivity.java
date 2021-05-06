package com.larrex.qrandbarcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.larrex.qrandbarcodescanner.databinding.ActivityMainBinding;
import com.larrex.qrandbarcodescanner.fragment.GenerateBarCodeListFragment;
import com.larrex.qrandbarcodescanner.fragment.ScanCodeFragment;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            setTheme(R.style.Theme_QRAndBarcodeScannerDark);
////            binding.toolbar.setPopupTheme(R.style.Theme_QRAndBarcodeScannerDark);
//        } else {
//            setTheme(R.style.Theme_QRAndBarcodeScanner);
////            binding.toolbar.setPopupTheme(R.style.Theme_QRAndBarcodeScanner);
//        }

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            binding.toolbar.setPopupTheme(R.style.Theme_QRAndBarcodeScannerDark);
//        } else {
//            binding.toolbar.setPopupTheme(R.style.Theme_QRAndBarcodeScanner);
//        }

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.rate:
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                        break;
//                    case R.id.history:
//                        break;
//                    case R.id.theme:
//                        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        } else {
//                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                        finish();
//                        break;
                }

                return true;
            }
        });

        QRGEncoder qrgEncoder = new QRGEncoder("", null, QRGContents.Type.TEXT, 500);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.home_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    public void OpenGenerateButtumShit(View view) {

        GenerateBarCodeListFragment codeListFragment = new GenerateBarCodeListFragment();
        codeListFragment.show(getSupportFragmentManager(), "gen");

    }

    public void OpenScanBotttomSheet(View view) {
        ScanCodeFragment scanCodeFragment = new ScanCodeFragment();
        scanCodeFragment.show(getSupportFragmentManager(), "scan");
    }
}
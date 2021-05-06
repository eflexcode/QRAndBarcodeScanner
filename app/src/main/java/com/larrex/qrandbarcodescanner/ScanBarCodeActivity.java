package com.larrex.qrandbarcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;
import com.larrex.qrandbarcodescanner.databinding.ActivityScanBarCodeBinding;

public class ScanBarCodeActivity extends AppCompatActivity{

    ActivityScanBarCodeBinding binding;
    CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_bar_code);
//        setContentView();

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);

        TextView resultText = dialog.findViewById(R.id.result_text);
//        Button closeDialog = dialog.findViewById(R.id.cancel);
        Button copy = dialog.findViewById(R.id.copy);

        codeScanner = new CodeScanner(this, binding.scannerView);

//        codeScanner.sc

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
//              type.setText(result.);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultText.setText(result.getText());
                        dialog.show();
//                        result.
                    }
                });
            }
        });

        codeScanner.startPreview();

//        closeDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                codeScanner.startPreview();
//            }
//        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied to clipboard", resultText.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                dialog.dismiss();
                codeScanner.startPreview();

                Toast.makeText(ScanBarCodeActivity.this, "Copied to clipboard!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void refreshPreview(View view) {
        codeScanner.startPreview();
    }
}
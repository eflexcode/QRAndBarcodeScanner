package com.larrex.qrandbarcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.larrex.qrandbarcodescanner.databinding.ActivityScanImageFileBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ScanImageFileActivity extends AppCompatActivity {

    ActivityScanImageFileBinding binding;
    Dialog dialog;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scan_image_file);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_image_file);


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);

        resultText = dialog.findViewById(R.id.result_text);
//        Button closeDialog = dialog.findViewById(R.id.cancel);
        Button copy = dialog.findViewById(R.id.copy);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied to clipboard", resultText.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                dialog.dismiss();

                Toast.makeText(ScanImageFileActivity.this, "Copied to clipboard!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void pickImageFile(View view) {

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        startActivityForResult(pickIntent, 111);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //the code is from here https://stackoverflow.com/questions/55427308/how-to-scan-qr-code-from-imagenot-form-camera i copied it
            case 111:
                if (data == null || data.getData() == null) {
                    Log.e("TAG", "The uri is null, probably the user cancelled the image selection process using the back button.");
                    return;
                }
                Uri uri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap == null) {
                        Log.e("TAG", "uri is not a bitmap," + uri.toString());
                        return;
                    }
                    int width = bitmap.getWidth(), height = bitmap.getHeight();
                    int[] pixels = new int[width * height];
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    bitmap.recycle();
                    bitmap = null;
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    MultiFormatReader reader = new MultiFormatReader();
                    try {
                        Result result = reader.decode(bBitmap);
                        resultText.setText(result.getText());
                        dialog.show();
                        Toast.makeText(this, "The content of the QR image is: " + result.getText(), Toast.LENGTH_LONG).show();
                    } catch (NotFoundException e) {
                        Toast.makeText(this, "Image does not contain a qr or barcode", Toast.LENGTH_LONG).show();
                    }
                } catch (FileNotFoundException e) {
                    Log.e("TAG", "can not open file" + uri.toString(), e);
                }
                break;
        }
    }

}
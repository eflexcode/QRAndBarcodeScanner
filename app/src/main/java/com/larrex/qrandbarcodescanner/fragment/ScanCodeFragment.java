package com.larrex.qrandbarcodescanner.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.larrex.qrandbarcodescanner.GenerateSmsActivity;
import com.larrex.qrandbarcodescanner.GenerateUrlActivity;
import com.larrex.qrandbarcodescanner.R;
import com.larrex.qrandbarcodescanner.ScanBarCodeActivity;
import com.larrex.qrandbarcodescanner.ScanImageFileActivity;
import com.larrex.qrandbarcodescanner.databinding.FragmentScanCodeBinding;

public class ScanCodeFragment extends BottomSheetDialogFragment {

    FragmentScanCodeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_code, container, false);

        binding.qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 23);

                } else {
                    dismiss();
                    startActivity(new Intent(getContext(), ScanBarCodeActivity.class));

                }
            }
        });

        binding.bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 23);

                } else {
                    dismiss();
                    startActivity(new Intent(getContext(), ScanImageFileActivity.class));

                }
            }
        });

        return binding.getRoot();
    }
}
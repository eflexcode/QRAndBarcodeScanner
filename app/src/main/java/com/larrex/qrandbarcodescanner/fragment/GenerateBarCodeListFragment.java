package com.larrex.qrandbarcodescanner.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.larrex.qrandbarcodescanner.GenerateContactActivity;
import com.larrex.qrandbarcodescanner.GenerateEmailActivity;
import com.larrex.qrandbarcodescanner.GenerateLocationActivity;
import com.larrex.qrandbarcodescanner.GeneratePlainActivity;
import com.larrex.qrandbarcodescanner.GenerateSmsActivity;
import com.larrex.qrandbarcodescanner.GenerateUrlActivity;
import com.larrex.qrandbarcodescanner.GenerateWifiKeyActivity;
import com.larrex.qrandbarcodescanner.R;
import com.larrex.qrandbarcodescanner.databinding.FragmentGenerateBarCodeListBinding;

public class GenerateBarCodeListFragment extends BottomSheetDialogFragment {

    FragmentGenerateBarCodeListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generate_bar_code_list, container, false);
//        getContext().getTheme().applyStyle(R.style.Theme_QRAndBarcodeScannerDark,true);

        View view = binding.getRoot();

        binding.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getContext(), GenerateUrlActivity.class));
            }
        });
        binding.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getContext(), GenerateContactActivity.class));
            }
        });
        binding.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getContext(), GeneratePlainActivity.class));
            }
        });
        binding.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getContext(), GenerateEmailActivity.class));
            }
        });
        binding.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getContext(), GenerateSmsActivity.class));
            }
        });
        binding.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getContext(), GenerateLocationActivity.class));
            }
        });

        binding.wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getContext(), GenerateWifiKeyActivity.class));
            }
        });


        return view;
    }
}
package com.example.verifierdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.verifierdemo.databinding.ActivityMainBinding;
import com.verify.verifier.api.aadharVerifier.AadharVerifier;
import com.verify.verifier.api.aadharVerifier.enums.EAadharResponse;
import com.verify.verifier.api.aadharVerifier.interfaces.IAadharVeriferResponse;
import com.verify.verifier.api.aadharVerifier.model.Captcha;
import com.verify.verifier.api.aadharVerifier.model.OtpResp;
import com.verify.verifier.api.aadharVerifier.model.UserInfo;

import java.io.File;

public class MainActivity extends AppCompatActivity implements IAadharVeriferResponse {

    private int PICK_ZIP_FILE=101;
    String TAG="loader";
    /*static {
        System.loadLibrary("verifier");
    }*/
    ActivityMainBinding binding;
    AadharVerifier aadharVerifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        aadharVerifier = AadharVerifier.getInstance(this).setCallback(this);
        if(!new File(getFilesDir(),"source").exists())
            pickZipFile();
        else{
            aadharVerifier.loadLibrary().init().getCaptcha();
        }

        binding.btnCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aadharVerifier.getOTP(binding.etAadhar.getText().toString(),binding.etCatpcha.getText().toString());
            }
        });

        binding.btnotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aadharVerifier.getUserInfo(binding.etotp.getText().toString());
            }
        });

    }

    private void pickZipFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/zip");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Choose a ZIP file"), PICK_ZIP_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_ZIP_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri zipUri = data.getData();
                if (zipUri != null) {
                    try {
                        aadharVerifier.loadLibraryFromZip(zipUri);
                        aadharVerifier.init().getCaptcha();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to unzip and load .so file", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public void toImageView(String base64Image){
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        binding.ivcaptcha.setImageBitmap(decodedByte);
    }

    @Override
    public <T> void onSuccess(EAadharResponse resp, T data) {
        switch (resp){
            case CAPTCHA:
                Captcha captcha = (Captcha)data;
                runOnUiThread(()->toImageView(captcha.getCaptcha()));
                break;
            case OTP:
                OtpResp otpResp = (OtpResp)data;
                Log.d(TAG, "onSuccess: "+otpResp.getMessage());
                break;
            case USERDATA:
                UserInfo userInfo = (UserInfo) data;
                runOnUiThread(()->binding.tvUserData.setText(userInfo.toString()));
                break;
        }
    }

    @Override
    public void onError(Exception ex) {

    }
}
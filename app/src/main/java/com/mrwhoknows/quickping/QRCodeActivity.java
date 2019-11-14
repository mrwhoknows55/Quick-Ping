package com.mrwhoknows.quickping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeActivity extends AppCompatActivity {

    MaterialButton scanBtn, genBtn;
    TextInputLayout ownNumberWrapper;
    ImageView qrcodeImage;
    String lastApi, ownPhoneNo;
    public static final String SHARED_PREF = "Shared Prefs";
    public static final String OWN_PH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        scanBtn = findViewById(R.id.scanBtn);
        genBtn = findViewById(R.id.popUpBtn);
        ownNumberWrapper = findViewById(R.id.ownMobNumberWrapper);
        qrcodeImage = findViewById(R.id.qrCodeImg);

        genBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genrateQRCode();
            }
        });

//        Done Add persistent mob number and qr code (load and update)
        loadNumber();
        updateNumber();

    }

    public void genrateQRCode(){
        getInput();
        try {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = multiFormatWriter.encode(lastApi, BarcodeFormat.QR_CODE, 500, 500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                qrcodeImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
//               done Add persistent mob number and qr code (save)
            saveNumber();
    }

    public void scanQRBtnClicked(View view){
        scanQRCode();
    }

    public void  scanQRCode(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(QRCodeActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setPrompt("Scanning QR Code");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null &&  result.getContents() != null){
            String fromQRCode = result.getContents();
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(fromQRCode)));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getInput(){
        ownPhoneNo = ownNumberWrapper.getEditText().getText().toString();

        if(ownPhoneNo.isEmpty()){
            ownNumberWrapper.setError("Reqired!");
        }else{
            ownNumberWrapper.setError(null);
            lastApi = "https://api.whatsapp.com/send?phone=91" + ownPhoneNo;
        }
    }

    public void saveNumber(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(OWN_PH, ownNumberWrapper.getEditText().getText().toString().trim());
        editor.apply();
    }

    public void loadNumber(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        ownPhoneNo = sharedPreferences.getString(OWN_PH,"");
    }

    public void updateNumber(){
        ownNumberWrapper.getEditText().setText(ownPhoneNo);
        genrateQRCode();
    }

}

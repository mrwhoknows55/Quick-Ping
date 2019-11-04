package com.mrwhoknows.quickping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    String lastApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        scanBtn = findViewById(R.id.scanBtn);
        genBtn = findViewById(R.id.popUpBtn);
        ownNumberWrapper = findViewById(R.id.ownMobNumberWrapper);
        qrcodeImage = findViewById(R.id.qrCodeImg);
    }

    public void genrateQRCode(View view){
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
        String ownPhoneNo = ownNumberWrapper.getEditText().getText().toString();

        if(ownPhoneNo.isEmpty()){
            ownNumberWrapper.setError("Reqired!");
        }else{
            ownNumberWrapper.setError(null);
            lastApi = "https://api.whatsapp.com/send?phone=91" + ownPhoneNo;
        }
    }
}

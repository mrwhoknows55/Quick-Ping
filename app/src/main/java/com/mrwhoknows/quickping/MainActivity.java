package com.mrwhoknows.quickping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
//    Variable Declarations
    Button messageBtn;
    TextInputLayout countryCodeInput, mobNumberInput, messageInput;
    String mobNumber, countryCode, message, finalNumber,apiLink;

//    Method Will Called When Message Button is clicked
    public void clicked(View view){
        Log.i("BUTTON","Message Button Clicked");

        getInput();
        checkError();
    }

    void checkError(){
        if (TextUtils.isEmpty(countryCode) && TextUtils.isEmpty(mobNumber)) {
            mobNumberInput.setError("Enter Country Code");
            countryCodeInput.setError("Enter Mobile Number");
        }else if(mobNumber.isEmpty()){
            mobNumberInput.setError("Enter Country Code");
        }else if(countryCode.isEmpty()){
            countryCodeInput.setError("Enter Mobile Number");
        } else {
            mobNumberInput.setError(null);
            countryCodeInput.setError(null);
            setOutput();
        }
    }

    void getInput(){
        mobNumber = mobNumberInput.getEditText().getText().toString();
        countryCode = countryCodeInput.getEditText().getText().toString();
        message = messageInput.getEditText().getText().toString();
    }

    void setOutput(){

//        finalNumber = countryCode.substring(0, 0) + countryCode.substring(1) + mobNumber;

        finalNumber = countryCode.substring(countryCode.lastIndexOf("+") +1) + mobNumber;

        if(message.isEmpty()){
            apiLink = "https://api.whatsapp.com/send?phone=" + finalNumber;
        }else{
            apiLink = "https://api.whatsapp.com/send?phone=" + finalNumber + "&text=" +message;
        }
        Log.i("LINK",apiLink);

        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(apiLink)));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foundByID();

    }

//    Finding components with their ids
    void foundByID(){
        messageInput = findViewById(R.id.messageWrapper);
        mobNumberInput = findViewById(R.id.mobNumberWrapper);
        countryCodeInput = findViewById(R.id.dialCodeWrapper);
        messageBtn = findViewById(R.id.messageBtn);
    }

}

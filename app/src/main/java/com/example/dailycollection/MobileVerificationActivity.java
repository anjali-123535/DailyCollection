package com.example.dailycollection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MobileVerificationActivity extends AppCompatActivity {
    private EditText editTextMobile;
    FirebaseAuth mAuth;
    private String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        //if(mAuth.getCurrentUser()==null)
        setContentView(R.layout.activity_mobile_verification);
        //else
          //  setContentView(R.layout.activity_main);

        editTextMobile = findViewById(R.id.editTextMobile);



        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 mobile = editTextMobile.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(MobileVerificationActivity.this, OtpVerificationActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });
    }
private void verifiedUserToMain()
{
    mobile=mAuth.getCurrentUser().getPhoneNumber();
    Intent intent = new Intent(MobileVerificationActivity.this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    intent.putExtra("mobile", mobile);
    startActivity(intent);
}
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null)
            findViewById(R.id.verificationscreen).setVisibility(View.VISIBLE);
        else
            verifiedUserToMain();
    }
}

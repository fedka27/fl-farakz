package test.kz.terah.projectfarakzm.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import kz.terah.projectfarakzm.R;

public class SecondActivity extends AppCompatActivity implements OnVerificationStateChangedCallback, OnCompleteListener<AuthResult> {
    private FirebaseAuth auth;
    private EditText codeText;
    private String mVerificationId;
    private PhoneVerifyProvider verifyProvider;
    private PhoneAuthProvider.ForceResendingToken token;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        auth = FirebaseAuth.getInstance();
        verifyProvider = PhoneVerifyProvider.getInstance(this);


        String phoneNumber = getIntent().getStringExtra("phone");

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, verifyProvider);

        codeText = findViewById(R.id.editText);

    }

    public void onSendCode(View v) {
        final String code = codeText.getText().toString();
        if (code.isEmpty()) {
            Toast.makeText(this, "Введите проверочный код", Toast.LENGTH_LONG).show();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        auth.signInWithCredential(credential).addOnCompleteListener(this, this);
    }

    public void onResendVerify(View v) {
        if (token != null)
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    getIntent().getStringExtra("phone"),
                    60,
                    TimeUnit.SECONDS,
                    this,
                    verifyProvider,
                    token);
    }

    @Override
    public void onVerificationCompleted(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this, this);
    }

    @Override
    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
        mVerificationId = verificationId;
        this.token = token;
    }


    @Override
    public void onVerificationFailed(FirebaseException e) {
        e.printStackTrace();
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, FirstActivity.class));
//        finish();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            startActivity(new Intent(this, ThirdActivity.class));
        } else {
            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}




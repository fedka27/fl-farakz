package kz.terah.projectfarakzm.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import kz.terah.projectfarakzm.main.MainActivity;
import test.kz.terah.projectfarakzm.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ThirdActivity extends AppCompatActivity {

    EditText eText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        eText = findViewById(R.id.editTextName);
    }


    public void onEnter(View v) {
        final String name = eText.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Введите ваше имя", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) {
            startActivity(new Intent(this, FirstActivity.class));
            finish();
            return;
        }

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(request);


        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}


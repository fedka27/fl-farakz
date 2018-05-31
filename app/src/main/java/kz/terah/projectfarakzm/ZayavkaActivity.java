package kz.terah.projectfarakzm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import test.kz.terah.projectfarakzm.R;

public class ZayavkaActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText5;
    Button buttonEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zayavka);

            editText1 = findViewById(R.id.editText1);
            editText2 = findViewById(R.id.editText2);
            editText3 =  findViewById(R.id.editText3);
            editText5 =  findViewById(R.id.editText5);
            buttonEmail =  findViewById(R.id.buttonEmail);

            buttonEmail.setOnClickListener(v -> {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                        "terahardi@mail.ru"
                });
                String text = String.format("Имя пользователя: %s\n" +
                        " Фамилия пользователя: %s\n" +
                        " Почта пользователя: %s\n" +
                        " Телефон пользователя: %s\n%s",
                        editText1.getText().toString(),
                        editText2.getText().toString(),
                        editText3.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),
                        editText5.getText().toString());


            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Заявка из приложения");
            emailIntent.putExtra(Intent.EXTRA_TEXT, text);
            ZayavkaActivity.this.startActivity(Intent.createChooser(emailIntent, "Отправка заявки"));
            });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}

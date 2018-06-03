package kz.terah.projectfarakzm.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import kz.terah.projectfarakzm.R;
import kz.terah.projectfarakzm.main.MainActivity;

public class FirstActivity extends AppCompatActivity {
    private EditText phoneNumberTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Подключение разметки
        setContentView(R.layout.activity_first);
        phoneNumberTv = findViewById(R.id.enterPhoneAuth);

    }


    //Вызывается при нажатии на "Войти" - из разметки
    public void onAuth(View v) {
        final String phone = phoneNumberTv.getText().toString();

        //Проверка омера телефона
        if (phone.isEmpty()) {
            Toast.makeText(FirstActivity.this, "Введите номер телефона", Toast.LENGTH_LONG).show();
            return;
        }

        //Открыть следующий экран
        Intent loginIntent = new Intent(this, SecondActivity.class);
        loginIntent.putExtra("phone", phone);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Если сессия сохранилась - открыть главный экран
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    //Звонок в службу поддержки ( нет в дизайне )
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+77024111198"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Открыть ВК ссылку ( нет в дизайне )
    void onVk(View w) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/farakzkz")));
    }

    //Открыть ИНСТАГРАМ ссылку ( нет в дизайне )
    void goInst(View t) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/terletskiya/")));
    }

    //Позвонить в поддержку ( нет в дизайне )
    void phoneCall(View q) {
        ActivityCompat.requestPermissions(this,
                new String[] {
                        android.Manifest.permission.CALL_PHONE},1);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+77024111198"));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }


}

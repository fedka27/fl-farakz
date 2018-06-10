package kz.terah.projectfarakzm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import kz.terah.projectfarakzm.models.CarPart;

public class ZayavkaActivity extends AppCompatActivity {
    private static final String TAG = ZayavkaActivity.class.getSimpleName();
    private static final String EXTRA_PART_FOR_BUY_NULLABLE = TAG + "_EXTRA_MESSAGE_NULLABLE";
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText messageEditText;
    Button buttonEmail;
    @Nullable CarPart carPart;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, @Nullable CarPart carPart) {
        Intent intent = new Intent(context, ZayavkaActivity.class);
        intent.putExtra(EXTRA_PART_FOR_BUY_NULLABLE, carPart);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Подключение разметки
        setContentView(R.layout.activity_zayavka);

        //Поиск виджетов
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        messageEditText = findViewById(R.id.messageEditText);
        buttonEmail = findViewById(R.id.buttonEmail);

        buttonEmail.setOnClickListener(v -> {
            //Обработка нажатия
            //Посылка запроса на отправку сообщение через email

            //Если запрос идет по детали которой нет - показать сообщение перед отправкой
            if (carPart != null) {
                showMessageForRequestAndSendToEmail();
            } else {
                sendEmailRequest();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initMessageForBuyPart();
    }

    private void initMessageForBuyPart() {
        if (getIntent().hasExtra(EXTRA_PART_FOR_BUY_NULLABLE)) {
            carPart = (CarPart) getIntent().getSerializableExtra(EXTRA_PART_FOR_BUY_NULLABLE);
            messageEditText.setText(getString(R.string.activity_request_template_message_part_,
                    carPart.getPart(),
                    carPart.getCarMark(),
                    carPart.getCarModel()));
        }
    }

    private void showMessageForRequestAndSendToEmail() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_warning)
                .setMessage(R.string.activity_request_message_before_send)
                .setPositiveButton(R.string.action_ok,
                        (dialogInterface, i) -> sendEmailRequest())
                .show();
    }

    private void sendEmailRequest() {
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
                messageEditText.getText().toString());


        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Заявка из приложения");
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(emailIntent, "Отправка заявки"));
        finish();
    }
}

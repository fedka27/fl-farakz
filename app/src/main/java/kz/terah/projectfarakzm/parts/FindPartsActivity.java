package kz.terah.projectfarakzm.parts;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kz.terah.projectfarakzm.R;

public class FindPartsActivity extends AppCompatActivity {

    SearchableSpinner autoCompany;
    SearchableSpinner autoMark;
    SearchableSpinner autoParts;

    ProgressBar progressBar;

    TextView resultText;

    JSONObject data;

    Handler handler;


    Runnable run = () -> {
        //Индикатор загрузки
        progressBar.setVisibility(View.GONE);
        resultText.setVisibility(View.VISIBLE);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Подключение разметки
        setContentView(R.layout.activity_find_parts);

        //Заголовок экрана
        setTitle("Поиск запчастей");

        handler = new Handler();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Поиск элементов из разметки
        autoCompany = findViewById(R.id.autoCompany);
        autoMark = findViewById(R.id.autoMark);
        autoParts = findViewById(R.id.autoParts);
        progressBar = findViewById(R.id.progressBar2);
        resultText = findViewById(R.id.resultText);

        autoCompany.setTitle("Выбери производителя");
        autoMark.setTitle("Выбери марку");
        autoParts.setTitle("Выбери запчасть");

        autoCompany.setPositiveButton("Закрыть");
        autoMark.setPositiveButton("Закрыть");
        autoParts.setPositiveButton("Закрыть");


        resultText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        autoMark.setEnabled(false);
        autoParts.setEnabled(false);

        //Парсинг json файла -> app/src/assests расположение
        data = readJSONFromAsset("auto.json");
        final List<String> companys = new ArrayList<>();
        if (data != null) {
            //Проход по всем элементам
            final Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                //Добавление всех "ключей" - названий марок автомобилей в список ( Honda, Toyota и т.д. )
                companys.add(keys.next());
            }
        }

        //Адаптер выпадающего списка компаний-названий авто
        final ArrayAdapter itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, companys);
        autoCompany.setAdapter(itemAdapter);
        autoCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                autoMark.setEnabled(true);
                //Нажатие на элемент компании
                final String itemAtPosition = (String) parent.getItemAtPosition(position);
                //Проверка! Есть ли марки авто у данной компании
                try {
                    final Iterator<String> keys = data.getJSONObject(itemAtPosition).getJSONObject("marks").keys();
                    final List<String> marks = new ArrayList<>();
                    if (keys != null) {
                        //Отобразить марки
                        while (keys.hasNext())
                            marks.add(keys.next());
                        final ArrayAdapter marksAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, marks);
                        autoMark.setAdapter(marksAdapter);
                    }
                } catch (JSONException e) {
                    //Марок нет - вывести в лог сообщение и продолжить работу приложения
                    e.printStackTrace();
                }

                //Отключить индикатор загрузки
                autoParts.setEnabled(false);
                autoParts.setAdapter(null);
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Когда ни один элемент из компаний не выбран, отключить индикатор загрузки и удалить все элементы из мписка
                autoParts.setEnabled(false);
                autoParts.setAdapter(null);
                autoMark.setEnabled(false);
                autoMark.setAdapter(null);
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }
        });

        //Нажатие на марку
        autoMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                autoParts.setEnabled(true);
                final String itemAtPosition = (String) parent.getItemAtPosition(position);
                //У марки могут быть запчасти
                try {
                    final JSONArray jsonArray = data.getJSONObject((String) autoCompany.getSelectedItem()).getJSONObject("marks").getJSONObject(itemAtPosition).getJSONArray("parts");
                    final List<String> marks = new ArrayList<>();
                    if (jsonArray != null) {
                        int len = jsonArray.length();
                        for (int i = 0; i < len; i++) {
                            marks.add(jsonArray.getString(i));
                        }

                        //Добавить запчасти в список
                        final ArrayAdapter marksAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, marks);
                        autoParts.setAdapter(marksAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Ошибка - вывести в лог и продолжить работу
                }

                //Отключить индикатоор загрузки
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Ничего не выбрано - отключить список и загрузку
                autoParts.setEnabled(false);
                autoParts.setAdapter(null);
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }
        });

        //Нажатие на запчасть
        autoParts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Ничего далее не делать
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Ничего далее не делать
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }
        });

    }

    //Нажатие из разметки - запустить поиск по запчастям
    public void onResultFindParts(View v) {
        progressBar.setVisibility(View.VISIBLE);
        handler.postDelayed(run, 3000);
    }

    //Чтение json файла из ассетов
    public JSONObject readJSONFromAsset(String path) {
        try (InputStream is = getAssets().open(path)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            return new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

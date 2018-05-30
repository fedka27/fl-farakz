package test.kz.terah.projectfarakzm.parts;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import test.kz.terah.projectfarakzm.R;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindPartsActivity extends AppCompatActivity {

    SearchableSpinner autoCompany;
    SearchableSpinner autoMark;
    SearchableSpinner autoParts;

    ProgressBar progressBar;

    TextView resultText;

    JSONObject data;

    Handler handler;


    Runnable run = () -> {
        progressBar.setVisibility(View.GONE);
        resultText.setVisibility(View.VISIBLE);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_parts);

        setTitle("Поиск запчастей");

        handler = new Handler();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


        data = readJSONFromAsset("auto.json");
        final List<String> companys = new ArrayList<>();
        if (data != null) {
            final Iterator<String> keys = data.keys();
            while (keys.hasNext())
                companys.add(keys.next());
        }

        final ArrayAdapter itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, companys);
        autoCompany.setAdapter(itemAdapter);
        autoCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                autoMark.setEnabled(true);
                final String itemAtPosition = (String) parent.getItemAtPosition(position);
                try {
                    final Iterator<String> keys = data.getJSONObject(itemAtPosition).getJSONObject("marks").keys();
                    final List<String> marks = new ArrayList<>();
                    if (keys != null) {
                        while (keys.hasNext())
                            marks.add(keys.next());
                        final ArrayAdapter marksAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, marks);
                        autoMark.setAdapter(marksAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                autoParts.setEnabled(false);
                autoParts.setAdapter(null);
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                autoParts.setEnabled(false);
                autoParts.setAdapter(null);
                autoMark.setEnabled(false);
                autoMark.setAdapter(null);
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }
        });

        autoMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                autoParts.setEnabled(true);
                final String itemAtPosition = (String) parent.getItemAtPosition(position);
                try {
                    final JSONArray jsonArray = data.getJSONObject((String) autoCompany.getSelectedItem()).getJSONObject("marks").getJSONObject(itemAtPosition).getJSONArray("parts");
                    final List<String> marks = new ArrayList<>();
                    if (jsonArray != null) {
                        int len = jsonArray.length();
                        for (int i = 0; i < len; i++) {
                            marks.add(jsonArray.getString(i));
                        }
                        final ArrayAdapter marksAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, marks);
                        autoParts.setAdapter(marksAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                autoParts.setEnabled(false);
                autoParts.setAdapter(null);
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }
        });

        autoParts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                resultText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(run);
            }
        });

    }

    public void onResultFindParts(View v) {
        progressBar.setVisibility(View.VISIBLE);
        handler.postDelayed(run, 3000);
    }

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

package kz.terah.projectfarakzm.popular;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import kz.terah.projectfarakzm.R;
import kz.terah.projectfarakzm.models.Popular;

public class PopularActivity extends AppCompatActivity {

    private List<Popular> popularList = new ArrayList<>();
    private PopularAdapter popularAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);


        //Поиск тулбара из разметки
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        popularAdapter = new PopularAdapter(getSupportFragmentManager());

        initViews();

        initPopularList();
    }

    private void initViews() {
        viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(popularAdapter);
    }

    private void initPopularList() {
        popularList.add(new Popular(R.drawable.fara_mers, "Фара", "Фара левая для Mercedes"));
        popularList.add(new Popular(R.drawable.kapot_mers, "Капот", "Капот чёрный для Mercedes"));
        popularList.add(new Popular(R.drawable.fara_volks, "Фара", "Фара левая для Volkswagen"));

        popularAdapter.setPopularList(popularList);
    }
}

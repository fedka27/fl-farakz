package kz.terah.projectfarakzm.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import kz.terah.projectfarakzm.ZayavkaActivity;
import kz.terah.projectfarakzm.parts.FindPartsActivity;
import kz.terah.projectfarakzm.KontaktiActivity;
import kz.terah.projectfarakzm.MapsActivity;
import kz.terah.projectfarakzm.OnasActivity;
import kz.terah.projectfarakzm.R;
import kz.terah.projectfarakzm.login.FirstActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        FloatingActionButton fab = findViewById(R.id.fab);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            android.Manifest.permission.CALL_PHONE}, 1001);
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:+77016102737"));
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.tVnameUser);
        userName.setText(user.getDisplayName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+77016102737"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        final Context context = this;

        if (id == R.id.nav_find_parts) {
            startActivity(new Intent(context, FindPartsActivity.class));
        } else if (id == R.id.nav_zayavka) {
            startActivity(new Intent(context, ZayavkaActivity.class));
        } else if (id == R.id.nav_gmap) {
            startActivity(new Intent(context, MapsActivity.class));
        } else if (id == R.id.nav_shop) {
            startActivity(new Intent(context, OnasActivity.class));
        } else if (id == R.id.nav_kontakti) {
            startActivity(new Intent(context, KontaktiActivity.class));
        } else if (id == R.id.nav_vk) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/farakzkz")));
        } else if (id == R.id.nav_insta) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/wwwfara_kz/")));
        } else if (id == R.id.nav_exit) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(context, FirstActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


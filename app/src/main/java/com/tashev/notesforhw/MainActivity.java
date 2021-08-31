package com.tashev.notesforhw;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initDrawer(initToolbar());

        if(savedInstanceState==null)
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_container, ListNotesFragment.newInstance())
                .commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // ищем фрагмент, который сидит в контейнере R.id.cities_container
        Fragment backStackFragment = (Fragment)getSupportFragmentManager()
                .findFragmentById(R.id.note_container);
        // если такой есть, и он является CoatOfArmsFragment
        if(backStackFragment!=null&&backStackFragment instanceof CreateNoteFragment){
            //то сэмулируем нажатие кнопки Назад
            onBackPressed();
        }
    }


    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.add, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Toast.makeText(getApplicationContext(), "Здесь будут настройки если понадобятся", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_about:
                        Toast.makeText(getApplicationContext(), "NotesForHW v1.0 \nTashev Victor \n2021", Toast.LENGTH_LONG).show();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
}
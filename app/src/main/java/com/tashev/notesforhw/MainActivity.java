package com.tashev.notesforhw;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.tashev.notesforhw.data.NoteSource;
import com.tashev.notesforhw.data.NoteSourceImpl;
import com.tashev.notesforhw.fragments.ListNotesFragment;
import com.tashev.notesforhw.observer.Publisher;

public class MainActivity extends AppCompatActivity {

    private Publisher publisher = new Publisher();
    private Navigation navigation = new Navigation(getSupportFragmentManager());

    private NoteSource noteSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteSource = new NoteSourceImpl(getResources()).init();

        initToolbar();
        initDrawer(initToolbar());

        navigation.addFragment(R.id.note_container, ListNotesFragment.newInstance(), false);
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

    public Publisher getPublisher() {
        return publisher;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public NoteSource getNoteSource() {
        return noteSource;
    }
}

//TODO 1)Сохранение состояния при повороте экрана; 2)Почему не работает контекстное меню; 3)Навести порядок в коде

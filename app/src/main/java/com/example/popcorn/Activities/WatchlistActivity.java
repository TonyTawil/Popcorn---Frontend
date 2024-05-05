package com.example.popcorn.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcorn.Networking.FetchWatchlistTask;
import com.example.popcorn.R;
import com.example.popcorn.Utils.NavigationManager;
import com.google.android.material.navigation.NavigationView;

public class WatchlistActivity extends AppCompatActivity {
    private RecyclerView watchlistRecyclerView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private SharedPreferences sharedPreferences;
    private NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        Toolbar toolbar = findViewById(R.id.appBarLayout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationManager = new NavigationManager(this, navigationView, drawerLayout);
        navigationManager.updateDrawerContents();

        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (id == R.id.nav_watchlist) {
                finish();
                startActivity(getIntent());
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (id == R.id.nav_logout) {
                navigationManager.logout();
                return true;
            }
            return false;
        });

        watchlistRecyclerView = findViewById(R.id.moviesRecyclerView);
        watchlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (userId != null) {
            FetchWatchlistTask fetchWatchlistTask = new FetchWatchlistTask(watchlistRecyclerView, userId, this);
            fetchWatchlistTask.fetchWatchlist();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}

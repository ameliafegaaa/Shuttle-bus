    package com.example.project;

    import android.content.Intent;
    import android.icu.number.IntegerWidth;
    import android.os.Bundle;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.Window;
    import android.view.WindowManager;
    import android.widget.ImageButton;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import com.google.android.material.navigation.NavigationView;
    import java.util.List;

    public class CampusArticleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

        private RecyclerView recyclerView;
        private CampusArticleAdapter adapter;
        private ImageButton naviOpenClose;
        private NavigationView NaviView;
        private DrawerLayout drawerLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_campus_article);

            NaviView = findViewById(R.id.Navi_View);
            drawerLayout = findViewById(R.id.drawer_layout);
            naviOpenClose = findViewById(R.id.naviOpenClose);

            NaviView.setNavigationItemSelectedListener(this);

            naviOpenClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                }
            });

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            fetchArticles();


        }

        @Override
        public void onBackPressed() {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.Home) {
                Intent intent =new Intent(CampusArticleActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.BusSchedule) {
                Intent intent = new Intent(CampusArticleActivity.this, BusScheduleActivity.class);
                startActivity(intent);
            } else if (id == R.id.CampusArticle) {
                // Already in CampusArticleActivity
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        private void fetchArticles() {
            new ArticleAsyncTask(new ArticleAsyncTask.ArticleCallback() {
                @Override
                public void onArticlesReceived(List<Article> articles) {
                    if (articles != null) {
                        adapter = new CampusArticleAdapter(articles);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }).execute();
        }
    }

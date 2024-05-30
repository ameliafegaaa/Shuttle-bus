package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton naviOpenClose;
    Button btnECamp, btnPuis, btnPu;

    NavigationView naviView;
    DrawerLayout drawerLayout;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        naviView = findViewById(R.id.Navi_View);
        drawerLayout = findViewById(R.id.drawer_layout);
        naviOpenClose = findViewById(R.id.naviOpenClose);
        btnECamp = findViewById(R.id.btn_ecamp);
        btnPuis = findViewById(R.id.btn_puis);
        btnPu = findViewById(R.id.btn_pu);
        webView = findViewById(R.id.webView);

        naviView.setNavigationItemSelectedListener(this);

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


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        btnECamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleWebView("https://ecampus.president.ac.id/");
            }
        });

        btnPuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleWebView("https://puis.president.ac.id/");
            }
        });

        btnPu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleWebView("https://president.ac.id/");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (webView.getVisibility() == View.VISIBLE && webView.canGoBack()) {

            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Home) {
            // Handle Home
        } else if (id == R.id.BusSchedule) {
            Intent intent = new Intent(MainActivity.this, BusScheduleActivity.class);
            startActivity(intent);

        } else if (id == R.id.CampusArticle) {
            Intent intent = new Intent(MainActivity.this, CampusArticleActivity.class);
            startActivity(intent);
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void toggleWebView(String url) {
        if (webView.getVisibility() == View.VISIBLE) {

            webView.setVisibility(View.GONE);
        } else {

            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(url);
        }
    }
}

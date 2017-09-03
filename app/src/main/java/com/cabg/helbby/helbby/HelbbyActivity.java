package com.cabg.helbby.helbby;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;

public class HelbbyActivity extends AppCompatActivity {

    TabLayout tabLayoutHelbby;
    ViewPager viewPagerHelbby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helbby_activity);

        tabLayoutHelbby = (TabLayout) findViewById(R.id.tabLayoutHelbby);
        viewPagerHelbby = (ViewPager) findViewById(R.id.viewPagerHelbby);
        tabLayoutHelbby.setupWithViewPager(viewPagerHelbby);
        viewPagerHelbby.setAdapter(new AdapterHelbby(getSupportFragmentManager()));

        if (AccessToken.getCurrentAccessToken() == null) {
        }


    }
    /* private void goLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent); */
}
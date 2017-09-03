package com.cabg.helbby.helbby;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;

public class HelbbyActivity extends AppCompatActivity {

    TabLayout tabLayoutHelbby;
    ViewPager viewPagerHelbby;
    private int[] tabIcons = {
            R.mipmap.ic_person_white_24dp,
            R.mipmap.ic_lock_white_24dp,
            R.mipmap.ic_email_white_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helbby_activity);
        if (AccessToken.getCurrentAccessToken() == null) {
        }

        tabLayoutHelbby = (TabLayout) findViewById(R.id.tabLayoutHelbby);
        viewPagerHelbby = (ViewPager) findViewById(R.id.viewPagerHelbby);

        tabLayoutHelbby.setupWithViewPager(viewPagerHelbby);
        viewPagerHelbby.setAdapter(new AdapterHelbby(getSupportFragmentManager()));


        setupTabIcons();




    }

    private void setupTabIcons() {
        tabLayoutHelbby.getTabAt(0).setIcon(tabIcons[0]);
        tabLayoutHelbby.getTabAt(1).setIcon(tabIcons[1]);
        tabLayoutHelbby.getTabAt(2).setIcon(tabIcons[2]);
    }



   /*  private void goLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent); */
}
package jp.ac.dendai.im.cps.footopic;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;

import jp.ac.dendai.im.cps.footopic.adapter.DonFragmentPagerAdapter;
import jp.ac.dendai.im.cps.footopic.util.App;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Don";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.setActivity(this);

        manager = getSupportFragmentManager();

        initPager();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_auth: {
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initPager() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        final DonFragmentPagerAdapter adapter = new DonFragmentPagerAdapter(manager);
        viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
        tabs.setViewPager(viewPager);
    }
}

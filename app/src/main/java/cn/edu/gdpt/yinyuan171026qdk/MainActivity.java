package cn.edu.gdpt.yinyuan171026qdk;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdpt.yinyuan171026qdk.fragment.MeFragment;
import cn.edu.gdpt.yinyuan171026qdk.fragment.SouyeFragment;

public class MainActivity extends AppCompatActivity {
    List<Fragment> fragmentList;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tablayout1);
        tabLayout.addTab(tabLayout.newTab().setText("首页").setIcon(R.drawable.icon_certificate));

        tabLayout.addTab(tabLayout.newTab().setText("我").setIcon(R.drawable.icon_signal));
        fragmentList = new ArrayList<>();
        fragmentList.add(new SouyeFragment());

        fragmentList.add(new MeFragment());
        MyVPFragmentAdapter myVPFragmentAdapter = new MyVPFragmentAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setAdapter(myVPFragmentAdapter);

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }


        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    if (tab == tabLayout.getTabAt(i))
                        viewPager.setCurrentItem(i);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    class MyVPFragmentAdapter extends FragmentPagerAdapter {
        public MyVPFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}


package com.codeinsight.app.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.codeinsight.app.R;
import com.codeinsight.app.databinding.ActivityMainBinding;
import com.codeinsight.app.ui.analytics.AnalyticsFragment;
import com.codeinsight.app.ui.dashboard.DashboardFragment;
import com.codeinsight.app.ui.mistakes.MistakesFragment;
import com.codeinsight.app.ui.problems.ProblemsFragment;
import com.codeinsight.app.ui.recommendations.RecommendationsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            loadFragment(new DashboardFragment());
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment fragment = null;

            if (itemId == R.id.nav_home) {
                fragment = new DashboardFragment();
            } else if (itemId == R.id.nav_problems) {
                fragment = new ProblemsFragment();
            } else if (itemId == R.id.nav_mistakes) {
                fragment = new MistakesFragment();
            } else if (itemId == R.id.nav_analysis) {
                fragment = new AnalyticsFragment();
            } else if (itemId == R.id.nav_insights) {
                fragment = new RecommendationsFragment();
            }

            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}


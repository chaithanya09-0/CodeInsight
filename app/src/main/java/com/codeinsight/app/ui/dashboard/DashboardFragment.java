package com.codeinsight.app.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codeinsight.app.CodeInsightApp;
import com.codeinsight.app.R;
import com.codeinsight.app.data.local.database.CodeInsightDatabase;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.databinding.FragmentDashboardBinding;
import com.codeinsight.app.domain.analysis.DashboardStats;
import com.codeinsight.app.domain.analysis.DayActivity;
import com.codeinsight.app.domain.analysis.WeakTopicSummary;
import com.codeinsight.app.ui.adapter.ProblemAdapter;
import com.codeinsight.app.ui.leetcode.LeetCodeSyncActivity;
import com.codeinsight.app.ui.problems.ProblemDetailActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel viewModel;
    private ProblemAdapter recentProblemsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        setupRecyclerView();
        setupChartsStyle();
        setupProfileSelector();

        binding.btnGotoAnalysis.setOnClickListener(v -> {
            com.google.android.material.bottomnavigation.BottomNavigationView bnv = requireActivity().findViewById(R.id.bottom_navigation);
            if (bnv != null) bnv.setSelectedItemId(R.id.nav_analysis);
        });

        binding.btnGotoInsights.setOnClickListener(v -> {
            com.google.android.material.bottomnavigation.BottomNavigationView bnv = requireActivity().findViewById(R.id.bottom_navigation);
            if (bnv != null) bnv.setSelectedItemId(R.id.nav_insights);
        });

        viewModel.getDashboardStats().observe(getViewLifecycleOwner(), this::updateDashboardUi);
    }

    private void setupRecyclerView() {
        recentProblemsAdapter = new ProblemAdapter(problem -> {
            Intent intent = new Intent(requireContext(), ProblemDetailActivity.class);
            intent.putExtra(ProblemDetailActivity.EXTRA_PROBLEM_ID, problem.problem.getId());
            startActivity(intent);
        });
        binding.rvRecentProblems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvRecentProblems.setAdapter(recentProblemsAdapter);
    }

    private void setupChartsStyle() {
        // Pie chart setup
        PieChart pie = binding.chartDifficultyPie;
        pie.getDescription().setEnabled(false);
        pie.setDrawHoleEnabled(true);
        pie.setHoleColor(Color.TRANSPARENT);
        pie.setTransparentCircleRadius(45f);
        pie.setHoleRadius(40f);
        pie.setEntryLabelColor(Color.WHITE);
        pie.setEntryLabelTextSize(11f);
        pie.getLegend().setEnabled(false);

        // Bar chart setup
        BarChart bar = binding.chartWeeklyBar;
        bar.getDescription().setEnabled(false);
        bar.setDrawGridBackground(false);
        bar.setDrawBarShadow(false);
        bar.setPinchZoom(false);
        bar.setDoubleTapToZoomEnabled(false);
        bar.getLegend().setEnabled(false);
        bar.getAxisRight().setEnabled(false);

        XAxis xAxis = bar.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_on_surface_variant));
        xAxis.setGranularity(1f);

        bar.getAxisLeft().setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_on_surface_variant));
        bar.getAxisLeft().setDrawGridLines(true);
        bar.getAxisLeft().setGridColor(ContextCompat.getColor(requireContext(), R.color.dark_surface));
        bar.getAxisLeft().setAxisMinimum(0f);
    }

    private void updateDashboardUi(DashboardStats stats) {
        if (stats == null || getContext() == null) return;

        // Metric cards
        binding.tvTotalSolved.setText(String.valueOf(stats.totalSolved));
        int ratePct = (int) Math.round(stats.acceptanceRate);
        binding.tvAcceptanceRate.setText(ratePct + "%");

        if (stats.averageSolvingTimeMinutes > 0) {
            binding.tvAvgTime.setText(Math.round(stats.averageSolvingTimeMinutes) + "m");
        } else {
            binding.tvAvgTime.setText("—");
        }

        // Breakdown texts
        binding.tvEasyBreakdown.setText("Easy: " + stats.easySolved + "/" + stats.easyTotal);
        binding.tvMediumBreakdown.setText("Medium: " + stats.mediumSolved + "/" + stats.mediumTotal);
        binding.tvHardBreakdown.setText("Hard: " + stats.hardSolved + "/" + stats.hardTotal);

        // Update Pie Chart
        updatePieChart(stats);

        // Update Weekly Activity Bar Chart
        updateWeeklyBarChart(stats.weeklyActivity);

        // Weakest Topics
        updateWeakTopics(stats.weakestTopics);

        // Recent Problems
        if (stats.recentProblems == null || stats.recentProblems.isEmpty()) {
            binding.tvRecentEmpty.setVisibility(View.VISIBLE);
            binding.rvRecentProblems.setVisibility(View.GONE);
        } else {
            binding.tvRecentEmpty.setVisibility(View.GONE);
            binding.rvRecentProblems.setVisibility(View.VISIBLE);
            recentProblemsAdapter.submitList(stats.recentProblems);
        }
    }

    private void updatePieChart(DashboardStats stats) {
        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        if (stats.easySolved > 0) {
            entries.add(new PieEntry(stats.easySolved, "Easy"));
            colors.add(ContextCompat.getColor(requireContext(), R.color.easy_green));
        }
        if (stats.mediumSolved > 0) {
            entries.add(new PieEntry(stats.mediumSolved, "Medium"));
            colors.add(ContextCompat.getColor(requireContext(), R.color.medium_orange));
        }
        if (stats.hardSolved > 0) {
            entries.add(new PieEntry(stats.hardSolved, "Hard"));
            colors.add(ContextCompat.getColor(requireContext(), R.color.hard_red));
        }

        if (entries.isEmpty()) {
            entries.add(new PieEntry(1f, "No Solved Problems"));
            colors.add(ContextCompat.getColor(requireContext(), R.color.dark_surface));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(11f);

        PieData data = new PieData(dataSet);
        binding.chartDifficultyPie.setData(data);
        binding.chartDifficultyPie.invalidate();
    }

    private void updateWeeklyBarChart(List<DayActivity> activityList) {
        if (activityList == null || activityList.isEmpty()) {
            binding.chartWeeklyBar.clear();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < activityList.size(); i++) {
            DayActivity day = activityList.get(i);
            entries.add(new BarEntry(i, day.getAttemptCount()));
            labels.add(day.getDayName());
        }

        BarDataSet dataSet = new BarDataSet(entries, "Attempts");
        dataSet.setColor(ContextCompat.getColor(requireContext(), R.color.primary));
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f);

        binding.chartWeeklyBar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        binding.chartWeeklyBar.setData(barData);
        binding.chartWeeklyBar.invalidate();
    }

    private void updateWeakTopics(List<WeakTopicSummary> weakTopics) {
        binding.layoutWeakTopicsList.removeAllViews();

        if (weakTopics == null || weakTopics.isEmpty()) {
            binding.tvWeakTopicsEmpty.setVisibility(View.VISIBLE);
            binding.tvWeakTopicsEmpty.setText("No critical weak areas identified (requires at least 5 attempts per topic to diagnose).");
            binding.layoutWeakTopicsList.setVisibility(View.GONE);
            return;
        }

        binding.tvWeakTopicsEmpty.setVisibility(View.GONE);
        binding.layoutWeakTopicsList.setVisibility(View.VISIBLE);

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        int maxItems = Math.min(2, weakTopics.size());
        for (int i = 0; i < maxItems; i++) {
            WeakTopicSummary topic = weakTopics.get(i);
            View itemView = inflater.inflate(R.layout.item_topic_diagnostic, binding.layoutWeakTopicsList, false);

            TextView tvName = itemView.findViewById(R.id.tv_diag_topic_name);
            TextView tvRisk = itemView.findViewById(R.id.tv_diag_risk);
            TextView tvAcceptance = itemView.findViewById(R.id.tv_diag_acceptance);
            TextView tvSolvedTotal = itemView.findViewById(R.id.tv_diag_solved_total);
            TextView tvAttempts = itemView.findViewById(R.id.tv_diag_attempts);
            TextView tvDominant = itemView.findViewById(R.id.tv_diag_dominant_mistake);

            tvName.setText(topic.getTopicName());
            tvRisk.setText("Weak Topic");
            tvRisk.setTextColor(ContextCompat.getColor(requireContext(), R.color.hard_red));
            tvRisk.setBackgroundResource(R.drawable.bg_badge_red);

            int failPct = (int) Math.round(topic.getFailureRate());
            tvAcceptance.setText(Math.max(0, 100 - failPct) + "%");
            tvSolvedTotal.setText((topic.getTotalAttempts() - topic.getFailureCount()) + " solved");
            tvAttempts.setText(String.valueOf(topic.getTotalAttempts()));

            if (topic.getDominantMistake() != null && !topic.getDominantMistake().isEmpty()) {
                tvDominant.setText("Dominant failure: " + topic.getDominantMistake());
            } else {
                tvDominant.setText("High failure frequency detected");
            }

            binding.layoutWeakTopicsList.addView(itemView);
        }
    }

    private void setupProfileSelector() {
        CodeInsightApp app = (CodeInsightApp) requireActivity().getApplication();
        app.getDatabase().platformConnectionDao().getActiveConnectionLive("LeetCode")
            .observe(getViewLifecycleOwner(), conn -> {
                if (conn != null && conn.getUsername() != null && !conn.getUsername().isEmpty()) {
                    binding.tvActiveProfileTitle.setText("@" + conn.getUsername());
                    String sub = (conn.getTotalSolved() > 0 ? (conn.getTotalSolved() + " Solved • ") : "") + "Tap to switch profile";
                    binding.tvActiveProfileSubtitle.setText(sub);
                    binding.btnSwitchProfile.setText("Switch");
                } else {
                    binding.tvActiveProfileTitle.setText("No LeetCode profile selected");
                    binding.tvActiveProfileSubtitle.setText("Tap to link a LeetCode account");
                    binding.btnSwitchProfile.setText("Connect");
                }
            });

        View.OnClickListener profileClickListener = v -> showProfileSelectorDialog();
        binding.cardActiveProfile.setOnClickListener(profileClickListener);
        binding.btnSwitchProfile.setOnClickListener(profileClickListener);
    }

    private void showProfileSelectorDialog() {
        CodeInsightApp app = (CodeInsightApp) requireActivity().getApplication();
        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            List<PlatformConnection> connections = app.getDatabase().platformConnectionDao().getConnectionsByPlatformSync("LeetCode");
            requireActivity().runOnUiThread(() -> {
                if (connections == null || connections.isEmpty()) {
                    Intent intent = new Intent(requireContext(), LeetCodeSyncActivity.class);
                    startActivity(intent);
                    return;
                }

                String[] items = new String[connections.size() + 1];
                int selectedIndex = -1;
                for (int i = 0; i < connections.size(); i++) {
                    PlatformConnection c = connections.get(i);
                    items[i] = "@" + c.getUsername() + (c.isActive() ? " (Active)" : "") +
                        (c.getTotalSolved() > 0 ? (" • " + c.getTotalSolved() + " Solved") : "");
                    if (c.isActive()) {
                        selectedIndex = i;
                    }
                }
                items[connections.size()] = "+ Connect Another LeetCode Profile";

                new AlertDialog.Builder(requireContext())
                    .setTitle("Select LeetCode Profile")
                    .setSingleChoiceItems(items, selectedIndex, (dialog, which) -> {
                        dialog.dismiss();
                        if (which == connections.size()) {
                            Intent intent = new Intent(requireContext(), LeetCodeSyncActivity.class);
                            startActivity(intent);
                        } else {
                            PlatformConnection selected = connections.get(which);
                            switchActiveProfile(selected.getUsername());
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            });
        });
    }

    private void switchActiveProfile(String username) {
        CodeInsightApp app = (CodeInsightApp) requireActivity().getApplication();
        CodeInsightDatabase.databaseWriteExecutor.execute(() -> {
            app.getDatabase().platformConnectionDao().deactivateAll("LeetCode");
            app.getDatabase().platformConnectionDao().setActiveProfile("LeetCode", username);
            PlatformConnection conn = app.getDatabase().platformConnectionDao().getConnection("LeetCode", username);
            if (conn != null && app.getPreferencesManager() != null) {
                app.getPreferencesManager().setActiveLeetCodeUsername(conn.getUsername());
                app.getPreferencesManager().saveLeetCodeStats(
                    conn.getTotalSolved(),
                    conn.getEasySolved(),
                    conn.getMediumSolved(),
                    conn.getHardSolved(),
                    conn.getRanking()
                );
            }
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Active profile switched to @" + username, Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


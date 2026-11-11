package com.codeinsight.app.ui.analytics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codeinsight.app.R;
import com.codeinsight.app.databinding.FragmentAnalyticsBinding;
import com.codeinsight.app.domain.analysis.AnalyticsData;
import com.codeinsight.app.domain.analysis.DatePoint;
import com.codeinsight.app.domain.analysis.TopicPerformance;
import com.codeinsight.app.domain.model.Difficulty;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private AnalyticsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AnalyticsViewModel.class);

        setupChartBasics();

        viewModel.getAnalyticsData().observe(getViewLifecycleOwner(), this::renderAnalytics);
    }

    private void setupChartBasics() {
        int textColor = ContextCompat.getColor(requireContext(), R.color.dark_on_surface_variant);
        int gridColor = ContextCompat.getColor(requireContext(), R.color.dark_surface);

        // Solved Bar Chart
        BarChart bar = binding.chartSolvedBar;
        bar.getDescription().setEnabled(false);
        bar.setDrawGridBackground(false);
        bar.getLegend().setEnabled(false);
        bar.getAxisRight().setEnabled(false);
        XAxis barX = bar.getXAxis();
        barX.setPosition(XAxis.XAxisPosition.BOTTOM);
        barX.setDrawGridLines(false);
        barX.setTextColor(textColor);
        barX.setGranularity(1f);
        barX.setLabelRotationAngle(-20f);

        bar.getAxisLeft().setTextColor(textColor);
        bar.getAxisLeft().setGridColor(gridColor);
        bar.getAxisLeft().setAxisMinimum(0f);

        // Acceptance Line Chart
        LineChart line = binding.chartAcceptanceLine;
        line.getDescription().setEnabled(false);
        line.setDrawGridBackground(false);
        line.getLegend().setEnabled(false);
        line.getAxisRight().setEnabled(false);

        XAxis lineX = line.getXAxis();
        lineX.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineX.setDrawGridLines(false);
        lineX.setTextColor(textColor);
        lineX.setGranularity(1f);
        lineX.setLabelRotationAngle(-20f);

        line.getAxisLeft().setTextColor(textColor);
        line.getAxisLeft().setGridColor(gridColor);
        line.getAxisLeft().setAxisMinimum(0f);
        line.getAxisLeft().setAxisMaximum(100f);

        // Difficulty Pie Chart
        PieChart pie = binding.chartAnalyticsDifficultyPie;
        pie.getDescription().setEnabled(false);
        pie.setDrawHoleEnabled(true);
        pie.setHoleColor(Color.TRANSPARENT);
        pie.setHoleRadius(42f);
        pie.setTransparentCircleRadius(48f);
        pie.setEntryLabelColor(Color.WHITE);
        pie.setEntryLabelTextSize(11f);
        pie.getLegend().setEnabled(false);

        // Topic Horizontal Bar Chart
        HorizontalBarChart topicBar = binding.chartTopicHorizontalBar;
        topicBar.getDescription().setEnabled(false);
        topicBar.getLegend().setEnabled(false);
        topicBar.getAxisRight().setEnabled(false);
        topicBar.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        topicBar.getXAxis().setDrawGridLines(false);
        topicBar.getXAxis().setTextColor(textColor);
        topicBar.getAxisLeft().setTextColor(textColor);
        topicBar.getAxisLeft().setGridColor(gridColor);
        topicBar.getAxisLeft().setAxisMinimum(0f);

        // Mistake Horizontal Bar Chart
        HorizontalBarChart mistakeBar = binding.chartMistakeHorizontalBar;
        mistakeBar.getDescription().setEnabled(false);
        mistakeBar.getLegend().setEnabled(false);
        mistakeBar.getAxisRight().setEnabled(false);
        mistakeBar.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mistakeBar.getXAxis().setDrawGridLines(false);
        mistakeBar.getXAxis().setTextColor(textColor);
        mistakeBar.getAxisLeft().setTextColor(textColor);
        mistakeBar.getAxisLeft().setGridColor(gridColor);
        mistakeBar.getAxisLeft().setAxisMinimum(0f);
    }

    private void renderAnalytics(AnalyticsData data) {
        if (data == null || getContext() == null) return;

        renderSolvedOverTime(data.problemsSolvedOverTime);
        renderAcceptanceTrend(data.acceptanceRateTrends);
        renderDifficultyPie(data.difficultyDistribution);
        renderTopicPerformance(data.topicPerformance);
        renderMistakeDistribution(data.mistakeCategoryDistribution);
    }

    private void renderSolvedOverTime(List<DatePoint> points) {
        if (points == null || points.isEmpty()) {
            binding.chartSolvedBar.clear();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            DatePoint pt = points.get(i);
            entries.add(new BarEntry(i, pt.getValue()));
            labels.add(pt.getLabel());
        }

        BarDataSet set = new BarDataSet(entries, "Solved");
        set.setColor(ContextCompat.getColor(requireContext(), R.color.primary));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        BarData barData = new BarData(set);
        barData.setBarWidth(0.5f);

        binding.chartSolvedBar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        binding.chartSolvedBar.setData(barData);
        binding.chartSolvedBar.invalidate();
    }

    private void renderAcceptanceTrend(List<DatePoint> points) {
        if (points == null || points.isEmpty()) {
            binding.chartAcceptanceLine.clear();
            return;
        }

        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            DatePoint pt = points.get(i);
            entries.add(new Entry(i, pt.getValue()));
            labels.add(pt.getLabel());
        }

        LineDataSet set = new LineDataSet(entries, "Acceptance %");
        set.setColor(ContextCompat.getColor(requireContext(), R.color.primary));
        set.setCircleColor(ContextCompat.getColor(requireContext(), R.color.primary));
        set.setLineWidth(2.5f);
        set.setCircleRadius(4f);
        set.setDrawCircleHole(false);
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        if (entries.size() > 1) {
            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            set.setMode(LineDataSet.Mode.LINEAR);
        }

        LineData lineData = new LineData(set);
        binding.chartAcceptanceLine.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        binding.chartAcceptanceLine.setData(lineData);
        binding.chartAcceptanceLine.invalidate();
    }

    private void renderDifficultyPie(Map<Difficulty, Integer> diffMap) {
        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        int easy = diffMap != null && diffMap.containsKey(Difficulty.EASY) ? diffMap.get(Difficulty.EASY) : 0;
        int medium = diffMap != null && diffMap.containsKey(Difficulty.MEDIUM) ? diffMap.get(Difficulty.MEDIUM) : 0;
        int hard = diffMap != null && diffMap.containsKey(Difficulty.HARD) ? diffMap.get(Difficulty.HARD) : 0;

        if (easy > 0) {
            entries.add(new PieEntry(easy, "Easy"));
            colors.add(ContextCompat.getColor(requireContext(), R.color.easy_green));
        }
        if (medium > 0) {
            entries.add(new PieEntry(medium, "Medium"));
            colors.add(ContextCompat.getColor(requireContext(), R.color.medium_orange));
        }
        if (hard > 0) {
            entries.add(new PieEntry(hard, "Hard"));
            colors.add(ContextCompat.getColor(requireContext(), R.color.hard_red));
        }

        if (entries.isEmpty()) {
            entries.add(new PieEntry(1f, "No Data"));
            colors.add(ContextCompat.getColor(requireContext(), R.color.dark_surface));
        }

        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(colors);
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(11f);

        binding.chartAnalyticsDifficultyPie.setData(new PieData(set));
        binding.chartAnalyticsDifficultyPie.invalidate();
    }

    private void renderTopicPerformance(List<TopicPerformance> topicList) {
        if (topicList == null || topicList.isEmpty()) {
            binding.chartTopicHorizontalBar.clear();
            return;
        }

        List<TopicPerformance> activeTopics = new ArrayList<>();
        for (TopicPerformance tp : topicList) {
            if (tp.getSolvedCount() > 0) {
                activeTopics.add(tp);
            }
        }

        if (activeTopics.isEmpty()) {
            binding.chartTopicHorizontalBar.clear();
            return;
        }

        // Sort descending by problems solved
        java.util.Collections.sort(activeTopics, (a, b) -> Integer.compare(b.getSolvedCount(), a.getSolvedCount()));

        int limit = Math.min(8, activeTopics.size());
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        // Add in reverse order so highest solved topic appears at the top of horizontal chart
        for (int i = 0; i < limit; i++) {
            TopicPerformance tp = activeTopics.get(limit - 1 - i);
            entries.add(new BarEntry(i, tp.getSolvedCount()));
            labels.add(tp.getTopicName());
        }

        BarDataSet set = new BarDataSet(entries, "Solved Count");
        set.setColor(ContextCompat.getColor(requireContext(), R.color.primary));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        BarData barData = new BarData(set);
        barData.setBarWidth(0.5f);

        binding.chartTopicHorizontalBar.getXAxis().setGranularity(1f);
        binding.chartTopicHorizontalBar.getXAxis().setLabelCount(labels.size());
        binding.chartTopicHorizontalBar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        binding.chartTopicHorizontalBar.setData(barData);
        binding.chartTopicHorizontalBar.invalidate();
    }

    private void renderMistakeDistribution(Map<String, Integer> mistakeMap) {
        if (mistakeMap == null || mistakeMap.isEmpty()) {
            binding.chartMistakeHorizontalBar.clear();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;

        for (Map.Entry<String, Integer> entry : mistakeMap.entrySet()) {
            entries.add(new BarEntry(index, entry.getValue()));
            labels.add(entry.getKey());
            index++;
        }

        BarDataSet set = new BarDataSet(entries, "Occurrences");
        set.setColor(ContextCompat.getColor(requireContext(), R.color.hard_red));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        BarData barData = new BarData(set);
        barData.setBarWidth(0.5f);

        binding.chartMistakeHorizontalBar.getXAxis().setGranularity(1f);
        binding.chartMistakeHorizontalBar.getXAxis().setLabelCount(labels.size());
        binding.chartMistakeHorizontalBar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        binding.chartMistakeHorizontalBar.setData(barData);
        binding.chartMistakeHorizontalBar.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


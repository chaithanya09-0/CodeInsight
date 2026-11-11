package com.codeinsight.app.ui.recommendations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codeinsight.app.databinding.FragmentRecommendationsBinding;
import com.codeinsight.app.domain.analysis.GeneratedRecommendation;
import com.codeinsight.app.domain.analysis.TopicDiagnostic;
import com.codeinsight.app.ui.adapter.RecommendationAdapter;
import com.codeinsight.app.ui.adapter.TopicDiagnosticAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class RecommendationsFragment extends Fragment {

    private FragmentRecommendationsBinding binding;
    private RecommendationsViewModel viewModel;
    private RecommendationAdapter recommendationAdapter;
    private TopicDiagnosticAdapter diagnosticAdapter;
    private int currentTab = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecommendationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RecommendationsViewModel.class);

        setupRecyclerViews();
        setupTabs();

        viewModel.getRecommendations().observe(getViewLifecycleOwner(), this::renderRecommendations);
        viewModel.getDiagnostics().observe(getViewLifecycleOwner(), this::renderDiagnostics);
    }

    private void setupRecyclerViews() {
        recommendationAdapter = new RecommendationAdapter(rec -> viewModel.dismissRecommendation(rec));
        binding.rvRecommendations.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvRecommendations.setAdapter(recommendationAdapter);

        diagnosticAdapter = new TopicDiagnosticAdapter();
        binding.rvDiagnostics.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvDiagnostics.setAdapter(diagnosticAdapter);
    }

    private void setupTabs() {
        binding.tabLayoutInsights.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                updateTabVisibility();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void updateTabVisibility() {
        if (currentTab == 0) {
            binding.rvDiagnostics.setVisibility(View.GONE);

            List<GeneratedRecommendation> recs = viewModel.getRecommendations().getValue();
            boolean empty = recs == null || recs.isEmpty();
            binding.rvRecommendations.setVisibility(empty ? View.GONE : View.VISIBLE);
            binding.tvEmptyRecommendations.setVisibility(empty ? View.VISIBLE : View.GONE);
            if (empty) {
                binding.tvEmptyRecommendations.setText("Log at least 3 problems with attempts to generate personalized, rule-based practice recommendations.");
            }
        } else {
            binding.rvRecommendations.setVisibility(View.GONE);

            List<TopicDiagnostic> diags = viewModel.getDiagnostics().getValue();
            boolean empty = diags == null || diags.isEmpty();
            binding.rvDiagnostics.setVisibility(empty ? View.GONE : View.VISIBLE);
            binding.tvEmptyRecommendations.setVisibility(empty ? View.VISIBLE : View.GONE);
            if (empty) {
                binding.tvEmptyRecommendations.setText("No topic diagnostic data available yet. Track problems across different programming topics to see detailed audits.");
            }
        }
    }

    private void renderRecommendations(List<GeneratedRecommendation> recommendations) {
        recommendationAdapter.submitList(recommendations);
        if (currentTab == 0) {
            updateTabVisibility();
        }
    }

    private void renderDiagnostics(List<TopicDiagnostic> diagnostics) {
        diagnosticAdapter.submitList(diagnostics);
        if (currentTab == 1) {
            updateTabVisibility();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


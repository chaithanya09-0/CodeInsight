package com.codeinsight.app.ui.problems;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codeinsight.app.R;
import com.codeinsight.app.databinding.FragmentProblemsBinding;
import com.codeinsight.app.domain.model.Difficulty;
import com.codeinsight.app.domain.model.Platform;
import com.codeinsight.app.domain.model.ProblemStatus;
import com.codeinsight.app.ui.adapter.ProblemAdapter;
import com.codeinsight.app.ui.leetcode.LeetCodeSyncActivity;
import com.google.android.material.chip.Chip;

public class ProblemsFragment extends Fragment {

    private FragmentProblemsBinding binding;
    private ProblemsViewModel viewModel;
    private ProblemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProblemsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProblemsViewModel.class);

        setupRecyclerView();
        setupSearch();
        setupFilterChips();
        setupSortButton();
        setupAutoSyncButton();
        setupFab();

        viewModel.getFilteredProblems().observe(getViewLifecycleOwner(), problems -> {
            if (problems != null) {
                adapter.submitList(problems);
                int count = problems.size();
                binding.tvProblemCount.setText(count == 1 ? "1 tracked problem" : count + " tracked problems");

                if (problems.isEmpty()) {
                    binding.tvEmptyProblems.setVisibility(View.VISIBLE);
                    binding.rvProblems.setVisibility(View.GONE);
                } else {
                    binding.tvEmptyProblems.setVisibility(View.GONE);
                    binding.rvProblems.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new ProblemAdapter(problem -> {
            Intent intent = new Intent(requireContext(), ProblemDetailActivity.class);
            intent.putExtra(ProblemDetailActivity.EXTRA_PROBLEM_ID, problem.problem.getId());
            startActivity(intent);
        });
        binding.rvProblems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvProblems.setAdapter(adapter);
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s != null ? s.toString() : "");
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupFilterChips() {
        // Platform Chips
        binding.chipLeetcode.setOnCheckedChangeListener((btn, isChecked) ->
                viewModel.setFilterPlatform(isChecked ? Platform.LEETCODE : null));
        binding.chipCodechef.setOnCheckedChangeListener((btn, isChecked) ->
                viewModel.setFilterPlatform(isChecked ? Platform.CODECHEF : null));
        binding.chipHackerrank.setOnCheckedChangeListener((btn, isChecked) ->
                viewModel.setFilterPlatform(isChecked ? Platform.HACKERRANK : null));
        binding.chipCodeforces.setOnCheckedChangeListener((btn, isChecked) ->
                viewModel.setFilterPlatform(isChecked ? Platform.CODEFORCES : null));

        // Difficulty Chips
        binding.chipEasy.setOnCheckedChangeListener((btn, isChecked) ->
                viewModel.setFilterDifficulty(isChecked ? Difficulty.EASY : null));
        binding.chipMedium.setOnCheckedChangeListener((btn, isChecked) ->
                viewModel.setFilterDifficulty(isChecked ? Difficulty.MEDIUM : null));
        binding.chipHard.setOnCheckedChangeListener((btn, isChecked) ->
                viewModel.setFilterDifficulty(isChecked ? Difficulty.HARD : null));

        // Status Chips
        binding.chipSolved.setOnCheckedChangeListener((btn, isChecked) ->
                viewModel.setFilterStatus(isChecked ? ProblemStatus.SOLVED : null));
        binding.chipAttempted.setOnCheckedChangeListener((btn, isChecked) ->
                viewModel.setFilterStatus(isChecked ? ProblemStatus.ATTEMPTED : null));
    }

    private void setupSortButton() {
        binding.btnSort.setOnClickListener(v -> {
            String[] sortOptions = new String[]{
                    "Newest Updated",
                    "Oldest Created",
                    "Title (A-Z)",
                    "Difficulty (Easy to Hard)",
                    "Most Attempts"
            };

            new AlertDialog.Builder(requireContext())
                    .setTitle("Sort Problems")
                    .setSingleChoiceItems(sortOptions, viewModel.getSortOrder(), (dialog, which) -> {
                        viewModel.setSortOrder(which);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void setupAutoSyncButton() {
        binding.btnAutoSync.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LeetCodeSyncActivity.class);
            startActivity(intent);
        });
    }

    private void setupFab() {
        binding.fabAddProblem.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddEditProblemActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


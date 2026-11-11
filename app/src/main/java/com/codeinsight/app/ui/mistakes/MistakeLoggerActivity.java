package com.codeinsight.app.ui.mistakes;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codeinsight.app.R;
import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.databinding.ActivityMistakeLoggerBinding;
import com.codeinsight.app.domain.model.MistakeCategory;
import com.codeinsight.app.ui.adapter.MistakeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MistakeLoggerActivity extends AppCompatActivity {

    public static final String EXTRA_PROBLEM_ID = "extra_problem_id";
    public static final String EXTRA_ATTEMPT_ID = "extra_attempt_id";

    private ActivityMistakeLoggerBinding binding;
    private MistakeViewModel viewModel;
    private MistakeAdapter mistakeAdapter;
    private long problemId = -1;
    private long targetAttemptId = -1;
    private final List<CodingAttempt> attemptList = new ArrayList<>();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMistakeLoggerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        problemId = getIntent().getLongExtra(EXTRA_PROBLEM_ID, -1);
        targetAttemptId = getIntent().getLongExtra(EXTRA_ATTEMPT_ID, -1);

        if (problemId <= 0) {
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(MistakeViewModel.class);

        setupToolbar();
        setupRecyclerView();
        setupSaveButton();

        viewModel.getProblemWithDetails(problemId).observe(this, this::renderProblemInfo);
        viewModel.getMistakesForProblem(problemId).observe(this, this::renderMistakes);
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        mistakeAdapter = new MistakeAdapter(this::confirmDeleteMistake);
        binding.rvLoggedMistakes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvLoggedMistakes.setAdapter(mistakeAdapter);
    }

    private void renderProblemInfo(ProblemWithDetails pwd) {
        if (pwd == null) return;

        binding.tvMistakeProblemTitle.setText(pwd.problem.getTitle());
        binding.tvMistakeProblemMeta.setText(pwd.problem.getPlatform()
                + " • " + pwd.problem.getDifficulty()
                + " • " + pwd.attempts.size() + " attempts");

        attemptList.clear();
        attemptList.addAll(pwd.attempts);

        List<String> spinnerLabels = new ArrayList<>();
        int selectedIndex = 0;

        for (int i = 0; i < attemptList.size(); i++) {
            CodingAttempt att = attemptList.get(i);
            String label = "Attempt #" + (i + 1) + " — "
                    + (att.isAccepted() ? "Accepted" : "Failed")
                    + " (" + DATE_FORMAT.format(new Date(att.getAttemptedAt())) + ")";
            spinnerLabels.add(label);

            if (att.getId() == targetAttemptId) {
                selectedIndex = i;
            }
        }

        if (spinnerLabels.isEmpty()) {
            spinnerLabels.add("Default Attempt (Auto-created)");
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerLabels
        );
        binding.spinnerAttempts.setAdapter(spinnerAdapter);
        if (selectedIndex < spinnerLabels.size()) {
            binding.spinnerAttempts.setSelection(selectedIndex);
        }
    }

    private void renderMistakes(List<Mistake> mistakes) {
        if (mistakes == null || mistakes.isEmpty()) {
            binding.tvNoMistakes.setVisibility(View.VISIBLE);
            binding.rvLoggedMistakes.setVisibility(View.GONE);
        } else {
            binding.tvNoMistakes.setVisibility(View.GONE);
            binding.rvLoggedMistakes.setVisibility(View.VISIBLE);
            mistakeAdapter.submitList(mistakes);
        }
    }

    private void setupSaveButton() {
        binding.btnSaveMistake.setOnClickListener(v -> {
            List<MistakeCategory> selectedCategories = new ArrayList<>();

            if (binding.chipLogicalError.isChecked()) selectedCategories.add(MistakeCategory.LOGICAL_ERROR);
            if (binding.chipSyntaxError.isChecked()) selectedCategories.add(MistakeCategory.SYNTAX_ERROR);
            if (binding.chipEdgeCase.isChecked()) selectedCategories.add(MistakeCategory.EDGE_CASE_MISSED);
            if (binding.chipTle.isChecked()) selectedCategories.add(MistakeCategory.TIME_LIMIT_EXCEEDED);
            if (binding.chipSpaceComplexity.isChecked()) selectedCategories.add(MistakeCategory.SPACE_COMPLEXITY);
            if (binding.chipIncorrectAlgo.isChecked()) selectedCategories.add(MistakeCategory.INCORRECT_ALGORITHM);
            if (binding.chipOffByOne.isChecked()) selectedCategories.add(MistakeCategory.OFF_BY_ONE);
            if (binding.chipRecursionError.isChecked()) selectedCategories.add(MistakeCategory.RECURSION_ERROR);
            if (binding.chipOther.isChecked()) selectedCategories.add(MistakeCategory.OTHER);

            if (selectedCategories.isEmpty()) {
                Toast.makeText(this, "Please select at least one mistake category", Toast.LENGTH_SHORT).show();
                return;
            }

            long selectedAttemptId = -1;
            int spinnerPos = binding.spinnerAttempts.getSelectedItemPosition();
            if (spinnerPos >= 0 && spinnerPos < attemptList.size()) {
                selectedAttemptId = attemptList.get(spinnerPos).getId();
            }

            String desc = binding.etMistakeDescription.getText() != null ? binding.etMistakeDescription.getText().toString().trim() : "";
            String notes = binding.etMistakeNotes.getText() != null ? binding.etMistakeNotes.getText().toString().trim() : "";

            binding.btnSaveMistake.setEnabled(false);

            viewModel.logMistakes(
                    problemId,
                    selectedAttemptId,
                    selectedCategories,
                    desc,
                    notes,
                    new ProblemRepository.RepositoryCallback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            runOnUiThread(() -> {
                                binding.btnSaveMistake.setEnabled(true);
                                binding.chipGroupMistakeCategories.clearCheck();
                                binding.etMistakeDescription.setText("");
                                binding.etMistakeNotes.setText("");
                                Toast.makeText(MistakeLoggerActivity.this, "Mistake recorded successfully!", Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            runOnUiThread(() -> {
                                binding.btnSaveMistake.setEnabled(true);
                                Toast.makeText(MistakeLoggerActivity.this, "Failed to log mistake: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
            );
        });
    }

    private void confirmDeleteMistake(Mistake mistake) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Mistake")
                .setMessage("Delete recorded mistake '" + mistake.getCategory() + "'?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteMistake(mistake, new ProblemRepository.RepositoryCallback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            runOnUiThread(() -> Toast.makeText(MistakeLoggerActivity.this, "Mistake deleted", Toast.LENGTH_SHORT).show());
                        }

                        @Override
                        public void onError(Exception e) {
                            runOnUiThread(() -> Toast.makeText(MistakeLoggerActivity.this, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

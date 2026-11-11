package com.codeinsight.app.ui.problems;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.codeinsight.app.R;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.local.entity.Topic;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.databinding.ActivityAddEditProblemBinding;
import com.codeinsight.app.domain.model.Difficulty;
import com.codeinsight.app.domain.model.Platform;
import com.google.android.material.chip.Chip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddEditProblemActivity extends AppCompatActivity {

    public static final String EXTRA_PROBLEM_ID = "extra_problem_id";

    private ActivityAddEditProblemBinding binding;
    private AddEditProblemViewModel viewModel;
    private long problemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditProblemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AddEditProblemViewModel.class);

        problemId = getIntent().getLongExtra(EXTRA_PROBLEM_ID, -1);

        setupToolbar();
        setupSwitchListener();
        setupTopicsObserver();
        setupAddCustomTopicButton();
        setupSaveButton();

        if (problemId > 0) {
            binding.toolbar.setTitle("Edit Problem");
            binding.cardInitialAttempt.setVisibility(View.GONE);
            viewModel.loadProblem(problemId);
            viewModel.getProblemToEdit().observe(this, this::populateExistingData);
        }
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupSwitchListener() {
        binding.switchInitialAttempt.setOnCheckedChangeListener((btn, isChecked) -> {
            binding.layoutAttemptDetails.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
    }

    private void setupTopicsObserver() {
        viewModel.getAllTopics().observe(this, this::renderTopicChips);
    }

    private void renderTopicChips(List<Topic> topics) {
        if (topics == null) return;
        binding.chipGroupTopics.removeAllViews();

        Set<String> seenNames = new HashSet<>();
        for (Topic t : topics) {
            String canonical = com.codeinsight.app.domain.model.TopicNormalizer.normalize(t.getName());
            if (canonical == null) continue; // Filter out platform strings like LeetCode
            if (seenNames.contains(canonical.toLowerCase())) continue; // Prevent duplicates in UI
            seenNames.add(canonical.toLowerCase());

            Chip chip = new Chip(this, null, com.google.android.material.R.style.Widget_Material3_Chip_Filter);
            chip.setText(canonical);
            chip.setCheckable(true);
            chip.setChecked(viewModel.getSelectedTopicIds().contains(t.getId()));

            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                viewModel.toggleTopic(t.getId());
            });

            binding.chipGroupTopics.addView(chip);
        }
    }

    private void setupAddCustomTopicButton() {
        binding.btnAddTopic.setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setHint("Topic name (e.g. Tries, Math)");
            int pad = (int) (16 * getResources().getDisplayMetrics().density);
            input.setPadding(pad, pad, pad, pad);

            new AlertDialog.Builder(this)
                    .setTitle("Add Custom Topic")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) -> {
                        String name = input.getText().toString().trim();
                        if (!TextUtils.isEmpty(name)) {
                            viewModel.createTopic(name, new ProblemRepository.RepositoryCallback<Topic>() {
                                @Override
                                public void onSuccess(Topic result) {
                                    runOnUiThread(() -> {
                                        viewModel.toggleTopic(result.getId());
                                        Toast.makeText(AddEditProblemActivity.this, "Topic added!", Toast.LENGTH_SHORT).show();
                                    });
                                }

                                @Override
                                public void onError(Exception e) {
                                    runOnUiThread(() -> Toast.makeText(AddEditProblemActivity.this, "Failed to create topic: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                                }
                            });
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void populateExistingData(ProblemWithDetails pwd) {
        if (pwd == null) return;

        binding.etTitle.setText(pwd.problem.getTitle());
        binding.etUrl.setText(pwd.problem.getUrl());
        binding.etProblemNotes.setText(pwd.problem.getNotes());

        // Platform
        Platform platform = Platform.fromString(pwd.problem.getPlatform());
        if (platform == Platform.LEETCODE) binding.chipPlatLeetcode.setChecked(true);
        else if (platform == Platform.CODECHEF) binding.chipPlatCodechef.setChecked(true);
        else if (platform == Platform.HACKERRANK) binding.chipPlatHackerrank.setChecked(true);
        else if (platform == Platform.CODEFORCES) binding.chipPlatCodeforces.setChecked(true);
        else binding.chipPlatOther.setChecked(true);

        // Difficulty
        Difficulty difficulty = Difficulty.fromString(pwd.problem.getDifficulty());
        if (difficulty == Difficulty.EASY) binding.chipDiffEasy.setChecked(true);
        else if (difficulty == Difficulty.HARD) binding.chipDiffHard.setChecked(true);
        else binding.chipDiffMedium.setChecked(true);

        // Re-render topics to mark selected
        if (viewModel.getAllTopics().getValue() != null) {
            renderTopicChips(viewModel.getAllTopics().getValue());
        }
    }

    private void setupSaveButton() {
        binding.btnSave.setOnClickListener(v -> {
            String title = binding.etTitle.getText() != null ? binding.etTitle.getText().toString().trim() : "";
            if (TextUtils.isEmpty(title)) {
                binding.tilTitle.setError("Title is required");
                return;
            }
            binding.tilTitle.setError(null);

            String url = binding.etUrl.getText() != null ? binding.etUrl.getText().toString().trim() : "";
            String notes = binding.etProblemNotes.getText() != null ? binding.etProblemNotes.getText().toString().trim() : "";

            // Selected Platform
            String platform = "LeetCode";
            int platChipId = binding.chipGroupPlatform.getCheckedChipId();
            if (platChipId == R.id.chip_plat_codechef) platform = "CodeChef";
            else if (platChipId == R.id.chip_plat_hackerrank) platform = "HackerRank";
            else if (platChipId == R.id.chip_plat_codeforces) platform = "Codeforces";
            else if (platChipId == R.id.chip_plat_other) platform = "Other";

            // Selected Difficulty
            String difficulty = "Medium";
            int diffChipId = binding.chipGroupDifficulty.getCheckedChipId();
            if (diffChipId == R.id.chip_diff_easy) difficulty = "Easy";
            else if (diffChipId == R.id.chip_diff_hard) difficulty = "Hard";

            // Initial Attempt info
            boolean logAttempt = binding.switchInitialAttempt.isChecked();
            boolean accepted = binding.rbAccepted.isChecked();
            String durStr = binding.etAttemptDuration.getText() != null ? binding.etAttemptDuration.getText().toString().trim() : "";
            Integer duration = null;
            if (!TextUtils.isEmpty(durStr)) {
                try {
                    duration = Integer.parseInt(durStr);
                } catch (Exception ignored) {
                    duration = 0;
                }
            }
            String lang = binding.etAttemptLanguage.getText() != null ? binding.etAttemptLanguage.getText().toString().trim() : "Java";
            String attemptNotes = binding.etAttemptNotes.getText() != null ? binding.etAttemptNotes.getText().toString().trim() : "";

            binding.btnSave.setEnabled(false);

            viewModel.saveProblem(
                    problemId,
                    title,
                    url,
                    platform,
                    difficulty,
                    notes,
                    logAttempt,
                    accepted,
                    duration,
                    lang,
                    attemptNotes,
                    new ProblemRepository.RepositoryCallback<Long>() {
                        @Override
                        public void onSuccess(Long result) {
                            runOnUiThread(() -> {
                                Toast.makeText(AddEditProblemActivity.this, "Problem saved successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            runOnUiThread(() -> {
                                binding.btnSave.setEnabled(true);
                                Toast.makeText(AddEditProblemActivity.this, "Failed to save: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
            );
        });
    }
}


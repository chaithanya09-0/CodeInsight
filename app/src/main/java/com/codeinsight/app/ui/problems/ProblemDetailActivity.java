package com.codeinsight.app.ui.problems;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codeinsight.app.R;
import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.local.entity.Topic;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.databinding.ActivityProblemDetailBinding;
import com.codeinsight.app.domain.model.Difficulty;
import com.codeinsight.app.domain.model.ProblemStatus;
import com.codeinsight.app.ui.adapter.AttemptAdapter;
import com.codeinsight.app.ui.mistakes.MistakeLoggerActivity;
import com.google.android.material.chip.Chip;

import java.util.Collections;

public class ProblemDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PROBLEM_ID = "extra_problem_id";

    private ActivityProblemDetailBinding binding;
    private ProblemDetailViewModel viewModel;
    private AttemptAdapter attemptAdapter;
    private long problemId = -1;
    private ProblemWithDetails currentProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProblemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        problemId = getIntent().getLongExtra(EXTRA_PROBLEM_ID, -1);
        if (problemId <= 0) {
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(ProblemDetailViewModel.class);

        setupToolbar();
        setupRecyclerView();
        setupActionButtons();

        viewModel.getProblemWithDetails(problemId).observe(this, this::renderProblem);
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_edit) {
                Intent intent = new Intent(this, AddEditProblemActivity.class);
                intent.putExtra(AddEditProblemActivity.EXTRA_PROBLEM_ID, problemId);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.action_delete) {
                confirmDeleteProblem();
                return true;
            }
            return false;
        });
    }

    private void setupRecyclerView() {
        attemptAdapter = new AttemptAdapter(attempt -> {
            Intent intent = new Intent(this, MistakeLoggerActivity.class);
            intent.putExtra(MistakeLoggerActivity.EXTRA_PROBLEM_ID, problemId);
            intent.putExtra(MistakeLoggerActivity.EXTRA_ATTEMPT_ID, attempt.getId());
            startActivity(intent);
        });
        binding.rvAttempts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAttempts.setAdapter(attemptAdapter);
    }

    private void setupActionButtons() {
        binding.btnLogAttempt.setOnClickListener(v -> showLogAttemptDialog());

        binding.btnLogMistake.setOnClickListener(v -> {
            long attemptId = -1;
            if (currentProblem != null && currentProblem.attempts != null && !currentProblem.attempts.isEmpty()) {
                // Default to most recent attempt
                attemptId = currentProblem.attempts.get(currentProblem.attempts.size() - 1).getId();
            }

            Intent intent = new Intent(this, MistakeLoggerActivity.class);
            intent.putExtra(MistakeLoggerActivity.EXTRA_PROBLEM_ID, problemId);
            intent.putExtra(MistakeLoggerActivity.EXTRA_ATTEMPT_ID, attemptId);
            startActivity(intent);
        });
    }

    private void renderProblem(ProblemWithDetails pwd) {
        if (pwd == null) return;
        currentProblem = pwd;

        binding.tvDetailTitle.setText(pwd.problem.getTitle());
        binding.tvDetailPlatform.setText(pwd.problem.getPlatform() != null ? pwd.problem.getPlatform() : "LeetCode");

        // Difficulty badge
        Difficulty diff = Difficulty.fromString(pwd.problem.getDifficulty());
        binding.tvDetailDifficulty.setText(diff.getDisplayName());
        int diffColor;
        int diffBg;
        switch (diff) {
            case EASY:
                diffColor = ContextCompat.getColor(this, R.color.easy_green);
                diffBg = R.drawable.bg_badge_green;
                break;
            case HARD:
                diffColor = ContextCompat.getColor(this, R.color.hard_red);
                diffBg = R.drawable.bg_badge_red;
                break;
            case MEDIUM:
            default:
                diffColor = ContextCompat.getColor(this, R.color.medium_orange);
                diffBg = R.drawable.bg_badge_orange;
                break;
        }
        binding.tvDetailDifficulty.setTextColor(diffColor);
        binding.tvDetailDifficulty.setBackgroundResource(diffBg);

        // Status badge
        ProblemStatus status = ProblemStatus.fromString(pwd.problem.getStatus());
        binding.tvDetailStatus.setText(status.getDisplayName());
        if (status == ProblemStatus.SOLVED) {
            binding.tvDetailStatus.setTextColor(ContextCompat.getColor(this, R.color.easy_green));
        } else if (status == ProblemStatus.ATTEMPTED) {
            binding.tvDetailStatus.setTextColor(ContextCompat.getColor(this, R.color.medium_orange));
        } else {
            binding.tvDetailStatus.setTextColor(ContextCompat.getColor(this, R.color.dark_on_surface_variant));
        }

        // Attempts count
        int attemptCount = pwd.attempts.size();
        binding.tvDetailAttemptsCount.setText("Total attempts: " + attemptCount);

        // URL link
        if (pwd.problem.getUrl() != null && !pwd.problem.getUrl().trim().isEmpty()) {
            binding.tvDetailUrl.setVisibility(View.VISIBLE);
            binding.tvDetailUrl.setOnClickListener(v -> {
                try {
                    String url = pwd.problem.getUrl().trim();
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "https://" + url;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Toast.makeText(this, "Could not open URL", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            binding.tvDetailUrl.setVisibility(View.GONE);
        }

        // Topics chips
        binding.chipGroupDetailTopics.removeAllViews();
        if (pwd.topics != null) {
            for (Topic t : pwd.topics) {
                Chip chip = new Chip(this);
                chip.setText(t.getName());
                chip.setClickable(false);
                binding.chipGroupDetailTopics.addView(chip);
            }
        }

        // Notes card
        if (pwd.problem.getNotes() != null && !pwd.problem.getNotes().trim().isEmpty()) {
            binding.cardNotes.setVisibility(View.VISIBLE);
            binding.tvDetailNotes.setText(pwd.problem.getNotes().trim());
        } else {
            binding.cardNotes.setVisibility(View.GONE);
        }

        // Attempts list (reverse chronological)
        if (pwd.attempts == null || pwd.attempts.isEmpty()) {
            binding.tvAttemptsEmpty.setVisibility(View.VISIBLE);
            binding.rvAttempts.setVisibility(View.GONE);
        } else {
            binding.tvAttemptsEmpty.setVisibility(View.GONE);
            binding.rvAttempts.setVisibility(View.VISIBLE);
            Collections.sort(pwd.attempts, (a, b) -> Long.compare(b.getAttemptedAt(), a.getAttemptedAt()));
            attemptAdapter.submitList(pwd.attempts);
        }
    }

    private void showLogAttemptDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_attempt, null, false);
        // Build dialog layout
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        int pad = (int) (18 * getResources().getDisplayMetrics().density);
        layout.setPadding(pad, pad, pad, pad);

        RadioGroup rgStatus = new RadioGroup(this);
        rgStatus.setOrientation(RadioGroup.HORIZONTAL);
        android.widget.RadioButton rbAcc = new android.widget.RadioButton(this);
        rbAcc.setText("Accepted");
        rbAcc.setChecked(true);
        android.widget.RadioButton rbFail = new android.widget.RadioButton(this);
        rbFail.setText("Failed");
        rgStatus.addView(rbAcc);
        rgStatus.addView(rbFail);
        layout.addView(rgStatus);

        EditText etDuration = new EditText(this);
        etDuration.setHint("Duration in minutes (e.g. 20)");
        etDuration.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(etDuration);

        EditText etLanguage = new EditText(this);
        etLanguage.setHint("Programming Language (e.g. Java)");
        etLanguage.setText("Java");
        layout.addView(etLanguage);

        EditText etNotes = new EditText(this);
        etNotes.setHint("Notes on this attempt");
        layout.addView(etNotes);

        new AlertDialog.Builder(this)
                .setTitle("Log Problem Attempt")
                .setView(layout)
                .setPositiveButton("Log", (dialog, which) -> {
                    boolean accepted = rbAcc.isChecked();
                    String durText = etDuration.getText().toString().trim();
                    Integer duration = null;
                    if (!TextUtils.isEmpty(durText)) {
                        try {
                            duration = Integer.parseInt(durText);
                        } catch (Exception ignored) {
                            duration = 0;
                        }
                    }
                    String lang = etLanguage.getText().toString().trim();
                    String notes = etNotes.getText().toString().trim();

                    viewModel.logAttempt(problemId, accepted, duration, lang, notes, new ProblemRepository.RepositoryCallback<Long>() {
                        @Override
                        public void onSuccess(Long result) {
                            runOnUiThread(() -> Toast.makeText(ProblemDetailActivity.this, "Attempt recorded!", Toast.LENGTH_SHORT).show());
                        }

                        @Override
                        public void onError(Exception e) {
                            runOnUiThread(() -> Toast.makeText(ProblemDetailActivity.this, "Error logging attempt: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void confirmDeleteProblem() {
        if (currentProblem == null) return;
        new AlertDialog.Builder(this)
                .setTitle("Delete Problem")
                .setMessage("Are you sure you want to delete '" + currentProblem.problem.getTitle() + "' and all its attempts and mistakes? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteProblem(currentProblem.problem, new ProblemRepository.RepositoryCallback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            runOnUiThread(() -> {
                                Toast.makeText(ProblemDetailActivity.this, "Problem deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            runOnUiThread(() -> Toast.makeText(ProblemDetailActivity.this, "Delete failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}


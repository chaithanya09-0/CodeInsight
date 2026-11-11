package com.codeinsight.app.ui.mistakes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codeinsight.app.R;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.MistakeWithProblem;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.repository.ProblemRepository;
import com.codeinsight.app.databinding.FragmentMistakesBinding;
import com.codeinsight.app.ui.adapter.MistakeCardAdapter;
import com.codeinsight.app.ui.problems.ProblemDetailActivity;

import java.util.List;

public class MistakesFragment extends Fragment {

    private FragmentMistakesBinding binding;
    private MistakesViewModel viewModel;
    private MistakeCardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMistakesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MistakesViewModel.class);

        setupRecyclerView();
        setupSearch();
        setupCategoryChips();
        setupTopicFilter();
        setupFab();
        observeViewModel();
    }

    private void setupRecyclerView() {
        adapter = new MistakeCardAdapter(
                item -> {
                    if (item.problem != null) {
                        Intent intent = new Intent(requireContext(), ProblemDetailActivity.class);
                        intent.putExtra(ProblemDetailActivity.EXTRA_PROBLEM_ID, item.problem.getId());
                        startActivity(intent);
                    }
                },
                this::showEditMistakeDialog,
                this::confirmDeleteMistake
        );

        binding.rvMistakes.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMistakes.setAdapter(adapter);
    }

    private void setupTopicFilter() {
        binding.chipFilterTopic.setOnClickListener(v -> {
            java.util.List<String> options = new java.util.ArrayList<>();
            options.add("All Topics");
            options.addAll(com.codeinsight.app.domain.model.TopicNormalizer.PREDEFINED_TOPICS);

            String[] array = options.toArray(new String[0]);
            new AlertDialog.Builder(requireContext())
                    .setTitle("Filter Mistakes by Topic")
                    .setItems(array, (dialog, which) -> {
                        String selected = array[which];
                        binding.chipFilterTopic.setText(selected);
                        if ("All Topics".equals(selected)) {
                            viewModel.setSelectedTopic("ALL");
                        } else {
                            viewModel.setSelectedTopic(selected);
                        }
                    })
                    .show();
        });
    }

    private void setupSearch() {
        binding.etSearchMistakes.addTextChangedListener(new TextWatcher() {
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

    private void setupCategoryChips() {
        binding.chipGroupCategories.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int checkedId = checkedIds.get(0);

            if (checkedId == R.id.chip_cat_all) {
                viewModel.setSelectedCategory("ALL");
            } else if (checkedId == R.id.chip_cat_logical) {
                viewModel.setSelectedCategory("Logical error");
            } else if (checkedId == R.id.chip_cat_algorithm) {
                viewModel.setSelectedCategory("Incorrect algorithm");
            } else if (checkedId == R.id.chip_cat_edge_case) {
                viewModel.setSelectedCategory("Edge case missed");
            } else if (checkedId == R.id.chip_cat_tle) {
                viewModel.setSelectedCategory("Time Limit Exceeded");
            } else if (checkedId == R.id.chip_cat_space) {
                viewModel.setSelectedCategory("Space complexity issue");
            } else if (checkedId == R.id.chip_cat_syntax) {
                viewModel.setSelectedCategory("Syntax error");
            } else if (checkedId == R.id.chip_cat_off_by_one) {
                viewModel.setSelectedCategory("Off-by-one error");
            } else if (checkedId == R.id.chip_cat_recursion) {
                viewModel.setSelectedCategory("Recursion error");
            } else if (checkedId == R.id.chip_cat_other) {
                viewModel.setSelectedCategory("Other");
            }
        });
    }

    private void setupFab() {
        binding.fabRecordMistake.setOnClickListener(v -> showProblemSelectionDialog());
        binding.btnEmptyRecordMistake.setOnClickListener(v -> showProblemSelectionDialog());
    }

    private void showProblemSelectionDialog() {
        List<ProblemWithDetails> problems = viewModel.getAllProblems().getValue();
        if (problems == null || problems.isEmpty()) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("No Problems Found")
                    .setMessage("To record a mistake, you must first add or track a coding problem in the Problems section.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        String[] problemTitles = new String[problems.size()];
        for (int i = 0; i < problems.size(); i++) {
            ProblemWithDetails p = problems.get(i);
            problemTitles[i] = p.problem.getTitle() + " (" + p.problem.getDifficulty() + ")";
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Select Problem to Log Mistake")
                .setItems(problemTitles, (dialog, which) -> {
                    ProblemWithDetails selected = problems.get(which);
                    Intent intent = new Intent(requireContext(), MistakeLoggerActivity.class);
                    intent.putExtra(MistakeLoggerActivity.EXTRA_PROBLEM_ID, selected.problem.getId());
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void confirmDeleteMistake(Mistake mistake) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Mistake")
                .setMessage("Are you sure you want to delete this recorded mistake?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteMistake(mistake, new ProblemRepository.RepositoryCallback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            if (isAdded()) {
                                Toast.makeText(requireContext(), "Mistake deleted", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            if (isAdded()) {
                                Toast.makeText(requireContext(), "Failed to delete mistake", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEditMistakeDialog(MistakeWithProblem item) {
        if (item == null || item.mistake == null) return;
        Mistake m = item.mistake;

        android.widget.LinearLayout layout = new android.widget.LinearLayout(requireContext());
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        int pad = (int) (16 * getResources().getDisplayMetrics().density);
        layout.setPadding(pad, pad, pad, pad);

        android.widget.TextView tvCat = new android.widget.TextView(requireContext());
        tvCat.setText("Category: " + m.getCategory());
        tvCat.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.primary));
        tvCat.setTextSize(14f);
        tvCat.setTypeface(null, android.graphics.Typeface.BOLD);
        layout.addView(tvCat);

        String[] categories = new String[] {
            "Logical error", "Incorrect algorithm", "Edge case missed",
            "Time Limit Exceeded", "Space complexity issue", "Syntax error",
            "Off-by-one error", "Recursion error", "Other"
        };
        final String[] chosenCat = new String[] { m.getCategory() };

        android.widget.Button btnPickCat = new android.widget.Button(requireContext());
        btnPickCat.setText("Change Category");
        btnPickCat.setTextSize(12f);
        btnPickCat.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                .setTitle("Select Mistake Category")
                .setItems(categories, (d, which) -> {
                    chosenCat[0] = categories[which];
                    tvCat.setText("Category: " + chosenCat[0]);
                })
                .show();
        });
        layout.addView(btnPickCat);

        android.widget.TextView tvDescLabel = new android.widget.TextView(requireContext());
        tvDescLabel.setText("Description / Error Manifestation:");
        tvDescLabel.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.dark_on_surface_variant));
        tvDescLabel.setTextSize(12f);
        tvDescLabel.setPadding(0, pad / 2, 0, 4);
        layout.addView(tvDescLabel);

        android.widget.EditText etDesc = new android.widget.EditText(requireContext());
        etDesc.setText(m.getDescription());
        etDesc.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.dark_on_surface));
        etDesc.setHint("What went wrong?");
        layout.addView(etDesc);

        android.widget.TextView tvNotesLabel = new android.widget.TextView(requireContext());
        tvNotesLabel.setText("Personal Reflection & Avoidance Checklist:");
        tvNotesLabel.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.dark_on_surface_variant));
        tvNotesLabel.setTextSize(12f);
        tvNotesLabel.setPadding(0, pad / 2, 0, 4);
        layout.addView(tvNotesLabel);

        android.widget.EditText etNotes = new android.widget.EditText(requireContext());
        etNotes.setText(m.getNotes());
        etNotes.setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.dark_on_surface));
        etNotes.setHint("Rule to remember next time");
        layout.addView(etNotes);

        new AlertDialog.Builder(requireContext())
                .setTitle("Edit Mistake")
                .setView(layout)
                .setPositiveButton("Save Changes", (dialog, which) -> {
                    m.setCategory(chosenCat[0]);
                    m.setDescription(etDesc.getText().toString().trim());
                    m.setNotes(etNotes.getText().toString().trim());
                    viewModel.updateMistake(m, new ProblemRepository.RepositoryCallback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            if (isAdded()) {
                                Toast.makeText(requireContext(), "Mistake updated!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            if (isAdded()) {
                                Toast.makeText(requireContext(), "Failed to update mistake", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void observeViewModel() {
        viewModel.getFilteredMistakes().observe(getViewLifecycleOwner(), mistakes -> {
            if (mistakes != null) {
                adapter.submitList(mistakes);

                if (mistakes.isEmpty()) {
                    binding.rvMistakes.setVisibility(View.GONE);
                    binding.layoutEmptyMistakes.setVisibility(View.VISIBLE);

                    if (!"ALL".equalsIgnoreCase(viewModel.getSelectedCategory())) {
                        binding.tvEmptyTitle.setText("No Matching Mistakes");
                        binding.tvEmptyDesc.setText("No mistakes recorded under the \"" + viewModel.getSelectedCategory() + "\" category.");
                    } else {
                        binding.tvEmptyTitle.setText("No Mistakes Recorded");
                        binding.tvEmptyDesc.setText("Logging failure reasons (Edge case, TLE, Logic error) helps you identify blind spots and avoid repeating them.");
                    }
                } else {
                    binding.rvMistakes.setVisibility(View.VISIBLE);
                    binding.layoutEmptyMistakes.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getTotalMistakesCountLive().observe(getViewLifecycleOwner(), count -> {
            binding.tvTotalMistakesCount.setText(String.valueOf(count != null ? count : 0));
        });

        viewModel.getDominantCategoryLive().observe(getViewLifecycleOwner(), dominant -> {
            binding.tvDominantMistakeCategory.setText(dominant != null ? dominant : "None");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


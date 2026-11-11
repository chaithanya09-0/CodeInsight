package com.codeinsight.app.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codeinsight.app.databinding.FragmentSettingsBinding;
import com.codeinsight.app.ui.leetcode.LeetCodeSyncActivity;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        setupProfileCard();
        setupPlatformNav();
        setupSwitches();
        setupDataManagement();
        observeViewModel();
    }

    private void setupProfileCard() {
        binding.cardUserProfile.setOnClickListener(v -> {
            EditText input = new EditText(requireContext());
            input.setHint("Enter your name");
            String current = viewModel.getUserNameLive().getValue();
            if (current != null) {
                input.setText(current);
            }
            int pad = (int) (16 * getResources().getDisplayMetrics().density);
            input.setPadding(pad, pad, pad, pad);

            new AlertDialog.Builder(requireContext())
                    .setTitle("Developer Name")
                    .setView(input)
                    .setPositiveButton("Save", (dialog, which) -> {
                        String name = input.getText().toString().trim();
                        viewModel.setUserName(name);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void setupPlatformNav() {
        binding.layoutLeetcodeSyncNav.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LeetCodeSyncActivity.class);
            startActivity(intent);
        });
    }

    private void setupSwitches() {
        binding.switchDarkTheme.setOnCheckedChangeListener((btn, isChecked) -> {
            viewModel.setDarkTheme(isChecked);
        });

        binding.switchAutoSync.setOnCheckedChangeListener((btn, isChecked) -> {
            viewModel.setAutoSync(requireContext(), isChecked);
        });
    }

    private void setupDataManagement() {
        binding.layoutSeedDemoData.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Load Sample Practice Data")
                    .setMessage("This will populate sample problems, attempts, mistakes, and topics so you can explore analytics, charts, and recommendations immediately.")
                    .setPositiveButton("Load Data", (dialog, which) -> {
                        Toast.makeText(requireContext(), "Loading sample practice data...", Toast.LENGTH_SHORT).show();
                        viewModel.seedDemoData();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        binding.layoutExportData.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Generating JSON export...", Toast.LENGTH_SHORT).show();
            viewModel.exportDataAsJson();
        });

        binding.layoutClearData.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Clear All Local Data")
                    .setMessage("Are you sure you want to delete all problems, attempts, mistakes, and recommendations? This cannot be undone.")
                    .setPositiveButton("Clear All", (dialog, which) -> {
                        viewModel.clearAllData();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void observeViewModel() {
        viewModel.getUserNameLive().observe(getViewLifecycleOwner(), name -> {
            if (name != null && !name.trim().isEmpty()) {
                binding.tvSettingsUsername.setText(name);
            } else {
                binding.tvSettingsUsername.setText("Set Developer Name");
            }
        });

        viewModel.getDarkThemeLive().observe(getViewLifecycleOwner(), isDark -> {
            if (isDark != null && binding.switchDarkTheme.isChecked() != isDark) {
                binding.switchDarkTheme.setChecked(isDark);
            }
        });

        viewModel.getAutoSyncLive().observe(getViewLifecycleOwner(), autoSync -> {
            if (autoSync != null && binding.switchAutoSync.isChecked() != autoSync) {
                binding.switchAutoSync.setChecked(autoSync);
            }
        });

        viewModel.getStatusMessage().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null && !msg.isEmpty()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getExportJsonLive().observe(getViewLifecycleOwner(), json -> {
            if (json != null && !json.isEmpty()) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, json);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "CodeInsight_Backup.json");
                sendIntent.setType("application/json");
                Intent shareIntent = Intent.createChooser(sendIntent, "Export CodeInsight Data");
                startActivity(shareIntent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


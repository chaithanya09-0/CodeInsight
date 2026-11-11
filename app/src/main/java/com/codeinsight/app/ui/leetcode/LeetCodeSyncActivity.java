package com.codeinsight.app.ui.leetcode;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codeinsight.app.R;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.data.remote.leetcode.LeetCodeProfile;
import com.codeinsight.app.databinding.ActivityLeetcodeSyncBinding;
import com.codeinsight.app.ui.adapter.ConnectedProfilesAdapter;
import com.codeinsight.app.ui.adapter.SyncHistoryAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LeetCodeSyncActivity extends AppCompatActivity {

    private ActivityLeetcodeSyncBinding binding;
    private LeetCodeViewModel viewModel;
    private ConnectedProfilesAdapter profilesAdapter;
    private SyncHistoryAdapter historyAdapter;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeetcodeSyncBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LeetCodeViewModel.class);

        setupToolbar();
        setupRecyclerViews();
        setupButtons();
        observeViewModel();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerViews() {
        profilesAdapter = new ConnectedProfilesAdapter(new ConnectedProfilesAdapter.OnProfileActionListener() {
            @Override
            public void onSetActive(PlatformConnection connection) {
                viewModel.switchActiveProfile(connection.getUsername());
            }

            @Override
            public void onSync(PlatformConnection connection) {
                viewModel.syncUserProblems(connection.getUsername());
            }

            @Override
            public void onDelete(PlatformConnection connection) {
                new androidx.appcompat.app.AlertDialog.Builder(LeetCodeSyncActivity.this)
                    .setTitle("Remove Profile @" + connection.getUsername())
                    .setMessage("Are you sure you want to disconnect @" + connection.getUsername() + "? Your locally recorded problems and mistakes will not be deleted.")
                    .setPositiveButton("Remove", (dialog, which) -> {
                        viewModel.deleteProfile(connection.getUsername());
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            }
        });
        binding.rvConnectedProfiles.setLayoutManager(new LinearLayoutManager(this));
        binding.rvConnectedProfiles.setAdapter(profilesAdapter);

        historyAdapter = new SyncHistoryAdapter();
        binding.rvSyncHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSyncHistory.setAdapter(historyAdapter);
    }

    private void setupButtons() {
        binding.btnCheckProfile.setOnClickListener(v -> {
            String username = getUsername();
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "Please enter your LeetCode username", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.checkProfile(username);
        });

        binding.btnSyncNow.setOnClickListener(v -> {
            String username = getUsername();
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "Please enter your LeetCode username", Toast.LENGTH_SHORT).show();
                return;
            }

            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Link LeetCode Account")
                    .setMessage("Confirm linking '" + username + "' to CodeInsight. Only publicly available profile statistics are fetched to power your personal analytics.")
                    .setPositiveButton("Confirm & Sync", (dialog, which) -> {
                        viewModel.syncUserProblems(username);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private String getUsername() {
        return binding.etLeetcodeUsername.getText() != null
                ? binding.etLeetcodeUsername.getText().toString().trim()
                : "";
    }

    private void observeViewModel() {
        viewModel.getConnectionsLive().observe(this, connections -> {
            boolean empty = connections == null || connections.isEmpty();
            binding.tvNoProfiles.setVisibility(empty ? View.VISIBLE : View.GONE);
            binding.rvConnectedProfiles.setVisibility(empty ? View.GONE : View.VISIBLE);
            if (!empty) {
                profilesAdapter.submitList(connections);
                binding.tvLeetcodeConnStatus.setText(connections.size() + " Linked");
                binding.tvLeetcodeConnStatus.setTextColor(ContextCompat.getColor(this, R.color.easy_green));
            } else {
                binding.tvLeetcodeConnStatus.setText("0 Linked");
                binding.tvLeetcodeConnStatus.setTextColor(ContextCompat.getColor(this, R.color.dark_on_surface_variant));
            }
        });

        viewModel.getRecentSyncHistory().observe(this, history -> {
            if (history != null) {
                historyAdapter.submitList(history);
            }
        });

        viewModel.getIsLoading().observe(this, loading -> {
            boolean isLoading = Boolean.TRUE.equals(loading);
            binding.progressSync.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.btnCheckProfile.setEnabled(!isLoading);
            binding.btnSyncNow.setEnabled(!isLoading);
        });

        viewModel.getStatusMessage().observe(this, msg -> {
            if (msg != null && !msg.isEmpty()) {
                binding.tvSyncStatusMsg.setVisibility(View.VISIBLE);
                binding.tvSyncStatusMsg.setTextColor(ContextCompat.getColor(this, R.color.easy_green));
                binding.tvSyncStatusMsg.setText(msg);
            } else {
                binding.tvSyncStatusMsg.setVisibility(View.GONE);
            }
        });

        viewModel.getErrorMessage().observe(this, err -> {
            if (err != null && !err.isEmpty()) {
                binding.tvSyncStatusMsg.setVisibility(View.VISIBLE);
                binding.tvSyncStatusMsg.setTextColor(ContextCompat.getColor(this, R.color.hard_red));
                binding.tvSyncStatusMsg.setText(err);
            }
        });

        viewModel.getProfilePreview().observe(this, this::renderProfilePreview);
    }

    private void renderProfilePreview(LeetCodeProfile profile) {
        if (profile == null) {
            binding.cardProfilePreview.setVisibility(View.GONE);
            return;
        }

        binding.cardProfilePreview.setVisibility(View.VISIBLE);
        binding.tvPreviewUsername.setText(profile.getUsername());
        binding.tvPreviewRank.setText("Ranking: #" + profile.getRanking());
        binding.tvPreviewTotalSolved.setText(profile.getTotalSolved() + " Solved");
        binding.tvPreviewEasy.setText("Easy: " + profile.getEasySolved());
        binding.tvPreviewMedium.setText("Med: " + profile.getMediumSolved());
        binding.tvPreviewHard.setText("Hard: " + profile.getHardSolved());
    }
}


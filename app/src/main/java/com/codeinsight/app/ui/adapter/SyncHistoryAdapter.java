package com.codeinsight.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinsight.app.R;
import com.codeinsight.app.data.local.entity.SyncHistory;
import com.codeinsight.app.databinding.ItemSyncHistoryBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SyncHistoryAdapter extends ListAdapter<SyncHistory, SyncHistoryAdapter.SyncHistoryViewHolder> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault());

    public SyncHistoryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<SyncHistory> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<SyncHistory>() {
                @Override
                public boolean areItemsTheSame(@NonNull SyncHistory oldItem, @NonNull SyncHistory newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull SyncHistory oldItem, @NonNull SyncHistory newItem) {
                    return oldItem.getItemsSynced() == newItem.getItemsSynced()
                            && oldItem.getStatus().equals(newItem.getStatus())
                            && oldItem.getSyncedAt() == newItem.getSyncedAt();
                }
            };

    @NonNull
    @Override
    public SyncHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSyncHistoryBinding binding = ItemSyncHistoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new SyncHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SyncHistoryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class SyncHistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemSyncHistoryBinding binding;

        SyncHistoryViewHolder(ItemSyncHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(SyncHistory history) {
            Context context = itemView.getContext();
            binding.tvHistoryStatus.setText(history.getStatus());

            if ("SUCCESS".equalsIgnoreCase(history.getStatus())) {
                binding.tvHistoryStatus.setTextColor(ContextCompat.getColor(context, R.color.easy_green));
                binding.tvHistoryStatus.setBackgroundResource(R.drawable.bg_badge_green);
                if (history.getItemsSynced() > 0) {
                    binding.tvHistoryItems.setText(history.getItemsSynced() + " solved problems verified");
                } else {
                    binding.tvHistoryItems.setText("Profile up to date");
                }
            } else {
                binding.tvHistoryStatus.setTextColor(ContextCompat.getColor(context, R.color.hard_red));
                binding.tvHistoryStatus.setBackgroundResource(R.drawable.bg_badge_red);
                String msg = history.getErrorMessage();
                binding.tvHistoryItems.setText(msg != null && !msg.isEmpty() ? msg : "Sync failed");
            }

            binding.tvHistoryDate.setText(DATE_FORMAT.format(new Date(history.getSyncedAt())));
        }
    }
}


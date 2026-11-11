package com.codeinsight.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinsight.app.R;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.databinding.ItemConnectedProfileBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ConnectedProfilesAdapter extends ListAdapter<PlatformConnection, ConnectedProfilesAdapter.ViewHolder> {

    public interface OnProfileActionListener {
        void onSetActive(PlatformConnection connection);
        void onSync(PlatformConnection connection);
        void onDelete(PlatformConnection connection);
    }

    private final OnProfileActionListener listener;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault());

    public ConnectedProfilesAdapter(OnProfileActionListener listener) {
        super(new DiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConnectedProfileBinding binding = ItemConnectedProfileBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemConnectedProfileBinding binding;

        ViewHolder(ItemConnectedProfileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PlatformConnection conn) {
            Context context = itemView.getContext();
            binding.tvProfileUsername.setText("@" + conn.getUsername());

            boolean isActive = conn.isActive();
            if (isActive) {
                binding.tvProfileActiveBadge.setVisibility(View.VISIBLE);
                binding.tvProfileActiveBadge.setText("ACTIVE");
                binding.tvProfileActiveBadge.setTextColor(ContextCompat.getColor(context, R.color.easy_green));
                binding.tvProfileActiveBadge.setBackgroundResource(R.drawable.bg_badge_green);
                binding.btnSetActive.setVisibility(View.GONE);
                binding.cardProfileRoot.setStrokeColor(ContextCompat.getColor(context, R.color.primary));
                binding.cardProfileRoot.setStrokeWidth(2);
            } else {
                binding.tvProfileActiveBadge.setVisibility(View.GONE);
                binding.btnSetActive.setVisibility(View.VISIBLE);
                binding.cardProfileRoot.setStrokeColor(ContextCompat.getColor(context, R.color.dark_surface));
                binding.cardProfileRoot.setStrokeWidth(1);
            }

            int total = conn.getTotalSolved();
            if (total > 0) {
                binding.tvProfileSolvedStats.setText(
                    total + " Solved • Easy: " + conn.getEasySolved() +
                    " • Med: " + conn.getMediumSolved() +
                    " • Hard: " + conn.getHardSolved() +
                    (conn.getRanking() > 0 ? " • #" + conn.getRanking() : "")
                );
            } else {
                binding.tvProfileSolvedStats.setText("Connected • No verified stats yet");
            }

            if (conn.getLastSyncAt() != null && conn.getLastSyncAt() > 0) {
                binding.tvProfileLastSync.setText("Last synced: " + DATE_FORMAT.format(new Date(conn.getLastSyncAt())));
            } else {
                binding.tvProfileLastSync.setText("Not synced yet");
            }

            binding.btnSetActive.setOnClickListener(v -> {
                if (listener != null) listener.onSetActive(conn);
            });

            binding.btnSyncProfile.setOnClickListener(v -> {
                if (listener != null) listener.onSync(conn);
            });

            binding.btnDeleteProfile.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(conn);
            });

            itemView.setOnClickListener(v -> {
                if (!isActive && listener != null) {
                    listener.onSetActive(conn);
                }
            });
        }
    }

    private static class DiffCallback extends DiffUtil.ItemCallback<PlatformConnection> {
        @Override
        public boolean areItemsTheSame(@NonNull PlatformConnection oldItem, @NonNull PlatformConnection newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull PlatformConnection oldItem, @NonNull PlatformConnection newItem) {
            return oldItem.isActive() == newItem.isActive()
                && Objects.equals(oldItem.getUsername(), newItem.getUsername())
                && oldItem.getTotalSolved() == newItem.getTotalSolved()
                && oldItem.getEasySolved() == newItem.getEasySolved()
                && oldItem.getMediumSolved() == newItem.getMediumSolved()
                && oldItem.getHardSolved() == newItem.getHardSolved()
                && Objects.equals(oldItem.getLastSyncAt(), newItem.getLastSyncAt());
        }
    }
}

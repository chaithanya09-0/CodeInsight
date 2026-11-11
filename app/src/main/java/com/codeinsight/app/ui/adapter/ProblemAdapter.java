package com.codeinsight.app.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinsight.app.R;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.databinding.ItemProblemBinding;
import com.codeinsight.app.domain.model.Difficulty;
import com.codeinsight.app.domain.model.ProblemStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProblemAdapter extends ListAdapter<ProblemWithDetails, ProblemAdapter.ProblemViewHolder> {

    public interface OnProblemClickListener {
        void onProblemClick(ProblemWithDetails problem);
    }

    private final OnProblemClickListener listener;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());

    public ProblemAdapter(OnProblemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<ProblemWithDetails> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ProblemWithDetails>() {
                @Override
                public boolean areItemsTheSame(@NonNull ProblemWithDetails oldItem, @NonNull ProblemWithDetails newItem) {
                    return oldItem.problem.getId() == newItem.problem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ProblemWithDetails oldItem, @NonNull ProblemWithDetails newItem) {
                    return oldItem.problem.getTitle().equals(newItem.problem.getTitle())
                            && oldItem.problem.getStatus().equals(newItem.problem.getStatus())
                            && oldItem.problem.getDifficulty().equals(newItem.problem.getDifficulty())
                            && oldItem.attempts.size() == newItem.attempts.size()
                            && oldItem.topics.size() == newItem.topics.size();
                }
            };

    @NonNull
    @Override
    public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProblemBinding binding = ItemProblemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ProblemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ProblemViewHolder extends RecyclerView.ViewHolder {
        private final ItemProblemBinding binding;

        ProblemViewHolder(ItemProblemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ProblemWithDetails item) {
            Context context = itemView.getContext();
            binding.tvItemTitle.setText(item.problem.getTitle());
            binding.tvItemPlatform.setText(item.problem.getPlatform() != null ? item.problem.getPlatform() : "LeetCode");

            // Difficulty styling
            Difficulty diff = Difficulty.fromString(item.problem.getDifficulty());
            binding.tvItemDifficulty.setText(diff.getDisplayName());
            int diffColor;
            int diffBg;
            switch (diff) {
                case EASY:
                    diffColor = ContextCompat.getColor(context, R.color.easy_green);
                    diffBg = R.drawable.bg_badge_green;
                    break;
                case HARD:
                    diffColor = ContextCompat.getColor(context, R.color.hard_red);
                    diffBg = R.drawable.bg_badge_red;
                    break;
                case MEDIUM:
                default:
                    diffColor = ContextCompat.getColor(context, R.color.medium_orange);
                    diffBg = R.drawable.bg_badge_orange;
                    break;
            }
            binding.tvItemDifficulty.setTextColor(diffColor);
            binding.tvItemDifficulty.setBackgroundResource(diffBg);

            // Dynamic attempt-derived status
            ProblemStatus status = ProblemStatus.fromString(item.getCalculatedStatus());
            binding.tvItemStatus.setText(status.getDisplayName());
            if (status == ProblemStatus.SOLVED) {
                binding.tvItemStatus.setTextColor(ContextCompat.getColor(context, R.color.easy_green));
            } else if (status == ProblemStatus.ATTEMPTED) {
                binding.tvItemStatus.setTextColor(ContextCompat.getColor(context, R.color.medium_orange));
            } else {
                binding.tvItemStatus.setTextColor(ContextCompat.getColor(context, R.color.dark_on_surface_variant));
            }

            // Topics list
            String topicsStr = item.getFormattedTopics();
            if (topicsStr.isEmpty()) {
                binding.tvItemTopics.setText("General");
            } else {
                binding.tvItemTopics.setText(topicsStr);
            }

            // Attempts
            int attemptCount = item.getTotalAttemptsCount();
            binding.tvItemAttempts.setText(attemptCount == 1 ? "1 attempt" : attemptCount + " attempts");

            // Date by latest activity
            long activityTs = item.getLatestActivityTimestamp();
            if (activityTs <= 0) activityTs = item.problem.getUpdatedAt();
            binding.tvItemDate.setText(DATE_FORMAT.format(new Date(activityTs)));

            // Click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProblemClick(item);
                }
            });
        }
    }
}

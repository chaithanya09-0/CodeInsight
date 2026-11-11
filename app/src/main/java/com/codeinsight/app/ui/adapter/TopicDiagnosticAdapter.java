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
import com.codeinsight.app.databinding.ItemTopicDiagnosticBinding;
import com.codeinsight.app.domain.analysis.TopicDiagnostic;

public class TopicDiagnosticAdapter extends ListAdapter<TopicDiagnostic, TopicDiagnosticAdapter.TopicDiagnosticViewHolder> {

    public TopicDiagnosticAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TopicDiagnostic> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TopicDiagnostic>() {
                @Override
                public boolean areItemsTheSame(@NonNull TopicDiagnostic oldItem, @NonNull TopicDiagnostic newItem) {
                    return oldItem.getTopicName().equals(newItem.getTopicName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull TopicDiagnostic oldItem, @NonNull TopicDiagnostic newItem) {
                    return oldItem.getAcceptanceRate() == newItem.getAcceptanceRate()
                            && oldItem.getTotalProblems() == newItem.getTotalProblems()
                            && oldItem.getStatusRisk().equals(newItem.getStatusRisk());
                }
            };

    @NonNull
    @Override
    public TopicDiagnosticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTopicDiagnosticBinding binding = ItemTopicDiagnosticBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TopicDiagnosticViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicDiagnosticViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class TopicDiagnosticViewHolder extends RecyclerView.ViewHolder {
        private final ItemTopicDiagnosticBinding binding;

        TopicDiagnosticViewHolder(ItemTopicDiagnosticBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TopicDiagnostic item) {
            Context context = itemView.getContext();
            binding.tvDiagTopicName.setText(item.getTopicName());

            String risk = item.getStatusRisk();
            binding.tvDiagRisk.setText(risk);
            if ("Critical".equalsIgnoreCase(risk)) {
                binding.tvDiagRisk.setTextColor(ContextCompat.getColor(context, R.color.hard_red));
                binding.tvDiagRisk.setBackgroundResource(R.drawable.bg_badge_red);
                binding.tvDiagAcceptance.setTextColor(ContextCompat.getColor(context, R.color.hard_red));
            } else if ("Needs Improvement".equalsIgnoreCase(risk) || "Needs Work".equalsIgnoreCase(risk)) {
                binding.tvDiagRisk.setTextColor(ContextCompat.getColor(context, R.color.medium_orange));
                binding.tvDiagRisk.setBackgroundResource(R.drawable.bg_badge_orange);
                binding.tvDiagAcceptance.setTextColor(ContextCompat.getColor(context, R.color.medium_orange));
            } else if ("Strong".equalsIgnoreCase(risk)) {
                binding.tvDiagRisk.setTextColor(ContextCompat.getColor(context, R.color.easy_green));
                binding.tvDiagRisk.setBackgroundResource(R.drawable.bg_badge_green);
                binding.tvDiagAcceptance.setTextColor(ContextCompat.getColor(context, R.color.easy_green));
            } else { // Insufficient Data
                binding.tvDiagRisk.setTextColor(ContextCompat.getColor(context, R.color.dark_on_surface_variant));
                binding.tvDiagRisk.setBackgroundResource(R.drawable.bg_badge_indigo);
                binding.tvDiagAcceptance.setTextColor(ContextCompat.getColor(context, R.color.dark_on_surface_variant));
            }

            int ratePct = (int) Math.round(item.getAcceptanceRate());
            binding.tvDiagAcceptance.setText(ratePct + "%");
            binding.tvDiagSolvedTotal.setText(item.getSolvedCount() + " / " + item.getTotalProblems());
            binding.tvDiagAttempts.setText(String.valueOf(item.getTotalAttempts()));

            if (item.getRationale() != null && !item.getRationale().isEmpty()) {
                binding.tvDiagDominantMistake.setText(item.getRationale());
            } else if (item.getDominantMistake() != null) {
                binding.tvDiagDominantMistake.setText("Dominant mistake: "
                        + item.getDominantMistake().getDisplayName()
                        + " (" + item.getDominantMistakeCount() + "x)");
            } else if (!item.isDataSufficient()) {
                binding.tvDiagDominantMistake.setText("Need at least 3 attempts to evaluate mistake patterns reliably.");
            } else {
                binding.tvDiagDominantMistake.setText("No recurring failure pattern identified.");
            }
        }
    }
}


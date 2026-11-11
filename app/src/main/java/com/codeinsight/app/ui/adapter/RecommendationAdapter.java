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
import com.codeinsight.app.databinding.ItemRecommendationBinding;
import com.codeinsight.app.domain.analysis.GeneratedRecommendation;

import java.util.List;

public class RecommendationAdapter extends ListAdapter<GeneratedRecommendation, RecommendationAdapter.RecommendationViewHolder> {

    public interface OnDismissRecommendationListener {
        void onDismiss(GeneratedRecommendation recommendation);
    }

    private final OnDismissRecommendationListener dismissListener;

    public RecommendationAdapter(OnDismissRecommendationListener dismissListener) {
        super(DIFF_CALLBACK);
        this.dismissListener = dismissListener;
    }

    private static final DiffUtil.ItemCallback<GeneratedRecommendation> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<GeneratedRecommendation>() {
                @Override
                public boolean areItemsTheSame(@NonNull GeneratedRecommendation oldItem, @NonNull GeneratedRecommendation newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle())
                            && oldItem.getTopicName().equals(newItem.getTopicName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull GeneratedRecommendation oldItem, @NonNull GeneratedRecommendation newItem) {
                    return oldItem.getDescription().equals(newItem.getDescription())
                            && oldItem.getPriority() == newItem.getPriority();
                }
            };

    @NonNull
    @Override
    public RecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecommendationBinding binding = ItemRecommendationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RecommendationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class RecommendationViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecommendationBinding binding;

        RecommendationViewHolder(ItemRecommendationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(GeneratedRecommendation item) {
            Context context = itemView.getContext();
            binding.tvRecTopic.setText(item.getTopicName());
            binding.tvRecTitle.setText(item.getTitle());
            binding.tvRecDescription.setText(item.getDescription());
            binding.tvRecReasoning.setText(item.getReasoning());

            int prio = item.getPriority();
            if (prio >= 3) {
                binding.tvRecPriority.setText("High Priority");
                binding.tvRecPriority.setTextColor(ContextCompat.getColor(context, R.color.hard_red));
                binding.tvRecPriority.setBackgroundResource(R.drawable.bg_badge_red);
            } else if (prio == 2) {
                binding.tvRecPriority.setText("Medium Priority");
                binding.tvRecPriority.setTextColor(ContextCompat.getColor(context, R.color.medium_orange));
                binding.tvRecPriority.setBackgroundResource(R.drawable.bg_badge_orange);
            } else {
                binding.tvRecPriority.setText("Practice Tip");
                binding.tvRecPriority.setTextColor(ContextCompat.getColor(context, R.color.primary));
                binding.tvRecPriority.setBackgroundResource(R.drawable.bg_badge_teal);
            }

            List<String> steps = item.getActionableSteps();
            if (steps != null && !steps.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < steps.size(); i++) {
                    sb.append("• ").append(steps.get(i));
                    if (i < steps.size() - 1) sb.append("\n");
                }
                binding.tvRecSteps.setText(sb.toString());
            } else {
                binding.tvRecSteps.setText("• Focus on deliberate practice with foundational problems.");
            }

            binding.btnDismissRec.setOnClickListener(v -> {
                if (dismissListener != null) {
                    dismissListener.onDismiss(item);
                }
            });
        }
    }
}


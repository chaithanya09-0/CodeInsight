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
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.MistakeWithProblem;
import com.codeinsight.app.databinding.ItemMistakeCardBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MistakeCardAdapter extends ListAdapter<MistakeWithProblem, MistakeCardAdapter.MistakeViewHolder> {

    public interface OnMistakeClickListener {
        void onMistakeClick(MistakeWithProblem item);
    }

    public interface OnMistakeEditListener {
        void onEditClick(MistakeWithProblem item);
    }

    public interface OnMistakeDeleteListener {
        void onDeleteClick(Mistake mistake);
    }

    private final OnMistakeClickListener clickListener;
    private final OnMistakeEditListener editListener;
    private final OnMistakeDeleteListener deleteListener;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());

    public MistakeCardAdapter(
        OnMistakeClickListener clickListener,
        OnMistakeEditListener editListener,
        OnMistakeDeleteListener deleteListener
    ) {
        super(DIFF_CALLBACK);
        this.clickListener = clickListener;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    private static final DiffUtil.ItemCallback<MistakeWithProblem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MistakeWithProblem>() {
                @Override
                public boolean areItemsTheSame(@NonNull MistakeWithProblem oldItem, @NonNull MistakeWithProblem newItem) {
                    return oldItem.mistake != null && newItem.mistake != null &&
                            oldItem.mistake.getId() == newItem.mistake.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull MistakeWithProblem oldItem, @NonNull MistakeWithProblem newItem) {
                    if (oldItem.mistake == null || newItem.mistake == null) return false;
                    boolean sameMistake = oldItem.mistake.getId() == newItem.mistake.getId()
                            && String.valueOf(oldItem.mistake.getCategory()).equals(String.valueOf(newItem.mistake.getCategory()))
                            && String.valueOf(oldItem.mistake.getDescription()).equals(String.valueOf(newItem.mistake.getDescription()))
                            && String.valueOf(oldItem.mistake.getNotes()).equals(String.valueOf(newItem.mistake.getNotes()));

                    boolean sameProblem = (oldItem.problem == null && newItem.problem == null)
                            || (oldItem.problem != null && newItem.problem != null
                            && oldItem.problem.getId() == newItem.problem.getId()
                            && String.valueOf(oldItem.problem.getTitle()).equals(String.valueOf(newItem.problem.getTitle())));

                    return sameMistake && sameProblem;
                }
            };

    @NonNull
    @Override
    public MistakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMistakeCardBinding binding = ItemMistakeCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MistakeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MistakeViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class MistakeViewHolder extends RecyclerView.ViewHolder {
        private final ItemMistakeCardBinding binding;

        MistakeViewHolder(ItemMistakeCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MistakeWithProblem item) {
            Context context = itemView.getContext();
            if (item == null || item.mistake == null) return;

            // Problem Title
            if (item.problem != null && item.problem.getTitle() != null && !item.problem.getTitle().isEmpty()) {
                binding.tvProblemTitle.setText(item.problem.getTitle());
            } else {
                binding.tvProblemTitle.setText("Problem #" + item.mistake.getProblemId());
            }

            // Platform Badge
            if (item.problem != null && item.problem.getPlatform() != null && !item.problem.getPlatform().isEmpty()) {
                binding.tvPlatformBadge.setText(item.problem.getPlatform());
                binding.tvPlatformBadge.setVisibility(View.VISIBLE);
            } else {
                binding.tvPlatformBadge.setVisibility(View.GONE);
            }

            // Difficulty Badge
            if (item.problem != null && item.problem.getDifficulty() != null) {
                binding.tvDifficultyBadge.setVisibility(View.VISIBLE);
                String diff = item.problem.getDifficulty();
                binding.tvDifficultyBadge.setText(diff);
                if ("Easy".equalsIgnoreCase(diff)) {
                    binding.tvDifficultyBadge.setTextColor(ContextCompat.getColor(context, R.color.easy_green));
                    binding.tvDifficultyBadge.setBackgroundResource(R.drawable.bg_badge_green);
                } else if ("Hard".equalsIgnoreCase(diff)) {
                    binding.tvDifficultyBadge.setTextColor(ContextCompat.getColor(context, R.color.hard_red));
                    binding.tvDifficultyBadge.setBackgroundResource(R.drawable.bg_badge_red);
                } else {
                    binding.tvDifficultyBadge.setTextColor(ContextCompat.getColor(context, R.color.medium_orange));
                    binding.tvDifficultyBadge.setBackgroundResource(R.drawable.bg_badge_orange);
                }
            } else {
                binding.tvDifficultyBadge.setVisibility(View.GONE);
            }

            // Date
            long dateTs = item.mistake.getCreatedAt() > 0 ? item.mistake.getCreatedAt() : System.currentTimeMillis();
            binding.tvMistakeDate.setText(DATE_FORMAT.format(new Date(dateTs)));

            // Mistake Category
            String cat = item.mistake.getCategory();
            binding.tvMistakeCategory.setText(cat != null && !cat.isEmpty() ? cat : "General Error");

            // Description
            String desc = item.mistake.getDescription();
            if (desc != null && !desc.trim().isEmpty()) {
                binding.tvMistakeDescription.setText(desc);
                binding.tvMistakeDescription.setVisibility(View.VISIBLE);
            } else {
                binding.tvMistakeDescription.setVisibility(View.GONE);
            }

            // Notes / Avoidance Checklist
            String notes = item.mistake.getNotes();
            if (notes != null && !notes.trim().isEmpty()) {
                binding.tvMistakeNotes.setText(notes);
                binding.layoutNotesContainer.setVisibility(View.VISIBLE);
            } else {
                binding.layoutNotesContainer.setVisibility(View.GONE);
            }

            // Click listener
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onMistakeClick(item);
                }
            });

            // Edit listener
            binding.btnEditMistake.setOnClickListener(v -> {
                if (editListener != null) {
                    editListener.onEditClick(item);
                }
            });

            // Delete listener
            binding.btnDeleteMistake.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteClick(item.mistake);
                }
            });
        }
    }
}


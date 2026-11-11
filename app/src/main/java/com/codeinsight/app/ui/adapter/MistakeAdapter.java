package com.codeinsight.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.databinding.ItemMistakeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MistakeAdapter extends ListAdapter<Mistake, MistakeAdapter.MistakeViewHolder> {

    public interface OnDeleteMistakeListener {
        void onDeleteMistake(Mistake mistake);
    }

    private final OnDeleteMistakeListener deleteListener;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault());

    public MistakeAdapter(OnDeleteMistakeListener deleteListener) {
        super(DIFF_CALLBACK);
        this.deleteListener = deleteListener;
    }

    private static final DiffUtil.ItemCallback<Mistake> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Mistake>() {
                @Override
                public boolean areItemsTheSame(@NonNull Mistake oldItem, @NonNull Mistake newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Mistake oldItem, @NonNull Mistake newItem) {
                    return oldItem.getCategory().equals(newItem.getCategory())
                            && oldItem.getDescription().equals(newItem.getDescription())
                            && oldItem.getNotes().equals(newItem.getNotes());
                }
            };

    @NonNull
    @Override
    public MistakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMistakeBinding binding = ItemMistakeBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MistakeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MistakeViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class MistakeViewHolder extends RecyclerView.ViewHolder {
        private final ItemMistakeBinding binding;

        MistakeViewHolder(ItemMistakeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Mistake mistake) {
            binding.tvMistakeCategory.setText(mistake.getCategory());

            if (mistake.getDescription() != null && !mistake.getDescription().trim().isEmpty()) {
                binding.tvMistakeDescription.setVisibility(View.VISIBLE);
                binding.tvMistakeDescription.setText(mistake.getDescription().trim());
            } else {
                binding.tvMistakeDescription.setVisibility(View.GONE);
            }

            if (mistake.getNotes() != null && !mistake.getNotes().trim().isEmpty()) {
                binding.tvMistakeNotes.setVisibility(View.VISIBLE);
                binding.tvMistakeNotes.setText("Note: " + mistake.getNotes().trim());
            } else {
                binding.tvMistakeNotes.setVisibility(View.GONE);
            }

            binding.tvMistakeDate.setText(DATE_FORMAT.format(new Date(mistake.getCreatedAt())));

            binding.btnDeleteMistake.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteMistake(mistake);
                }
            });
        }
    }
}


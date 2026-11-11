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
import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.databinding.ItemAttemptBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttemptAdapter extends ListAdapter<CodingAttempt, AttemptAdapter.AttemptViewHolder> {

    public interface OnAttemptClickListener {
        void onAttemptClick(CodingAttempt attempt);
    }

    private final OnAttemptClickListener listener;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault());

    public AttemptAdapter(OnAttemptClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<CodingAttempt> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CodingAttempt>() {
                @Override
                public boolean areItemsTheSame(@NonNull CodingAttempt oldItem, @NonNull CodingAttempt newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull CodingAttempt oldItem, @NonNull CodingAttempt newItem) {
                    return oldItem.isAccepted() == newItem.isAccepted()
                            && oldItem.getAttemptedAt() == newItem.getAttemptedAt()
                            && (oldItem.getNotes() == null ? "" : oldItem.getNotes())
                                    .equals(newItem.getNotes() == null ? "" : newItem.getNotes());
                }
            };

    @NonNull
    @Override
    public AttemptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAttemptBinding binding = ItemAttemptBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new AttemptViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AttemptViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class AttemptViewHolder extends RecyclerView.ViewHolder {
        private final ItemAttemptBinding binding;

        AttemptViewHolder(ItemAttemptBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CodingAttempt attempt) {
            Context context = itemView.getContext();

            if (attempt.isAccepted()) {
                binding.tvAttemptStatus.setText("Accepted");
                binding.tvAttemptStatus.setTextColor(ContextCompat.getColor(context, R.color.easy_green));
                binding.tvAttemptStatus.setBackgroundResource(R.drawable.bg_badge_green);
            } else {
                binding.tvAttemptStatus.setText("Failed");
                binding.tvAttemptStatus.setTextColor(ContextCompat.getColor(context, R.color.hard_red));
                binding.tvAttemptStatus.setBackgroundResource(R.drawable.bg_badge_red);
            }

            binding.tvAttemptLanguage.setText(attempt.getLanguage() != null ? attempt.getLanguage() : "Java");

            if (attempt.getDurationMinutes() != null && attempt.getDurationMinutes() > 0) {
                binding.tvAttemptDuration.setVisibility(View.VISIBLE);
                binding.tvAttemptDuration.setText("• " + attempt.getDurationMinutes() + "m");
            } else {
                binding.tvAttemptDuration.setVisibility(View.GONE);
            }

            binding.tvAttemptDate.setText(DATE_FORMAT.format(new Date(attempt.getAttemptedAt())));

            if (attempt.getNotes() != null && !attempt.getNotes().trim().isEmpty()) {
                binding.tvAttemptNotes.setVisibility(View.VISIBLE);
                binding.tvAttemptNotes.setText(attempt.getNotes().trim());
            } else {
                binding.tvAttemptNotes.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAttemptClick(attempt);
                }
            });
        }
    }
}


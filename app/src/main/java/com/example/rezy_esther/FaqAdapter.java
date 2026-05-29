package com.example.rezy_esther;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rezy_esther.databinding.ItemFaqBinding;

import java.util.List;

public class FaqAdapter extends ArrayAdapter<FaqModel> {

    private final List<FaqModel> faqList;

    public FaqAdapter(@NonNull Context context, @NonNull List<FaqModel> faqList) {
        super(context, 0, faqList);
        this.faqList = faqList;
    }

    // ViewHolder for efficiency
    private static class ViewHolder {
        final ItemFaqBinding binding;
        ViewHolder(ItemFaqBinding binding) {
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View view;

        if (convertView == null) {
            ItemFaqBinding binding = ItemFaqBinding.inflate(
                    LayoutInflater.from(getContext()), parent, false
            );
            view = binding.getRoot();
            holder = new ViewHolder(binding);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        FaqModel data = getItem(position);

        if (data != null) {
            holder.binding.tvFaqIcon.setText(data.iconEmoji);
            holder.binding.tvFaqPertanyaan.setText(data.pertanyaan);
            holder.binding.tvFaqJawaban.setText(data.jawaban);

            // Default state: collapsed
            holder.binding.tvFaqJawaban.setVisibility(View.GONE);
            holder.binding.ivFaqArrow.setRotation(0f);

            view.setOnClickListener(v -> {
                boolean isExpanded = holder.binding.tvFaqJawaban.getVisibility() == View.VISIBLE;
                if (isExpanded) {
                    holder.binding.tvFaqJawaban.setVisibility(View.GONE);
                    holder.binding.ivFaqArrow.animate().rotation(0f).setDuration(200).start();
                } else {
                    holder.binding.tvFaqJawaban.setVisibility(View.VISIBLE);
                    holder.binding.ivFaqArrow.animate().rotation(180f).setDuration(200).start();
                }
            });
        }

        return view;
    }
}

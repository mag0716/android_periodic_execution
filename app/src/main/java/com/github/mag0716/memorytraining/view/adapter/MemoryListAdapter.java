package com.github.mag0716.memorytraining.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.ViewListItemBinding;
import com.github.mag0716.memorytraining.model.Memory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by mag0716 on 2017/06/13.
 */
public class MemoryListAdapter extends RecyclerView.Adapter<MemoryListAdapter.MemoryViewHolder> {

    private final LayoutInflater inflater;
    private List<Memory> memoryList = new ArrayList<>();

    public MemoryListAdapter(@NonNull Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MemoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new MemoryViewHolder(inflater.inflate(R.layout.view_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(MemoryViewHolder viewHolder, int position) {
        // TODO: setVariable & executePendingBindings
        final Memory memory = memoryList.get(position);
        ((ViewListItemBinding) viewHolder.binding).questionText.setText(memory.getQuestion());
        ((ViewListItemBinding) viewHolder.binding).answerText.setText(memory.getAnswer());
        ((ViewListItemBinding) viewHolder.binding).openAndCloseIcon.setOnClickListener(v -> {
            final View answerGroup = ((ViewListItemBinding) viewHolder.binding).answerGroup;
            answerGroup.setVisibility(answerGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }

    public void addAll(List<Memory> memoryList) {
        this.memoryList.clear();
        if (memoryList != null) {
            this.memoryList.addAll(memoryList);
            notifyDataSetChanged();
        }
    }

    public class MemoryViewHolder extends RecyclerView.ViewHolder {

        @Getter
        private final ViewDataBinding binding;

        public MemoryViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}

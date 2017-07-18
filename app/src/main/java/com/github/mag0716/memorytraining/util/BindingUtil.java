package com.github.mag0716.memorytraining.util;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.mag0716.memorytraining.view.adapter.MemoryListAdapter;
import com.github.mag0716.memorytraining.viewmodel.ListItemViewModel;

import java.util.List;

/**
 * Created by mag0716 on 2017/06/17.
 */
public class BindingUtil {

    @BindingAdapter("visibleOrInvisible")
    public static void setVisibleOrInvisible(@NonNull View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("visibleOrGone")
    public static void setVisibleOrGone(@NonNull View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("srcCompat")
    public static void setSrcCompat(@NonNull ImageView view, @DrawableRes int resourceId) {
        view.setImageResource(resourceId);
    }

    @BindingAdapter("memoryViewModelList")
    public static void setMemoryViewModelList(@NonNull RecyclerView view, @NonNull List<ListItemViewModel> viewModelList) {
        if (view.getAdapter() instanceof MemoryListAdapter) {
            final MemoryListAdapter adapter = ((MemoryListAdapter) view.getAdapter());
            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MemoryListDiffCallback(adapter.getViewModelList(), viewModelList));
            adapter.setViewModelList(viewModelList);
            diffResult.dispatchUpdatesTo(adapter);
        }
    }
}

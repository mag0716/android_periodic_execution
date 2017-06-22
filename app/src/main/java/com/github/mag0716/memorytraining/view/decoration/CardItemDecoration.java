package com.github.mag0716.memorytraining.view.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.mag0716.memorytraining.R;

/**
 * Created by mag0716 on 2017/06/14.
 */
public class CardItemDecoration extends RecyclerView.ItemDecoration {

    private final Context context;
    private final int verticalSpace;
    private final int topPadding;

    public CardItemDecoration(@NonNull Context context) {
        this.context = context;
        topPadding = context.getResources().getDimensionPixelOffset(R.dimen.lists_top_padding);
        verticalSpace = context.getResources().getDimensionPixelOffset(R.dimen.cards_vertical_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);
        outRect.set(0, position == 0 ? topPadding : 0, 0, verticalSpace);
    }
}

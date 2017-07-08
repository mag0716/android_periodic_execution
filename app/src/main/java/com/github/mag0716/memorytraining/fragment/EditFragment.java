package com.github.mag0716.memorytraining.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.FragmentEditBinding;
import com.github.mag0716.memorytraining.model.Memory;

/**
 * 追加、編集画面
 * <p>
 * Created by mag0716 on 2017/07/08.
 */
public class EditFragment extends Fragment {

    public static final String TAG = EditFragment.class.getCanonicalName();
    private static final String EXTRA_MEMORY = TAG + ".MEMORY";

    private FragmentEditBinding binding;

    /**
     * インスタンスを返却
     *
     * @return EditFragment
     */
    public static EditFragment newInstance() {
        return new EditFragment();
    }

    /**
     * インスタンスを返却
     *
     * @param memory 編集対象データ
     * @return EditFragment
     */
    public static EditFragment newInstance(@NonNull Memory memory) {
        final EditFragment fragment = new EditFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MEMORY, memory);
        fragment.setArguments(bundle);
        return fragment;
    }

    public EditFragment() {
    }

    // region lifecycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false);
        return binding.getRoot();
    }


    // endregion
}

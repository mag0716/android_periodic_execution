package com.github.mag0716.memorytraining.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.FragmentEditBinding;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.presenter.EditPresenter;
import com.github.mag0716.memorytraining.view.EditView;
import com.github.mag0716.memorytraining.viewmodel.EditViewModel;

import timber.log.Timber;

/**
 * 追加、編集画面
 * <p>
 * Created by mag0716 on 2017/07/08.
 */
public class EditFragment extends Fragment implements EditView {

    public static final String TAG = EditFragment.class.getCanonicalName();
    private static final String EXTRA_MEMORY = TAG + ".MEMORY";

    private FragmentEditBinding binding;
    private EditViewModel viewModel;
    private EditPresenter presenter;

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
        presenter = new EditPresenter(((Application) getContext().getApplicationContext()).getDatabase().memoryDao());
        binding.setPresenter(presenter);
        final Bundle bundle = getArguments();
        Memory memory = null;
        if (bundle != null) {
            memory = bundle.getParcelable(EXTRA_MEMORY);
        }
        viewModel = new EditViewModel(memory != null ? memory : new Memory());
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachView();
    }

    // endregion

    // region EditView

    @Override
    public void saveSuccess() {
        Timber.d("saveSuccess");
        getActivity().onBackPressed();
    }

    @Override
    public void saveFailed(@NonNull Throwable throwable) {
        Timber.w(throwable, "saveFailed");
        // TODO: 基本は発生しないはずだが、調査できるように Exception はトラッキングしておく
    }

    // endregion
}

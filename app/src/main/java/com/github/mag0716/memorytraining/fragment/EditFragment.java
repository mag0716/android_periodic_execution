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
    private static final String EXTRA_MEMORY_ID = TAG + ".MEMORY_ID";

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
     * @param id 編集対象データの ID
     * @return EditFragment
     */
    public static EditFragment newInstance(long id) {
        final EditFragment fragment = new EditFragment();
        final Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_MEMORY_ID, id);
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
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.loadIfNeeded(getArguments(), EXTRA_MEMORY_ID);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachView();
    }

    // endregion

    // region EditView

    @Override
    public void showMemory(@NonNull Memory memory) {
        viewModel = new EditViewModel(memory);
        binding.setViewModel(viewModel);
    }

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

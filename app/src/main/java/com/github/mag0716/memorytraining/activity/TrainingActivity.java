package com.github.mag0716.memorytraining.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.ActivityTrainingBinding;
import com.github.mag0716.memorytraining.fragment.EditFragment;
import com.github.mag0716.memorytraining.fragment.ListFragment;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.presenter.TrainingPresenter;
import com.github.mag0716.memorytraining.view.TrainingView;

/**
 * 訓練画面
 */
public class TrainingActivity extends AppCompatActivity implements TrainingView, FragmentManager.OnBackStackChangedListener {

    private ActivityTrainingBinding binding;
    private TrainingPresenter presenter;
    private FragmentManager fragmentManager;

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, TrainingActivity.class);
    }

    // region lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_training);
        presenter = new TrainingPresenter();
        fragmentManager = getSupportFragmentManager();
        binding.setPresenter(presenter);
        setSupportActionBar(binding.toolbar);

        showTrainingList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
        fragmentManager.addOnBackStackChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachView();
        fragmentManager.removeOnBackStackChangedListener(this);
    }

    // endregion

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackStackChanged() {
        final Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
        // TODO: ここにロジックがあるのが若干気持ち悪い
        if (currentFragment instanceof ListFragment) {
            binding.fab.setVisibility(View.VISIBLE);
        } else {
            binding.fab.setVisibility(View.INVISIBLE);
        }
    }


    // region TrainingView

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showTrainingList() {
        if (fragmentManager.findFragmentByTag(ListFragment.TAG) == null) {
            fragmentManager.beginTransaction().replace(R.id.content, ListFragment.newInstance(), ListFragment.TAG).commit();
        }
    }

    @Override
    public void showAddView() {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(EditFragment.TAG);
        transaction.replace(R.id.content, EditFragment.newInstance(), EditFragment.TAG);
        transaction.commit();
    }

    @Override
    public void showEditView(@NonNull Memory memory) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(EditFragment.TAG);
        transaction.replace(R.id.content, EditFragment.newInstance(memory), EditFragment.TAG);
        transaction.commit();
    }

    // endregion
}

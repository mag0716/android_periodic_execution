package com.github.mag0716.memorytraining.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.ActivityTrainingBinding;
import com.github.mag0716.memorytraining.fragment.ListFragment;
import com.github.mag0716.memorytraining.presenter.TrainingPresenter;
import com.github.mag0716.memorytraining.view.TrainingView;

/**
 * 訓練画面
 */
public class TrainingActivity extends AppCompatActivity implements TrainingView {

    private ActivityTrainingBinding binding;
    private TrainingPresenter presenter;

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, TrainingActivity.class);
    }

    // region lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_training);
        presenter = new TrainingPresenter();
        binding.setPresenter(presenter);
        setSupportActionBar(binding.toolbar);

        showTrainingList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachView();
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

    // region TrainingView

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showTrainingList() {
        final FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentByTag(ListFragment.class.getCanonicalName()) == null) {
            manager.beginTransaction().replace(R.id.content, ListFragment.newInstance(), ListFragment.class.getCanonicalName()).commit();
        }
    }

    // endregion
}

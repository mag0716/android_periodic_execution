package com.github.mag0716.memorytraining.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.Observable.OnPropertyChangedCallback;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mag0716.memorytraining.BR;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.ActivityTrainingBinding;
import com.github.mag0716.memorytraining.fragment.EditFragment;
import com.github.mag0716.memorytraining.fragment.ListFragment;
import com.github.mag0716.memorytraining.presenter.TrainingPresenter;
import com.github.mag0716.memorytraining.view.TrainingView;
import com.github.mag0716.memorytraining.viewmodel.TrainingViewModel;

import timber.log.Timber;

/**
 * 訓練画面
 */
public class TrainingActivity extends AppCompatActivity
        implements TrainingView, FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener {

    private ActivityTrainingBinding binding;
    private TrainingPresenter presenter;
    private FragmentManager fragmentManager;
    private TrainingViewModel viewModel = new TrainingViewModel();

    private ActionBarDrawerToggle toggle;

    private final Observable.OnPropertyChangedCallback propertyChangedCallback = new OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            Timber.d("onPropertyChanged : %d", propertyId);
            if (propertyId == BR.selectableCategory) {
                setDrawerEnabled(viewModel.isSelectableCategory());
            } else if (propertyId == BR.currentCategory) {
                presenter.changeCategory(viewModel.getCurrentCategory());
            }
        }
    };

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, TrainingActivity.class);
    }

    // region lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_training);
        viewModel.addOnPropertyChangedCallback(propertyChangedCallback);
        presenter = new TrainingPresenter();
        fragmentManager = getSupportFragmentManager();
        binding.setViewModel(viewModel);
        binding.setPresenter(presenter);
        setSupportActionBar(binding.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.removeOnPropertyChangedCallback(propertyChangedCallback);
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
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            final FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() > 0) {
                manager.popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onBackStackChanged() {
        updateView();
    }

    // region NavigationView.OnNavigationItemSelectedListener

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        viewModel.changeCategory(menuItem.getItemId());
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // endregion

    // region TrainingView

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showTrainingList() {
        Timber.d("showTrainingList : %s", fragmentManager.findFragmentByTag(ListFragment.TAG));
        if (fragmentManager.findFragmentByTag(ListFragment.TAG) == null) {
            fragmentManager.beginTransaction().replace(R.id.content, ListFragment.newInstance(), ListFragment.TAG).commit();
        } else {
            updateView();
        }
    }

    @Override
    public void showAddView() {
        Timber.d("showAddView");
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(EditFragment.TAG);
        transaction.replace(R.id.content, EditFragment.newInstance(), EditFragment.TAG);
        transaction.commit();
    }

    @Override
    public void showEditView(long id) {
        Timber.d("showEditView : %d", id);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(EditFragment.TAG);
        transaction.replace(R.id.content, EditFragment.newInstance(id), EditFragment.TAG);
        transaction.commit();
    }

    // endregion

    /**
     * 表示状態を更新する
     */
    private void updateView() {
        final Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
        Timber.d("updateView : %s", currentFragment);
        viewModel.setSelectableCategory(currentFragment instanceof ListFragment);
        viewModel.setAddable(currentFragment instanceof ListFragment);
    }

    /**
     * Drawer の有効状態を変更する
     *
     * @param enabled true:有効、false:無効
     */
    private void setDrawerEnabled(boolean enabled) {
        Timber.d("setDrawerEnabled : %b", enabled);
        binding.drawerLayout.setDrawerLockMode(enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle.setDrawerIndicatorEnabled(enabled);
    }
}

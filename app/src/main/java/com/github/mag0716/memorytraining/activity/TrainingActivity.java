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
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.github.mag0716.memorytraining.BR;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.ActivityTrainingBinding;
import com.github.mag0716.memorytraining.databinding.DrawerHeaderBinding;
import com.github.mag0716.memorytraining.fragment.EditFragment;
import com.github.mag0716.memorytraining.fragment.ListFragment;
import com.github.mag0716.memorytraining.presenter.TrainingPresenter;
import com.github.mag0716.memorytraining.util.DeviceUtil;
import com.github.mag0716.memorytraining.view.TrainingView;
import com.github.mag0716.memorytraining.viewmodel.TrainingViewModel;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

import timber.log.Timber;

/**
 * 訓練画面
 */
public class TrainingActivity extends AppCompatActivity
        implements TrainingView, FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener {

    private ActivityTrainingBinding binding;
    private DrawerHeaderBinding drawerHeaderBinding;
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
        drawerHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.drawer_header, binding.drawerLayout, false);
        drawerHeaderBinding.setViewModel(viewModel);
        binding.navigationView.addHeaderView(drawerHeaderBinding.getRoot());
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

        if (savedInstanceState == null) {
            showTrainingList(viewModel.getCurrentCategory());
        } else {
            updateView();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        viewModel.calculateDrawerHeaderHeight(this, DeviceUtil.getStatusBarHeight(getWindow()));
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
        final int groupId = menuItem.getGroupId();
        final int menuId = menuItem.getItemId();
        if (groupId == R.id.training_category) {
            viewModel.changeCategory(menuId);
        } else {
            if (menuId == R.id.navigation_license) {
                startActivity(new Intent(this, OssLicensesMenuActivity.class));
            }
        }
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
    public void showTrainingList(int category) {
        final ListFragment fragment = (ListFragment) fragmentManager.findFragmentByTag(ListFragment.TAG);
        Timber.d("showTrainingList : %d, %s", category, fragment);
        fragmentManager.beginTransaction().replace(R.id.content, ListFragment.newInstance(category), ListFragment.TAG).commit();
        binding.fab.show();
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

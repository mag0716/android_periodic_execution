package com.github.mag0716.memorytraining.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.ActivityListBinding;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.presenter.ListPresenter;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.view.adapter.MemoryListAdapter;
import com.github.mag0716.memorytraining.view.decoration.CardItemDecoration;
import com.github.mag0716.memorytraining.viewmodel.ListViewModel;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 訓練対象データ一覧画面
 */
public class ListActivity extends AppCompatActivity {

    private ActivityListBinding binding;
    private ListViewModel viewModel = new ListViewModel();
    private ListPresenter presenter;

    private MemoryListAdapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        setSupportActionBar(binding.toolbar);

        presenter = new ListPresenter(((Application) getApplication()).getDatabase().memoryDao());
        binding.setPresenter(presenter);
        adapter = new MemoryListAdapter(this, presenter);
        binding.content.trainingList.setLayoutManager(new LinearLayoutManager(this));
        itemDecoration = new CardItemDecoration(this);
        binding.content.trainingList.addItemDecoration(itemDecoration);
        binding.content.trainingList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: Presenter に委譲
        final MemoryDao memoryDao = ((Application) getApplication()).getDatabase().memoryDao();
        Single.create((SingleOnSubscribe<List<Memory>>) emitter -> emitter.onSuccess(memoryDao.loadAll()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memoryList -> {
                    viewModel.addAll(memoryList);
                    adapter.addAll(viewModel.getItemViewModelList());
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.content.trainingList.removeItemDecoration(itemDecoration);
    }

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
}

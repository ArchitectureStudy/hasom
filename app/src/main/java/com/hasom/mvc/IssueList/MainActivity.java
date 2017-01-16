package com.hasom.mvc.IssueList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hasom.mvc.IssueDetail.IssueDetailActivity;
import com.hasom.mvc.IssueList.adapter.IssueListAdapter;
import com.hasom.mvc.IssueList.presenter.ListPresenter;
import com.hasom.mvc.IssueList.presenter.ListPresenterImpl;
import com.hasom.mvc.R;
import com.hasom.mvc.util.Define;
import com.hasom.mvc.util.MessageDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create by junho.lee
 */
public class MainActivity extends AppCompatActivity implements ListPresenter.View{

    @BindView(R.id.listView)
    RecyclerView listView;

    private LinearLayoutManager mLayoutManager;
    private IssueListAdapter mAdapter;

    private ListPresenterImpl listPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new IssueListAdapter(this);
        listView.setAdapter(mAdapter);

        listPresenter = new ListPresenterImpl();
        listPresenter.attachView(this);
        listPresenter.setIssueListAdapterModel(mAdapter);
        listPresenter.setIssueListAdapterView(mAdapter);

        listPresenter.loadIsueList(this);

        listPresenter.checkSaveAccToken(getApplicationContext());

    }

    @Override
    public void moveToDetailActivity(int issueNum) {
        Intent intent = new Intent(this, IssueDetailActivity.class);
        intent.putExtra(Define.INTENT_SEND_ISSUENUM, issueNum);
        startActivity(intent);

    }

    @Override
    public void showInputToken() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MessageDialog dialog = new MessageDialog(MainActivity.this, listPresenter);
                dialog.show();
            }
        });
    }
}

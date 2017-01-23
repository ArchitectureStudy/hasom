package com.hasom.mvc.IssueList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.hasom.mvc.CreateIssue.CreateIssueActivity;
import com.hasom.mvc.IssueDetail.IssueDetailActivity;
import com.hasom.mvc.IssueList.adapter.IssueListAdapter;
import com.hasom.mvc.IssueList.presenter.ListPresenter;
import com.hasom.mvc.IssueList.presenter.ListPresenterImpl;
import com.hasom.mvc.R;
import com.hasom.mvc.base.util.Define;
import com.hasom.mvc.base.util.MessageDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create by junho.lee
 */
public class MainActivity extends AppCompatActivity implements ListPresenter.View {

    @BindView(R.id.listView)
    RecyclerView listView;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private LinearLayoutManager mLayoutManager;
    private IssueListAdapter mAdapter;

    private ListPresenterImpl listPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        init();
    }

    private void init() {

        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.addOnScrollListener(recyclerViewOnScrollListener);

        mAdapter = new IssueListAdapter(this);
        listView.setAdapter(mAdapter);

        listPresenter = new ListPresenterImpl();
        listPresenter.attachView(this);
        listPresenter.setIssueListAdapterModel(mAdapter);
        listPresenter.setIssueListAdapterView(mAdapter);

        listPresenter.loadIsueList();

        listPresenter.checkSaveAccToken(getApplicationContext());

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listPresenter.loadIsueList();
                listPresenter.clearListData();
            }
        });
    }

    /**
     * Check List More Load
     */
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            listPresenter.checkListViewPositionBottom(visibleItemCount, totalItemCount, firstVisibleItemPosition);
        }
    };

    @Override
    public void moveToDetailActivity(int issueNum) {

        Intent intent = new Intent(this, IssueDetailActivity.class);
        intent.putExtra(Define.INTENT_SEND_ISSUENUM, issueNum);
        startActivityForResult(intent, Define.REQ_ACTIVITY_ISSUE_DETAIL);

    }

    @OnClick(R.id.ivCreateIssue)
    public void OnClickCreateIssue() {
        Intent intent = new Intent(this, CreateIssueActivity.class);
        startActivityForResult(intent, Define.REQ_ACTIVITY_ISSUE_CREATE);
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

    @Override
    public void stopRefreshData() {
        refreshLayout.setRefreshing(false);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("", "onActivityResult requestCode = " + requestCode + " | resultCode = " + resultCode);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Define.REQ_ACTIVITY_ISSUE_DETAIL:
                    if (data != null) {
                        int issueNum = data.getIntExtra(Define.INTENT_SEND_ISSUENUM, 0);
                        listPresenter.updateIssueItem(issueNum);
                    }
                    break;
                case Define.REQ_ACTIVITY_ISSUE_CREATE:
                    listPresenter.clearListData();
                    listPresenter.loadIsueList();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listPresenter.detachView();
        listPresenter = null;
    }

}

package com.hasom.mvc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hasom.mvc.adapter.IssueListAdapter;
import com.hasom.mvc.model.IssueDTO;
import com.hasom.mvc.model.IssueModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create by junho.lee
 */
public class MainActivity extends AppCompatActivity implements IssueModel.ModelDataChange {

    @BindView(R.id.listView)
    RecyclerView listView;
    private LinearLayoutManager mLayoutManager;
    private IssueListAdapter mAdapter;

    private IssueModel model;

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

        model = new IssueModel();
        model.setOnChangeListener(this);
        model.callIssueList();

    }

    @Override
    public void update(List<IssueDTO> list) {
        mAdapter.setListData(list);
    }
}

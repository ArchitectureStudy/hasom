package com.hasom.mvc.IssueList.presenter;

import android.content.Context;

import com.hasom.mvc.IssueList.adapter.contract.IssueListAdapterContract;
import com.hasom.mvc.IssueList.model.IssueDTO;
import com.hasom.mvc.IssueList.model.IssueModel;
import com.hasom.mvc.listener.OnItemClickListener;
import com.hasom.mvc.util.SharedPreferenceUtil;

import java.util.List;

/**
 * Created by leejunho on 2017. 1. 12..
 */

public class ListPresenterImpl implements ListPresenter.Presenter, IssueModel.ModelDataChange, OnItemClickListener {

    private ListPresenter.View view;

    private IssueListAdapterContract.Model adapterModel;
    private IssueListAdapterContract.View adapterView;

    private IssueModel issueModel;

    @Override
    public void attachView(ListPresenter.View view) {
        this.view = view;
        issueModel = new IssueModel();
        issueModel.setOnChangeListener(this);
    }

    @Override
    public void detachView() {
        view = null;
        issueModel = null;
    }

    @Override
    public void setIssueListAdapterModel(IssueListAdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void setIssueListAdapterView(IssueListAdapterContract.View adapterView) {
        this.adapterView = adapterView;
        this.adapterView.setOnItemClickListener(this);
    }

    @Override
    public void loadIsueList(Context context) {
        issueModel.callIssueList();
    }

    @Override
    public void checkSaveAccToken(Context context) {
        String accToken = SharedPreferenceUtil.getInstance(context).getToken();

        if (accToken.length() == 0) {
            view.showInputToken();
        }
    }

    @Override
    public void saveAcctoken(Context context, String token) {
        SharedPreferenceUtil.getInstance(context).setToken(token);
    }


    @Override
    public void update(List<IssueDTO> list) {
        List<IssueDTO> issueDTOs = list;
        adapterModel.setListData(issueDTOs);
        adapterView.notifytAdapter();
    }

    @Override
    public void onItemClick(int issueNum) {
        view.moveToDetailActivity(issueNum);
    }
}

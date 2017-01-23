package com.hasom.mvc.IssueList.presenter;

import android.content.Context;

import com.hasom.mvc.IssueDetail.model.IssueDetailDTO;
import com.hasom.mvc.IssueDetail.model.IssueDetailModel;
import com.hasom.mvc.IssueList.adapter.contract.IssueListAdapterContract;
import com.hasom.mvc.IssueList.model.IssueDTO;
import com.hasom.mvc.IssueList.model.IssueModel;
import com.hasom.mvc.base.listener.OnItemClickListener;
import com.hasom.mvc.base.util.SharedPreferenceUtil;

import java.util.List;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * Created by leejunho on 2017. 1. 12..
 */

public class ListPresenterImpl implements ListPresenter.Presenter, IssueModel.ModelDataChange, OnItemClickListener, IssueDetailModel.DetailModelDataChange {

    private ListPresenter.View view;

    private IssueListAdapterContract.Model adapterModel;
    private IssueListAdapterContract.View adapterView;

    private IssueModel issueModel;
    private IssueDetailModel issueDetailModel;

    // Pager Variables
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int currentPage = 1;

    @Override
    public void attachView(ListPresenter.View view) {
        this.view = view;
        issueModel = new IssueModel();
        issueModel.setOnChangeListener(this);

        issueDetailModel = new IssueDetailModel();
        issueDetailModel.setOnChangeListener(this);
    }

    @Override
    public void detachView() {
        view = null;
        issueModel.setOnChangeListener(null);
        issueModel = null;

        issueDetailModel.setOnChangeListener(null);
        issueDetailModel = null;
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
    public void loadIsueList() {
        isLoading = true;
        issueModel.callIssueList(currentPage);
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
    public void checkListViewPositionBottom(int visibleItemCount, int totalItemCount, int firstVisibleItemPosition) {
        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                currentPage = currentPage + 1;
                loadIsueList();
            }
        }
    }

    @Override
    public void clearListData() {
        adapterModel.clearListData();
    }

    @Override
    public void updateIssueItem(int issueNum) {
        if (issueNum > 0) {
            issueDetailModel.callIssueDetail(issueNum);
        }
    }

    @Override
    public void onSuccess(List<IssueDTO> list) {
        isLoading = false;
        view.stopRefreshData();

        if (list == null || list.size() == 0) {
            isLastPage = true;
            return;
        }
        List<IssueDTO> issueDTOs = list;

        adapterModel.setListData(issueDTOs);
        adapterView.notifytAdapter();
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onItemClick(int issueNum) {
        view.moveToDetailActivity(issueNum);
    }

    @Override
    public void update(IssueDetailDTO model) {
        adapterModel.updateCommentCount(model.getNumber(), model.getComments());
    }


}

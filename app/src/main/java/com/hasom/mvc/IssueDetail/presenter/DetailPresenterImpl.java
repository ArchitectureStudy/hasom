package com.hasom.mvc.IssueDetail.presenter;

import com.hasom.mvc.IssueDetail.adapter.contract.IssueCommentAdapterContract;
import com.hasom.mvc.IssueDetail.model.IssueCommentDTO;
import com.hasom.mvc.IssueDetail.model.IssueCommentModel;
import com.hasom.mvc.IssueDetail.model.IssueDetailDTO;
import com.hasom.mvc.IssueDetail.model.IssueDetailModel;

import java.util.List;

/**
 * Created by leejunho on 2017. 1. 14..
 */

public class DetailPresenterImpl implements DetailPresenter.Presenter, IssueDetailModel.DetailModelDataChange, IssueCommentModel.CommentModelDataChange {

    private DetailPresenter.View view;
    private IssueDetailModel issueDetailModel;
    private IssueCommentModel issueCommentModel;

    private IssueCommentAdapterContract.Model adapterModel;
    private IssueCommentAdapterContract.View adapterView;


    private boolean isRefresh = false;


    @Override
    public void attachView(DetailPresenter.View view) {

        this.view = view;
        issueDetailModel = new IssueDetailModel();
        issueDetailModel.setOnChangeListener(this);

        issueCommentModel = new IssueCommentModel();
        issueCommentModel.setOnChangeListener(this);

    }

    @Override
    public void detachView() {
        view = null;

        issueDetailModel.setOnChangeListener(null);
        issueDetailModel = null;

        issueCommentModel.setOnChangeListener(null);
        issueCommentModel = null;
    }

    @Override
    public void setIssueCommentAdapterModel(IssueCommentAdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void setIssueCommentAdapterView(IssueCommentAdapterContract.View adapterView) {
        this.adapterView = adapterView;
    }


    private void createIssueComment(int issueNum, String token, String comment) {
        if (issueCommentModel == null) {
            return;
        }

        issueCommentModel.createIssueComment(issueNum, token, comment);
    }

    @Override
    public void sendIssueComment(int issueNum, String token, String comment) {
        view.hideKeyPad();

        if (comment.length() == 0) {
            return;
        }

        if (token.length() == 0) {
            view.showToast();
        }

        createIssueComment(issueNum, token, comment);

        view.clearEditText();
    }

    @Override
    public boolean checkRefreshData() {
        return isRefresh;
    }


    @Override
    public void loadIsueDetail(int issueNum) {
        if (issueDetailModel == null) {
            return;
        }
        issueDetailModel.callIssueDetail(issueNum);
    }


    @Override
    public void loadIsueComment(int issueNum) {
        if (issueCommentModel == null) {
            return;
        }
        issueCommentModel.callIssueComment(issueNum);

    }



    @Override
    public void update(IssueDetailDTO model) {
        if (model == null) {
            return;
        }

        if (model.getUser() != null) {
            if (model.getUser().getAvatar_url().length() > 0) {
                view.updateProfile(model.getUser().getAvatar_url());
            }

            if (model.getUser().getLogin().length() > 0) {
                view.updateUserName(model.getUser().getLogin());
            }
        }

        if (model.getTitle().length() > 0) {
            view.updateIssueDetail(model.getTitle());
        }

        if (model.getBody().length() > 0) {
            view.updateBody(model.getBody());
        }
    }

    @Override
    public void update(List<IssueCommentDTO> model) {
        if (adapterModel != null && model != null && adapterView != null) {
            adapterModel.setListData(model);
            adapterView.notifytAdapter();
        }
    }

    @Override
    public void createIssueComment(IssueCommentDTO issueComment) {
        if (adapterModel != null && issueComment != null && adapterView != null) {
            adapterModel.addListData(issueComment);
            adapterView.notifytAdapter();
            isRefresh = true;
        }
    }
}

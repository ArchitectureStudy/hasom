package com.hasom.mvc.IssueDetail.presenter;

import com.hasom.mvc.IssueDetail.adapter.contract.IssueCommentAdapterContract;
import com.hasom.mvc.IssueDetail.model.IssueCommentDTO;
import com.hasom.mvc.IssueDetail.model.IssueCommentModel;
import com.hasom.mvc.IssueDetail.model.IssueDetailDTO;
import com.hasom.mvc.IssueDetail.model.IssueDetailModel;
import com.hasom.mvc.base.GithubApplication;
import com.hasom.mvc.base.Observer.CustomObserver;

import java.util.List;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * Created by leejunho on 2017. 1. 14..
 */

public class DetailPresenterImpl implements DetailPresenter.Presenter, IssueDetailModel.DetailModelDataChange, IssueCommentModel.CommentModelDataChange, CustomObserver {

    private DetailPresenter.View view;
    private IssueDetailModel issueDetailModel;
    private IssueCommentModel issueCommentModel;

    private IssueCommentAdapterContract.Model adapterModel;
    private IssueCommentAdapterContract.View adapterView;


    private boolean isRefresh = false;
    // Pager Variables
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int currentPage = 1;

    @Override
    public void attachView(DetailPresenter.View view) {

        this.view = view;
        issueDetailModel = new IssueDetailModel();
        issueDetailModel.setOnChangeListener(this);

        issueCommentModel = new IssueCommentModel();
        issueCommentModel.setOnChangeListener(this);

        GithubApplication.getGithubApplication().addObserver(this);

    }

    @Override
    public void detachView() {
        view = null;

        issueDetailModel.setOnChangeListener(null);
        issueDetailModel = null;

        issueCommentModel.setOnChangeListener(null);
        issueCommentModel = null;

        GithubApplication.getGithubApplication().deleteObserver(this);
    }

    @Override
    public void checkListViewPositionBottom(int issueNum, int visibleItemCount, int totalItemCount, int firstVisibleItemPosition) {
        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                currentPage = currentPage + 1;
                loadIsueComment(issueNum);
            }
        }
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
        issueCommentModel.callIssueComment(issueNum, currentPage);

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
    public void update(List<IssueCommentDTO> list) {
        if (adapterModel != null && list != null && adapterView != null) {
            isLoading = false;

            if (list == null || list.size() == 0) {
                isLastPage = true;
                return;
            }

            adapterModel.setListData(list);
            adapterView.notifytAdapter();
        }
    }

    @Override
    public void createIssueComment(IssueCommentDTO issueComment) {
        if (adapterModel != null && issueComment != null && adapterView != null) {
            adapterModel.addListData(issueComment);
            adapterView.notifytAdapter();
            isRefresh = true;

            IssueDetailDTO tmpDTO = issueDetailModel.getIssueDetailDTO();
            tmpDTO.setComments(tmpDTO.getComments() + 1);

            GithubApplication.getGithubApplication().changeModel(tmpDTO);
        }
    }

    @Override
    public void update(Object obj) {

    }
}

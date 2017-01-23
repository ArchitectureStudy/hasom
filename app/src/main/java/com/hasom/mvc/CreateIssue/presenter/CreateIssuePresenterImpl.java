package com.hasom.mvc.CreateIssue.presenter;

import android.content.Context;

import com.hasom.mvc.CreateIssue.model.CreateIssueDTO;
import com.hasom.mvc.CreateIssue.model.CreateIssueModel;
import com.hasom.mvc.base.util.SharedPreferenceUtil;

/**
 * Created by leejunho on 2017. 1. 12..
 */

public class CreateIssuePresenterImpl implements CreateIssuePresenter.Presenter, CreateIssueModel.ModelDataChange {

    private CreateIssuePresenter.View view;

    private CreateIssueModel createIssueModel;

    String accessToken = null;

    @Override
    public void attachView(CreateIssuePresenter.View view) {
        this.view = view;
        createIssueModel = new CreateIssueModel();
        createIssueModel.setOnChangeListener(this);
    }

    @Override
    public void detachView() {
        view = null;
        createIssueModel.setOnChangeListener(null);
        createIssueModel = null;

    }

    @Override
    public void createIssue(Context context, String title, String body) {

        if (title.isEmpty() == true) {
            view.showToast("Please Input Issue Title");
            return;
        }

        if (accessToken == null) {
            accessToken = SharedPreferenceUtil.getInstance(context).getToken();
        }

        createIssueModel.callCreateIssue(accessToken, title, body);

    }




    @Override
    public void onSuccess(CreateIssueDTO model) {
        view.CreateIssueOnSuccess();
    }

    @Override
    public void onFail() {
        view.CreateIssueOnFail();
    }



}

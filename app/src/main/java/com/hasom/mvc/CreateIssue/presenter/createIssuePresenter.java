package com.hasom.mvc.CreateIssue.presenter;

import android.content.Context;

/**
 * Created by leejunho on 2017. 1. 12..
 */

public interface CreateIssuePresenter {

    interface View {
        void CreateIssueOnSuccess();
        void CreateIssueOnFail();
        void showToast(String msg);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void createIssue(Context context, String title, String body);
    }
}

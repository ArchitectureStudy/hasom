package com.hasom.mvc.IssueList.presenter;

import android.content.Context;
import com.hasom.mvc.IssueList.adapter.contract.IssueListAdapterContract;

/**
 * Created by leejunho on 2017. 1. 12..
 */

public interface ListPresenter {

    interface View {
        void moveToDetailActivity(int issueNum);
        void showInputToken();
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void setIssueListAdapterModel(IssueListAdapterContract.Model adapterModel);

        void setIssueListAdapterView(IssueListAdapterContract.View adapterView);

        void loadIsueList(Context context);

        void checkSaveAccToken(Context context);

        void saveAcctoken(Context context, String token);
    }
}

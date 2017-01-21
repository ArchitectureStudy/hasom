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
        void stopRefreshData();
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void setIssueListAdapterModel(IssueListAdapterContract.Model adapterModel);

        void setIssueListAdapterView(IssueListAdapterContract.View adapterView);

        void loadIsueList();

        void checkSaveAccToken(Context context);

        void saveAcctoken(Context context, String token);

        void checkListViewPositionBottom(int visibleItemCount, int totalItemCount, int firstVisibleItemPosition);

        void clearListData();

        void updateIssueItem(int issueNum);
    }
}

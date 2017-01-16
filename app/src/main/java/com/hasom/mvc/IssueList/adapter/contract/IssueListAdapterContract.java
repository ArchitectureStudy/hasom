package com.hasom.mvc.IssueList.adapter.contract;


import com.hasom.mvc.IssueList.model.IssueDTO;
import com.hasom.mvc.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by leejunho on 2017. 1. 12..
 */

public interface IssueListAdapterContract {

    interface View {
        void notifytAdapter();

        void setOnItemClickListener(OnItemClickListener listener);
    }

    interface Model {
        void setListData(List<IssueDTO> listItem);

    }
}

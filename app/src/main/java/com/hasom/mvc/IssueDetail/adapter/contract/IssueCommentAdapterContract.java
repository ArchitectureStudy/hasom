package com.hasom.mvc.IssueDetail.adapter.contract;


import com.hasom.mvc.IssueDetail.model.IssueCommentDTO;

import java.util.List;

/**
 * Created by leejunho on 2017. 1. 12..
 */

public interface IssueCommentAdapterContract {

    interface View {
        void notifytAdapter();

    }

    interface Model {
        void setListData(List<IssueCommentDTO> listItem);
        void addListData(IssueCommentDTO item);
    }
}

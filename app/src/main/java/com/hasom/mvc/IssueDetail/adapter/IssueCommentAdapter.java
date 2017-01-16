package com.hasom.mvc.IssueDetail.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hasom.mvc.IssueDetail.adapter.contract.IssueCommentAdapterContract;
import com.hasom.mvc.IssueDetail.adapter.holder.IssueCommentViewHolder;
import com.hasom.mvc.IssueDetail.model.IssueCommentDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leejunho on 2017. 1. 7..
 */
public class IssueCommentAdapter extends RecyclerView.Adapter<IssueCommentViewHolder> implements IssueCommentAdapterContract.Model, IssueCommentAdapterContract.View {

    private Activity mAct;

    private List<IssueCommentDTO> list = new ArrayList<IssueCommentDTO>();

    public IssueCommentAdapter(Activity act) {
        super();
        this.mAct = act;

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public void setListData(List<IssueCommentDTO> listItem) {
        this.list = listItem;
    }

    @Override
    public void addListData(IssueCommentDTO item) {
        this.list.add(item);
    }

    @Override
    public void notifytAdapter() {
        notifyDataSetChanged();
    }


    @Override
    public IssueCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IssueCommentViewHolder(mAct, parent);

    }

    @Override
    public void onBindViewHolder(IssueCommentViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        holder.onBind(list.get(position), position);
    }


    private View.OnClickListener playerClickListener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.playerClickListener = listener;
    }


}
package com.hasom.mvc.IssueList.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hasom.mvc.IssueList.adapter.contract.IssueListAdapterContract;
import com.hasom.mvc.IssueList.adapter.holder.IssueListViewHolder;
import com.hasom.mvc.IssueList.model.IssueDTO;
import com.hasom.mvc.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leejunho on 2017. 1. 7..
 */
public class IssueListAdapter extends RecyclerView.Adapter<IssueListViewHolder> implements IssueListAdapterContract.Model, IssueListAdapterContract.View {

    private Activity mAct;
    private OnItemClickListener onItemClickListener;

    private List<IssueDTO> list = new ArrayList<IssueDTO>();

    public IssueListAdapter(Activity act) {
        super();
        this.mAct = act;

    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public void setListData(List<IssueDTO> listItem) {
        this.list = listItem;
    }

    @Override
    public void notifytAdapter() {
        notifyDataSetChanged();
    }


    @Override
    public IssueListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IssueListViewHolder(mAct, parent, onItemClickListener);

    }

    @Override
    public void onBindViewHolder(IssueListViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        holder.onBind(list.get(position), position);
    }

}
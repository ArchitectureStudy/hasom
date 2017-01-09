package com.hasom.mvc.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hasom.mvc.R;
import com.hasom.mvc.model.IssueDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leejunho on 2017. 1. 7..
 */
public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.ViewHolder> {

    private Activity mAct;

    private List<IssueDTO> list = new ArrayList<IssueDTO>();
    private long mLastClickTime = 0;

    public IssueListAdapter(Activity act) {
        super();
        this.mAct = act;

    }

    /**
     * @param list
     */
    public void setListData(List<IssueDTO> list) {

       this.list = list;
        notifyDataSetChanged();
    }

    public IssueDTO getListItem(int pos) {
        return this.list.get(pos);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvIssueNum)
        TextView tvIssueNum = null;

        @BindView(R.id.tvIssueTitle)
        TextView tvIssueTitle = null;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_issue, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        IssueDTO info = list.get(position);

        try {

            vh.tvIssueNum.setText("" + info.getId());

            vh.tvIssueTitle.setText("" + info.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private View.OnClickListener playerClickListener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.playerClickListener = listener;
    }


}
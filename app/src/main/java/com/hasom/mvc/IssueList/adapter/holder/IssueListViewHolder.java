package com.hasom.mvc.IssueList.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasom.mvc.IssueList.model.IssueDTO;
import com.hasom.mvc.R;
import com.hasom.mvc.listener.OnItemClickListener;
import com.hasom.mvc.util.RoundedConrnerTransformation;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leejunho on 2017. 1. 12..
 */
public class IssueListViewHolder extends RecyclerView.ViewHolder {

    private Context mCtx;

    private OnItemClickListener onItemCLickListener;

    @BindView(R.id.tvIssueNum)
    TextView tvIssueNum;

    @BindView(R.id.tvIssueTitle)
    TextView tvIssueTitle;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.tvCommentCount)
    TextView tvCommentCount;


    public IssueListViewHolder(Context ctx, ViewGroup parent, OnItemClickListener listener) {
        super(LayoutInflater.from(ctx).inflate(R.layout.row_issue, parent, false));

        this.mCtx = ctx;
        this.onItemCLickListener = listener;

        ButterKnife.bind(this, itemView);

    }

    public void onBind(final IssueDTO data, final int position) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemCLickListener != null) {
                    onItemCLickListener.onItemClick(data.getNumber());
                }
            }
        });


        try {

            tvIssueNum.setText("" + data.getNumber());
            tvIssueTitle.setText("" + data.getTitle());
            tvUserName.setText("" + data.getUser().getLogin());
            tvCommentCount.setText("Comments : " + data.getComments());

            Picasso.with(mCtx)// Context
                    .load(data.getUser().getAvatar_url())// URL
                    .transform(new RoundedConrnerTransformation())
                    .into(ivProfile);// View


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

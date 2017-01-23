package com.hasom.mvc.IssueDetail.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasom.mvc.IssueDetail.model.IssueCommentDTO;
import com.hasom.mvc.R;
import com.hasom.mvc.base.util.RoundedConrnerTransformation;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leejunho on 2017. 1. 12..
 */
public class IssueCommentViewHolder extends RecyclerView.ViewHolder {

    private Context mCtx;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.tvComment)
    TextView tvComment;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    public IssueCommentViewHolder(Context ctx, ViewGroup parent) {
        super(LayoutInflater.from(ctx).inflate(R.layout.row_comment_other, parent, false));

        this.mCtx = ctx;

        ButterKnife.bind(this, itemView);

    }

    public void onBind(IssueCommentDTO data, final int position) {


        try {

            tvUserName.setText("" + data.getUser().getLogin());
            tvComment.setText("" + data.getBody());


            Picasso.with(mCtx)// Context
                    .load(data.getUser().getAvatar_url())// URL
                    .transform(new RoundedConrnerTransformation())
                    .into(ivProfile);// View


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

package com.hasom.mvc.IssueDetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hasom.mvc.IssueDetail.adapter.IssueCommentAdapter;
import com.hasom.mvc.IssueDetail.presenter.DetailPresenter;
import com.hasom.mvc.IssueDetail.presenter.DetailPresenterImpl;
import com.hasom.mvc.R;
import com.hasom.mvc.base.util.Define;
import com.hasom.mvc.base.util.RoundedConrnerTransformation;
import com.hasom.mvc.base.util.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by leejunho on 2017. 1. 12..
 */

public class IssueDetailActivity extends Activity implements DetailPresenter.View{

    @BindView(R.id.listView)
    RecyclerView listView;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.tvIssueTitle)
    TextView tvIssueTitle;

    @BindView(R.id.tvBody)
    TextView tvBody;

    @BindView(R.id.edComment)
    EditText edComment;

    private LinearLayoutManager mLayoutManager;
    private IssueCommentAdapter mAdapter;

    private DetailPresenterImpl detailPresenter;

    private InputMethodManager imm = null;

    private int issueNum = 0;
    private String accessToken = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_issue_detail);

        ButterKnife.bind(this);

        init();

    }

    private void init() {

        Intent intent = getIntent();

        if (intent.hasExtra(Define.INTENT_SEND_ISSUENUM) == true) {
            issueNum = intent.getIntExtra(Define.INTENT_SEND_ISSUENUM, 0);
        }

        if (issueNum == 0) {
            Toast.makeText(this, "이슈번호가 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        accessToken = SharedPreferenceUtil.getInstance(this).getToken();

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new IssueCommentAdapter(this);
        listView.setAdapter(mAdapter);
        listView.addOnScrollListener(recyclerViewOnScrollListener);

        detailPresenter = new DetailPresenterImpl();
        detailPresenter.attachView(this);
        detailPresenter.setIssueCommentAdapterModel(mAdapter);
        detailPresenter.setIssueCommentAdapterView(mAdapter);

        detailPresenter.loadIsueDetail(issueNum);
        detailPresenter.loadIsueComment(issueNum);
    }

    /**
     * Check List More Load
     */
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            detailPresenter.checkListViewPositionBottom(issueNum, visibleItemCount, totalItemCount, firstVisibleItemPosition);
        }
    };

    @Override
    public void updateProfile(String url) {
        Picasso.with(this)// Context
                .load(url)// URL
                .transform(new RoundedConrnerTransformation())
                .into(ivProfile);// View
    }

    @Override
    public void updateUserName(final String name) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tvUserName == null) {
                    return;
                }
                tvUserName.setText(name);
            }
        });
    }

    @Override
    public void updateIssueDetail(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tvIssueTitle == null) {
                    return;
                }
                tvIssueTitle.setText(text);
            }
        });
    }

    @Override
    public void updateBody(final String body) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tvIssueTitle == null) {
                    return;
                }
                tvBody.setText(body);
            }
        });
    }

    @Override
    public void hideKeyPad() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imm.hideSoftInputFromWindow(edComment.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void clearEditText() {
        edComment.setText("");
    }

    @OnClick(R.id.btnSend)
    public void btnSend() {
        String sendComment = edComment.getText().toString().trim();

        detailPresenter.sendIssueComment(issueNum, accessToken, sendComment);

    }

    public void showToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(IssueDetailActivity.this, "Check Your AccessTokem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.ivBack)
    public void OnClickBack() {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        if (detailPresenter.checkRefreshData() == true) {
            Intent intent = new Intent();
            intent.putExtra(Define.INTENT_SEND_ISSUENUM, issueNum);
            setResult(Activity.RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.detachView();
        detailPresenter = null;
    }
}

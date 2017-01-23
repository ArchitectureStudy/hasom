package com.hasom.mvc.CreateIssue;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.hasom.mvc.CreateIssue.presenter.CreateIssuePresenter;
import com.hasom.mvc.CreateIssue.presenter.CreateIssuePresenterImpl;
import com.hasom.mvc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by leejunho on 2017. 1. 23..
 */

public class CreateIssueActivity extends Activity implements CreateIssuePresenter.View{


    @BindView(R.id.edTitle)
    EditText edTitle;

    @BindView(R.id.edBody)
    EditText edBody;

    private CreateIssuePresenterImpl createIssuePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_create_issue);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

        createIssuePresenterImpl = new CreateIssuePresenterImpl();
        createIssuePresenterImpl.attachView(this);
    }

    @OnClick(R.id.ivBack)
    public void onClickBack() {

        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.ivSave)
    public void onClickSave() {
        String title = edTitle.getText().toString().trim();
        String body = edBody.getText().toString().trim();

        createIssuePresenterImpl.createIssue(this, title, body);

    }

    @Override
    public void CreateIssueOnSuccess() {
        showToast("Success Register Issue");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void CreateIssueOnFail() {
        showToast("Fail CreateIssue");
    }

    @Override
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CreateIssueActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

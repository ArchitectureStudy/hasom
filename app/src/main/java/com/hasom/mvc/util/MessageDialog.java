package com.hasom.mvc.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.hasom.mvc.IssueList.presenter.ListPresenterImpl;
import com.hasom.mvc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author leejunho
 *
 */
public class MessageDialog extends Dialog {

	private Activity mAct;
	private ListPresenterImpl presenter;

	@BindView(R.id.edToken)
	EditText edToken;

	public MessageDialog(Activity act, ListPresenterImpl presenter) {
		super(act);

		this.mAct = act;
		this.presenter = presenter;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		// 화면을 dimm하는 함수
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.dialog_save_token);

		ButterKnife.bind(this);

		setCanceledOnTouchOutside(true);
	}

	@OnClick(R.id.btnSave)
	public void btnSave() {
		String token = edToken.getText().toString().trim();
		presenter.saveAcctoken(mAct, token);
		dismiss();

	}



}

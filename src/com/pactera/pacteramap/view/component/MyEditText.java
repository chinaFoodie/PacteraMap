/**
 * 
 */
package com.pactera.pacteramap.view.component;

import android.content.Context;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.util.StringUtil;

/**
 * @author cWX223941
 *
 */
public class MyEditText extends LinearLayout {
	private TextView hintText;
	private EditText editText;
	private LinearLayout itemBlock;

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(getContext()).inflate(R.layout.custom_edittext,
				this);
		hintText = (TextView) findViewById(R.id.et_custom_hint);
		editText = (EditText) findViewById(R.id.et_custom_edit);
		itemBlock = (LinearLayout) findViewById(R.id.layout_custom_et_block);
	}

	public void setInputPassword() {
		editText.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}

	public void setTextSize(float size) {
		editText.setTextSize(size);
	}

	public void setSelection(int len) {
		editText.setSelection(len);
	}

	public void setHintText(int resId) {
		hintText.setText(resId);
	}

	public String getText() {
		return editText.getText().toString();
	}

	public void setText(String speedOuterSite) {
		editText.setText(speedOuterSite);
	}

	/**
	 * forbid user input any thing
	 * */
	public void forbidInput() {
		editText.setEnabled(false);
	}

	public void allowInput() {
		editText.setEnabled(true);
	}

	public void setEditFocusChangeListener(
			OnFocusChangeListener focusChangeListener) {
		editText.setOnFocusChangeListener(focusChangeListener);
	}

	public void setTextChangedListener(TextWatcher textWatcher) {
		editText.addTextChangedListener(textWatcher);
	}

	public boolean hasContent() {
		if (!StringUtil.isEmpty(editText.getText().toString())) {
			return true;
		}
		return false;
	}

	public void setItemBlockBackground(int resId) {
		itemBlock.setBackgroundResource(resId);
	}

	public void hiddenHintText() {
		hintText.setVisibility(View.GONE);
	}
}

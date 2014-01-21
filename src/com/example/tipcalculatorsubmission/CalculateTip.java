package com.example.tipcalculatorsubmission;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculateTip extends Activity {

	EditText amount, tipOther;
	TextView tipValue;
	Button tip10, tip15, tip20, clearAll;
	double tip_value = 0.0; 
	double pre_amount = 0.0;

	private static String TAG = "TIP CALCULATOR";

	private void calculateTip(String tip) {
		if ((!tipOther.getText().toString().equals("")) && (!amount.getText().toString().equals(""))){

			pre_amount = Double.parseDouble(amount.getText().toString());

			if (pre_amount == 0){
				 Toast.makeText(this, "Please enter Pre-Tip Amount", Toast.LENGTH_LONG).show();
			}
			else {
				tip_value = (pre_amount / 100)*(Double.parseDouble(tip));

				// limit tip value to 2 decimal (round off) by default Math.round returns long
				tip_value = Math.round(tip_value*100)/100.00;
				tipValue.setText((CharSequence)Double.toString(tip_value));
			}
		}
		else {
			tipValue.setText((CharSequence)"");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculate_tip);

		tipValue = (TextView) findViewById(R.id.tv_tipValue);
		amount = (EditText) findViewById(R.id.et_tipAmt);
		tip10 = (Button) findViewById(R.id.b_tip10);
		tip15 = (Button) findViewById(R.id.b_tip15);
		tip20 = (Button) findViewById(R.id.b_tip20);
		clearAll = (Button) findViewById(R.id.b_clear);
		tipOther = (EditText) findViewById(R.id.et_otherTip);
		
		amount.requestFocus();
		
		setupTipClickListener();
		setupOtherTipEnterListener();
		setupAmtChangeListener();
		setupClearClickListener();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculate_tip, menu);
		return true;
	}

	private void setupAmtChangeListener() {
		amount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				calculateTip(tipOther.getText().toString());
			}
		});
	}

	private void setupClearClickListener() {
		View.OnClickListener clearAllHandler = new View.OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "Clear ALL is clicked");
				amount.setText("");
				tipValue.setText("");
				tipOther.setText("");
				amount.requestFocus();
			}
		};

		clearAll.setOnClickListener(clearAllHandler);
	}

	private void setupOtherTipEnterListener() {

		tipOther.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.d(TAG, "ON TXT CHG CharSeq s: " +s + " and : " + s.toString() + " start: " + start + "before: "+before + " count: "+count);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				Log.d(TAG, "BEFORE TXT CHG CharSeq s: " +s + " and : " + s.toString() + " start: " + start + "after: "+after + " count: "+count);
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!s.toString().equals("")){
					if (Double.parseDouble(s.toString()) > 100.00 || Double.parseDouble(s.toString()) < 0.00){
						// show it is not valid value
						 // Toast.makeText(this, "Please enter Pre-Tip Amount", Toast.LENGTH_LONG).show();
						s.replace(0, s.toString().length(), "");
					} else {
						// s is basically value in edit text
						calculateTip(s.toString());
					}
				} else {
					tipValue.setText((CharSequence)"");
				}
			}
		});

	}

	private void setupTipClickListener() {
		View.OnClickListener tipHandler = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Pre-tip amount is : "+amount.getText());

				if (!amount.getText().toString().equals("")){
					pre_amount = Double.parseDouble(amount.getText().toString());

					if (pre_amount == 0){
						// Toast.makeText(this, "Please enter Pre-Tip Amount", Toast.LENGTH_LONG).show();
					}

					else {
						TextView tip = (TextView) findViewById(v.getId());
						String tip_percentage = tip.getText().toString();
						tip_percentage = tip_percentage.substring(0, tip_percentage.indexOf("%"));

						tip_value = (pre_amount / 100)*(Double.parseDouble(tip_percentage));
						// limit tip value to 2 decimal (round off) by default Math.round returns long
						tip_value = Math.round(tip_value*100)/100.00;

						tipValue.setText((CharSequence)Double.toString(tip_value));
					}
				} else {
					tipValue.setText((CharSequence)"");
				}
			}
		};

		tip10.setOnClickListener(tipHandler);
		tip15.setOnClickListener(tipHandler);
		tip20.setOnClickListener(tipHandler);
	}
}

package com.pabloojeda.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView mCalculatorDisplay;
	private Boolean userIsInTheMiddleOfTypingANumber = false;
	private static final String DIGITS = "0123456789.";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mCalculatorDisplay = (TextView) findViewById(R.id.textView1);
		
		findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
 
        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSubtract).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);
        findViewById(R.id.buttonToggleSign).setOnClickListener(this);
        findViewById(R.id.buttonDecimalPoint).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);
        
 
        // The following buttons only exist in layout-land (Landscape mode) and require extra attention.
        // The messier option is to place the buttons in the regular layout too and set android:visibility="invisible".
        //if (findViewById(R.id.buttonSquareRoot) != null) {
        //    findViewById(R.id.buttonSquareRoot).setOnClickListener(this);
        //}
        if (findViewById(R.id.buttonSquared) != null) {
            findViewById(R.id.buttonSquared).setOnClickListener(this);
        }
        //if (findViewById(R.id.buttonInvert) != null) {
        //    findViewById(R.id.buttonInvert).setOnClickListener(this);
        //}
        if (findViewById(R.id.buttonSine) != null) {
            findViewById(R.id.buttonSine).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonCosine) != null) {
            findViewById(R.id.buttonCosine).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonTangent) != null) {
            findViewById(R.id.buttonTangent).setOnClickListener(this);
        }
	}
	
	@Override
	public void onClick(View v){
		String buttonPressed = ((Button) v).getText().toString();
		 
        if (DIGITS.contains(buttonPressed)) {
 
            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {
 
                if (buttonPressed.equals(".") && mCalculatorDisplay.getText().toString().contains(".")) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
                } else {
                    mCalculatorDisplay.append(buttonPressed);
                }
 
            } else {
 
                if (buttonPressed.equals(".")) {
                    // ERROR PREVENTION
                    // This will avoid error if only the decimal is hit before an operator, by placing a leading zero
                    // before the decimal
                    mCalculatorDisplay.setText(0 + buttonPressed);
                } else {
                	if (mCalculatorDisplay.getText().toString().equals("0")){
                		mCalculatorDisplay.setText(buttonPressed);
                	}
                	else {
                		mCalculatorDisplay.append(buttonPressed);
                	}
                }
 
                userIsInTheMiddleOfTypingANumber = true;
            }
 
        } else {
        	if (buttonPressed.equals("C")){
        		mCalculatorDisplay.setText("0");
        	}
        	else {
        		// operation was pressed
        		if (userIsInTheMiddleOfTypingANumber) {
 
        			// mCalculatorBrain.setOperand(Double.parseDouble(mCalculatorDisplay.getText().toString()));
        			mCalculatorDisplay.append(" " + buttonPressed + " ");
        			userIsInTheMiddleOfTypingANumber = false;
        		}
        	}
 
            //mCalculatorBrain.performOperation(buttonPressed);
            //mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
 
        }
 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save variables on screen orientation change
        savedInstanceState.putString("OPERAND", mCalculatorDisplay.getText().toString());
        savedInstanceState.putBoolean("userIsInTheMiddleOfTypingANumber", userIsInTheMiddleOfTypingANumber);
    }
 
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorDisplay.setText(savedInstanceState.getString("OPERAND"));
        userIsInTheMiddleOfTypingANumber = savedInstanceState.getBoolean("userIsInTheMiddleOfTypingANumber");
    }

}

package com.pabloojeda.calculator;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/* 
 * Development by Pablo E. Ojeda Vasco for @speakinbytes
 * 
 * This project is about a Calculator. It has been done to illustrate the use of the
 * methods onSaveInstanceState and onRestoreInstanceState when the screen is rotated and
 * the activity is destroyed and created again. We have two screens, portrait and landscape.
 * 
 * The calculator works easily:
 *  -    4 + 2 = 6 -> "C"
 *  -    4 + 2 - 2 = 4 + 4 = 8 * 2 = 16 -> "C"
 *  -    4 + 2 * 2 = 12 ((4+2)*6), NOT 4 + (2*2)
 * 
 * The steps for the calculator are:
 *  - saving in a pile the values of the numbers
 *  - saving in a pile the operations
 *  - When the user touch "=", we call the method brainCalculator.
 *  --- brainCalculator select the params and do the corrects operations.
 */

public class MainActivity extends Activity implements OnClickListener {

	private TextView mCalculatorDisplay;
	private Boolean userIsInTheMiddleOfTypingANumber = false;
	private static final String DIGITS = "0123456789.";
	
	ArrayList<String> pilaOp = new ArrayList<String>();
    ArrayList<String> pilaNum = new ArrayList<String>();
    String value = ""; // Last value inserted completely, not parcially
    
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
		 
		// If I touch a digit
        if (DIGITS.contains(buttonPressed)) {
 
            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) { 
                if (buttonPressed.equals(".") && mCalculatorDisplay.getText().toString().contains(".")) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
                } else {
                    mCalculatorDisplay.append(buttonPressed);
                    value = value + buttonPressed;
                } 
            } else { 
                if (buttonPressed.equals(".")) {
                    // ERROR PREVENTION
                    // This will avoid error if only the decimal is hit before an operator, by placing a leading zero
                    // before the decimal
                    mCalculatorDisplay.setText(0 + buttonPressed);
                    value = 0 + buttonPressed;
                } else {
                	if (mCalculatorDisplay.getText().toString().equals("0")){
                		mCalculatorDisplay.setText(buttonPressed);
                	}
                	else {
                		mCalculatorDisplay.append(buttonPressed);
                	}
                	value = buttonPressed;
                } 
                userIsInTheMiddleOfTypingANumber = true;
            }
            
        } else { // If I touch other thing
        	if (buttonPressed.equals("C")){
        		mCalculatorDisplay.setText("0");
        		value = "";
        		pilaNum.clear();
        		pilaOp.clear();
        		userIsInTheMiddleOfTypingANumber = false;
        	}
        	else {
        		
        		if (buttonPressed.equals("=")){
        			if (!value.equals("")){
        				pilaNum.add(value);
        				mCalculatorDisplay.setText(brainCalculator());
        			}
        			else{
        				// Not is possilbe do 4 + =
        			}
        		}else{
        			// operation was pressed
        			if (userIsInTheMiddleOfTypingANumber) {
        				
        				pilaNum.add(value);
        				pilaOp.add(buttonPressed);
        				value = "";        				
        				
        				mCalculatorDisplay.append(" " + buttonPressed + " ");
        				userIsInTheMiddleOfTypingANumber = false;
        			}
        		}
        	}
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
        savedInstanceState.putString("VALUE", value);
        savedInstanceState.putStringArrayList("PILANUM", pilaNum);
        savedInstanceState.putStringArrayList("PILAOP", pilaOp);
    }
 
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorDisplay.setText(savedInstanceState.getString("OPERAND"));
        userIsInTheMiddleOfTypingANumber = savedInstanceState.getBoolean("userIsInTheMiddleOfTypingANumber");
        value = savedInstanceState.getString("VALUE");
        pilaNum = savedInstanceState.getStringArrayList("PILANUM");
        pilaOp = savedInstanceState.getStringArrayList("PILAOP");
    }
    
    /* This method is the Brain of the calculator
     * It take the values of the pilaNum and the operators of the pilaOp
     * It works:
     * -   4 + 2 * 7 + 6
     * -   temp = 4
     * -   temp = 4 + 2 = 6
     * -   temp = 6 * 7 = 42
     * -   temp = 42 + 6 = 48 
     * */
    public String brainCalculator() {
		String mResult = "";
		float temp;
    	
		if(pilaOp.size() >= 1 && pilaNum.size() >= 2){
			temp = Float.parseFloat(pilaNum.get(0));
    		for (int i = 0; i < pilaNum.size() - 1; i++){
    			if(pilaOp.get(i).equals("+")){
        			//Suma
        			temp = temp + Float.parseFloat(pilaNum.get(i+1));
        		}
    			if(pilaOp.get(i).equals("-")){
        			// Resta
    				temp = temp - Float.parseFloat(pilaNum.get(i+1));
        		}
    			if(pilaOp.get(i).equals("*")){
        			// Multiplicaci—n
    				temp = temp * Float.parseFloat(pilaNum.get(i+1));
        		}
    			if(pilaOp.get(i).equals("/")){
        			// Divisi—n
    				temp = temp / Float.parseFloat(pilaNum.get(i+1));
        		}
    		}
    		pilaNum.clear();
    		pilaOp.clear();
    		
    		mResult = Float.toString(temp);
    		value = mResult;
    	}
				
    	return mResult;    	
    }

}

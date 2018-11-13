package com.example.a10012482.calculatorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Calculator extends AppCompatActivity implements View.OnClickListener {

    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonAdd, buttonSubtract, buttonMultiply, buttonDivide, buttonEqual, buttonC;
    TextView viewBar;
    String input = "";
    ArrayList<String> al = new ArrayList<>();
    public static final String errorMsg = "Error";
    DecimalFormat decimalForm = new DecimalFormat("#.#####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonSubtract = (Button) findViewById(R.id.buttonSubtract);
        buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonEqual = (Button) findViewById(R.id.buttonEqual);
        buttonC = (Button) findViewById(R.id.buttonC);
        viewBar = (TextView) findViewById(R.id.viewBar);
        viewBar.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (input.equals("0"))
            input = " ";
        input += ((Button) view).getText();
        if (view.equals(buttonC)) {
            input = "0";
        }
        if (view.equals(buttonEqual)) {
            //Last value is equal sign, so removing it while computing
            input = input.substring(0, input.length() - 1);
            StringTokenizer compute = new StringTokenizer(input, "+-*/", true);
            while (compute.hasMoreTokens())
                al.add(compute.nextToken());
            int alTokens = al.size();
            String result = "";
            if (alTokens == 0)
                result = "0";
            else {
                String temp = al.get(0);
                //Checks if first value is an operator
                if (isOperator(temp))
                    result = errorMsg;
                else {
                    //If only one number is entered, it is the result
                    if (alTokens == 1)
                        result = temp;
                    else {
                        //Checks if last value is an operator
                        if (isOperator(al.get(al.size() - 1)))
                            result = errorMsg;
                        else {
                            for (int i = 1; i < al.size() - 1; i++) {
                                //Checks for double operator
                                if (isOperator(al.get(i)) && isOperator(al.get(i + 1))) {
                                    result = errorMsg;
                                    break;
                                }
                                //Checks for division by zero
                                String str2 = al.get(i+1);
                                while (str2.length() > 1) {
                                    if (str2.startsWith("0")) {
                                        str2=str2.substring(1);
                                    } else {
                                        break;
                                    }
                                }
                                //if (isDivOperator(al.get(i)) && al.get(i + 1).equals("0")) {
                                if (isDivOperator(al.get(i)) && (str2.equals("0"))) {
                                    result = errorMsg;
                                    break;
                                }
                            }
                            double resultValue = 0.0;
                            double valOne;
                            double valTwo;
                            if (result.equals("")) {
                                for (int i = 0; i < al.size(); i++) {
                                    temp = al.get(i);
                                    if (isMultOperator(temp) || isDivOperator(temp)) {
                                        valOne = Double.parseDouble(al.get(i - 1));
                                        valTwo = Double.parseDouble(al.get(i + 1));
                                        if (isMultOperator(temp))
                                            resultValue = valOne * valTwo;
                                        else
                                            resultValue = valOne / valTwo;
                                        resultValue = Double.valueOf(decimalForm.format(resultValue));
                                        result = resultValue + "";
                                        al.remove(i + 1);
                                        al.remove(i);
                                        al.set(i - 1, result);
                                        i--;
                                    }
                                }
                                for (int i = 0; i < al.size(); i++) {
                                    if (al.size() == 1)
                                        result = al.get(0);
                                    else {
                                        temp = al.get(i);
                                        if (isAddOperator(temp) || isSubOperator(temp)) {
                                            valOne = Double.parseDouble(al.get(i-1));
                                            valTwo = Double.parseDouble(al.get(i+1));
                                            if (isAddOperator(temp))
                                                resultValue = valOne + valTwo;
                                            else
                                                resultValue = valOne - valTwo;
                                            resultValue = Double.valueOf(decimalForm.format(resultValue));
                                            result = resultValue + "";
                                            al.remove(i+1);
                                            al.remove(i);
                                            al.set(i-1, result);
                                            i--;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            input = result;
            al.clear();
        }

        viewBar.setText(input);
    }

    private boolean isOperator(String s) {
        if ((isAddOperator(s)) || (isSubOperator(s)) || (isMultOperator(s)) || (isDivOperator(s)))
            return true;
        return false;
    }

    private boolean isAddOperator(String s) {
        if ("+".equals(s))
            return true;
        return false;
    }

    private boolean isSubOperator(String s) {
        if ("-".equals(s))
            return true;
        return false;
    }

    private boolean isMultOperator(String s) {
        if ("*".equals(s))
            return true;
        return false;
    }

    private boolean isDivOperator(String s) {
        if ("/".equals(s))
            return true;
        return false;
    }
}





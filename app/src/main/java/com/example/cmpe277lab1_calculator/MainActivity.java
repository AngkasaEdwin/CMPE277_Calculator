package com.example.cmpe277lab1_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView displayTextView;

    private String value1_str;
    private double value1;
    private String value2_str;
    private double value2;

    enum Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        NONE
    }
    private Operation currentOperation;

    enum State {
        ENTER_VALUE1,
        ENTER_VALUE2,
        ERROR
    }
    private State currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBindings();
        displayTextView = findViewById(R.id.textView2);

        currentState = State.ENTER_VALUE1;
        currentOperation = Operation.NONE;
        value1_str = "";
        value1 = 0;
        value2_str = "";
        value2 = 0;
        displayTextView.setText("0");
    }

    private void setBindings(){
        findViewById(R.id.button0).setOnClickListener(v -> onClickNumber(0));
        findViewById(R.id.button1).setOnClickListener(v -> onClickNumber(1));
        findViewById(R.id.button2).setOnClickListener(v -> onClickNumber(2));
        findViewById(R.id.button3).setOnClickListener(v -> onClickNumber(3));
        findViewById(R.id.button4).setOnClickListener(v -> onClickNumber(4));
        findViewById(R.id.button5).setOnClickListener(v -> onClickNumber(5));
        findViewById(R.id.button6).setOnClickListener(v -> onClickNumber(6));
        findViewById(R.id.button7).setOnClickListener(v -> onClickNumber(7));
        findViewById(R.id.button8).setOnClickListener(v -> onClickNumber(8));
        findViewById(R.id.button9).setOnClickListener(v -> onClickNumber(9));
        findViewById(R.id.button_clear).setOnClickListener(v -> onClickClear());
        findViewById(R.id.button_plus).setOnClickListener(v -> onClickPlus());
        findViewById(R.id.button_minus).setOnClickListener(v -> onClickMinus());
        findViewById(R.id.button_mult).setOnClickListener(v -> onClickMult());
        findViewById(R.id.button_dot).setOnClickListener(v -> onClickDot());
        findViewById(R.id.button_equal).setOnClickListener(v -> onClickEqual());
    }

    private void onClickNumber(int number){
        switch (currentState){
            case ERROR:
                currentState = State.ENTER_VALUE1;
            case ENTER_VALUE1:
                if(value1_str.equals("0"))
                    value1_str = String.valueOf(number);
                else
                    value1_str += number;
                break;
            case ENTER_VALUE2:
                if(value2_str.equals("0"))
                    value2_str = String.valueOf(number);
                else
                    value2_str += number;
                break;
        }
        updateScreenDisplay();
    }

    private void onClickClear(){
        resetValues();
        currentState = State.ENTER_VALUE1;
        updateScreenDisplay();
    }

    private void onClickPlus(){
        switch (currentState){
            case ENTER_VALUE1:
                if(!value1_str.isEmpty()){
                    try{
                        value1 = Double.parseDouble(value1_str);
                        currentOperation = Operation.ADD;
                        currentState = State.ENTER_VALUE2;
                    }catch(Exception e){
                        errorState();
                    }
                }
                break;
            case ENTER_VALUE2:
                if(!value2_str.isEmpty()){
                    try{
                        value2 = Double.parseDouble(value2_str);
                        calculateAndDisplayCurrentOperation();
                        currentOperation = Operation.ADD;
                    }catch(Exception e){
                        errorState();
                    }
                }
                break;
            case ERROR:
                currentState = State.ENTER_VALUE1;
                value1_str = "0";
                displayTextView.setText(value1_str);
                break;
        }
    }

    private void onClickMinus(){
        switch (currentState){
            case ENTER_VALUE1:
                if(!value1_str.isEmpty()){
                    try{
                        value1 = Double.parseDouble(value1_str);
                        currentOperation = Operation.SUBTRACT;
                        currentState = State.ENTER_VALUE2;
                    }catch(Exception e){
                        errorState();
                    }
                }
                break;
            case ENTER_VALUE2:
                if(!value2_str.isEmpty()){
                    try{
                        value2 = Double.parseDouble(value2_str);
                        calculateAndDisplayCurrentOperation();
                        currentOperation = Operation.SUBTRACT;
                    }catch(Exception e){
                        errorState();
                    }
                }
                break;
            case ERROR:
                currentState = State.ENTER_VALUE1;
                value1_str = "0";
                displayTextView.setText(value1_str);
                break;
        }
    }

    private void onClickMult(){
        switch (currentState){
            case ENTER_VALUE1:
                if(!value1_str.isEmpty()){
                    try{
                        value1 = Double.parseDouble(value1_str);
                        currentOperation = Operation.MULTIPLY;
                        currentState = State.ENTER_VALUE2;
                    }catch(Exception e){
                        errorState();
                    }
                }
                break;
            case ENTER_VALUE2:
                if(!value2_str.isEmpty()){
                    try{
                        value2 = Double.parseDouble(value2_str);
                        calculateAndDisplayCurrentOperation();
                        currentOperation = Operation.MULTIPLY;
                    }catch(Exception e){
                        errorState();
                    }
                }
                break;
            case ERROR:
                currentState = State.ENTER_VALUE1;
                value1_str = "0";
                displayTextView.setText(value1_str);
                break;
        }
    }

    private void onClickDot(){
        switch (currentState){
            case ERROR:
                currentState = State.ENTER_VALUE1;
            case ENTER_VALUE1:
                value1_str += ".";
                break;
            case ENTER_VALUE2:
                value2_str += ".";
                break;
        }
    }

    private void onClickEqual(){
        switch (currentState){
            case ERROR:
                currentState = State.ENTER_VALUE1;
                updateScreenDisplay();
                break;
            case ENTER_VALUE1:
                break;
            case ENTER_VALUE2:
                calculateAndDisplayCurrentOperation();
                break;
        }
    }

    private void errorState(){
        resetValues();
        currentState = State.ERROR;
        displayTextView.setText("ERROR");
    }

    private void resetValues(){
        value1_str = "0";
        value1 = 0;
        value2_str = "0";
        value2 = 0;
        currentOperation = Operation.NONE;
    }

    private void calculateAndDisplayCurrentOperation(){
        switch(currentOperation){
            case NONE:
                currentState = State.ENTER_VALUE1;
                updateScreenDisplay();
                break;
            case ADD:
                currentOperation = Operation.NONE;
                displayCalculatedValue(Double.parseDouble(value1_str) + Double.parseDouble(value2_str));
                break;
            case SUBTRACT:
                currentOperation = Operation.NONE;
                displayCalculatedValue(Double.parseDouble(value1_str) - Double.parseDouble(value2_str));
                break;
            case MULTIPLY:
                currentOperation = Operation.NONE;
                displayCalculatedValue(Double.parseDouble(value1_str) * Double.parseDouble(value2_str));
                break;
        }
    }

    private void displayCalculatedValue(double value){
        value1 = value;
        value2 = 0;
        value1_str = String.valueOf(value);
        value2_str = "0";
        displayTextView.setText(value1_str);
    }

    private void updateScreenDisplay(){
        switch (currentState){
            case ERROR:
                displayTextView.setText("ERROR");
                break;
            case ENTER_VALUE1:
                displayTextView.setText(value1_str);
                break;
            case ENTER_VALUE2:
                displayTextView.setText(value2_str);
                break;
        }
    }
}
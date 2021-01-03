package edu.ib.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvEquation;
    private TextView tvScore;

    private boolean isOperatorClick = false;
    private boolean isLastOperationEqual = false;
    private boolean isStart = true;
    private boolean isNumberClick = false;

    private String score;
    private String equation;

    Calculator calculator = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton delete = (ImageButton) findViewById(R.id.btnBack);
        delete.setOnClickListener(view -> deleteNumber());

        tvEquation = (TextView)findViewById(R.id.tvEquation);
        tvScore = (TextView) findViewById(R.id.tvScore);

        if (savedInstanceState != null) {
            score = savedInstanceState.getString("score");
            equation = savedInstanceState.getString("equation");
            tvEquation.setText(equation);
            tvScore.setText(score);
        }

    }

    private void deleteNumber() {
        score = tvScore.getText().toString();
        String newProgressNumber = score.substring(0, score.length()-1);
        if(newProgressNumber.isEmpty()){
            tvScore.setText("0");
        }
        calculator.setProgressNumber(newProgressNumber);
        tvScore.setText(calculator.getProgressNumber());
    }

    public void onClickNumber(View view) {
        isNumberClick = true;
        if (isStart) {
            tvScore.setText("");
            isStart = false;
        }
        else if (isOperatorClick) {
            tvScore.setText("");
            isOperatorClick = false;
        }

        Button button = (Button) view;
        score = button.getText().toString();
        this.calculator.concatProgressNumber(score);
        tvScore.setText(this.calculator.getProgressNumber());
        isLastOperationEqual = false;
        System.out.println(score);
    }

    public void onClickOperator(View view) {
        if (isNumberClick){
            this.isOperatorClick = true;
            Button button = (Button) view;
            score = button.getText().toString();
            Operation operation = Operation.fromString(score);
            this.calculator.addProgressNumber(tvScore.getText().toString());
            this.calculator.addOperations(operation);
            equation = this.calculator.getCalculationInfo();
            this.tvEquation.setText(equation);
            isLastOperationEqual = false;
        }else{
            tvScore.setText("0");
        }
    }

    public void onClickSimplyOperation(View view) {
        if(isNumberClick){
            Button button = (Button) view;
            score = button.getText().toString();
            Operation operation = Operation.fromString(score);
            this.calculator.executeOperation(operation);
            this.tvScore.setText(this.calculator.getProgressNumber());
        }else{
            tvScore.setText("0");
        }
    }

    public void onClickEquals(View view) {
        if(isLastOperationEqual){
            this.calculator.initialWithLastOperation();
        }
        else{
            this.calculator.addProgressNumber(tvScore.getText().toString());
        }

        this.calculator.addOperations(Operation.EQU);
        equation = this.calculator.getCalculationInfo();
        tvEquation.setText(equation);
        Double calculateResult = this.calculator.calculate();
        score = Converter.getStringCalculateValue(calculateResult);
        tvScore.setText(score);

        calculator.clearCalculation();
        isLastOperationEqual = true;
    }

    public void onClickClearAll(View view) {
        this.calculator = new Calculator();
        tvEquation.setText("");
        setZeroScore();
        isNumberClick=false;
    }

    public void onClickClearLast(View view) {
        this.calculator.setProgressNumber("");
        setZeroScore();
    }

    private void setZeroScore(){
        this.tvScore.setText("0");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("score", score);
        savedInstanceState.putString("equation", equation);
    }
}

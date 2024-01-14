package com.example.codersforum;
// QuizActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CQuizActivity extends AppCompatActivity {

    private String currentSubject;
    private int currentQuestionIndex = 0;
    private int score = 0;
    Button btnSubmit;

    private String[] questions = {
            "Which of the following is the correct syntax to declare an array in C?",
            "What is the purpose of the 'scanf' function in C?",
            "In C, how is memory allocated for a dynamic array?",
            "What is the output of the code snippet: 'printf('%d', sizeof('a'))'?",
            "Which keyword is used to exit from a loop in C?",
            "What is the role of the 'typedef' keyword in C?",
            "What is the purpose of the '#include' directive in C?",
            "In C, what is the difference between '++i' and 'i++'?",
            "What is the purpose of the 'break' statement in C?",
            "What is the output of the code snippet: 'int x = 5; printf('%d', x++ + ++x);'?"
    };

    private String[][] options = {
            {"int arr[10];", "array arr[10];", "arr[] = new int[10];", "int arr = [10];"},
            {"To print output to the console", "To read input from the console", "To allocate memory", "To declare a variable"},
            {"Using 'malloc' function", "By declaring it with 'dynamic' keyword", "Automatically allocated by the compiler", "Using 'new' keyword"},
            {"4", "1", "8", "Error"},
            {"terminate", "exit", "break", "return"},
            {"To define a new data type", "To declare a variable", "To include a header file", "To allocate memory"},
            {"To import external functions", "To include a standard library", "To include header files", "To define macros"},
            {"Both increment the value of 'i' by 1", "'++i' increments 'i' and then returns its value, 'i++' returns the value and then increments it", "'++i' returns the value and then increments it, 'i++' increments 'i' and then returns its value", "There is no difference"},
            {"To exit the loop immediately", "To skip the next iteration and continue with the loop", "To skip the current iteration and continue with the loop", "To terminate the program"},
            {"10", "12", "11", "Error"}
    };

    private int[] correctAnswers = {0, 1, 0, 2, 2, 0, 2, 1, 2, 2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cquiz);

        // currentSubject = getIntent().getStringExtra("SUBJECT");

        TextView tvQuestion = findViewById(R.id.tvQuestion);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        btnSubmit = findViewById(R.id.btnSubmit);

        displayQuestion(tvQuestion, radioGroup);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = radioGroup.getCheckedRadioButtonId();

                if (selectedOptionId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedOptionId);
                    int selectedOptionIndex = radioGroup.indexOfChild(selectedRadioButton);

                    checkAnswer(selectedOptionIndex);

                    if (currentQuestionIndex < questions.length - 1) {
                        currentQuestionIndex++;
                        displayQuestion(tvQuestion, radioGroup);
                    } else {
                        showResult();
                    }

                } else {
                    Toast.makeText(CQuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayQuestion(TextView tvQuestion, RadioGroup radioGroup) {
        tvQuestion.setText(questions[currentQuestionIndex]);
        if (currentQuestionIndex==9)
        {

            btnSubmit.setText("Submit");
        }
        for (int i = 0; i < options[currentQuestionIndex].length; i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setText(options[currentQuestionIndex][i]);
        }

        radioGroup.clearCheck();
    }

    private void checkAnswer(int selectedOptionIndex) {
        if (selectedOptionIndex == correctAnswers[currentQuestionIndex]) {
            score++;
        }
    }

    private void showResult() {
        Toast.makeText(this, "Quiz Completed!\nYour Score: " + score + " out of " + questions.length, Toast.LENGTH_LONG).show();
        // You can also display the correct answers or navigate to a different activity to show detailed results.
        Intent intent=new Intent(CQuizActivity.this,WelcomeActivity.class);
        startActivity(intent);
    }
}

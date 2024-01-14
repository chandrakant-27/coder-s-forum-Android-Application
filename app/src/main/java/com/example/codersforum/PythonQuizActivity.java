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

public class PythonQuizActivity extends AppCompatActivity {

    private String currentSubject;
    private int currentQuestionIndex = 0;
    private int score = 0;
    Button btnSubmit;

    // QuizActivity.java

// ...

    private String[] questions = {
            "What is the purpose of the 'self' keyword in Python?",
            "Which of the following is used to create a function in Python?",
            "What is the output of the code snippet: 'print('Python'[::-1])'?",
            "What is the difference between '==' and 'is' in Python?",
            "Which of the following data structures is mutable in Python?",
            "What is the purpose of the 'lambda' keyword in Python?",
            "How do you open a file named 'example.txt' in read mode in Python?",
            "What is the purpose of the 'pass' statement in Python?",
            "What is the output of the code snippet: 'print(2 ** 3)'?",
            "What does the 'import' statement do in Python?"
    };

    private String[][] options = {
            {"To represent the instance of the class", "To call a method from another class", "To create a static variable", "All of the above"},
            {"func", "def", "function", "define"},
            {"'nohtyP'", "'Python'", "'Pnohty'", "Error"},
            {"'==' is used for object reference comparison, 'is' is used for content comparison", "'is' is used for object reference comparison, '==' is used for content comparison", "'==' and 'is' are equivalent in Python", "None of the above"},
            {"List", "Tuple", "Set", "String"},
            {"To define anonymous functions", "To create a new class", "To handle exceptions", "To iterate over a sequence"},
            {"file = open('example.txt', 'r')", "file = open('example.txt')", "file = read('example.txt')", "file = read_file('example.txt')"},
            {"To create an infinite loop", "To indicate that nothing should happen", "To pass the control to the next iteration", "To create a placeholder for future code"},
            {"8", "6", "16", "Error"},
            {"Imports a module or a specific function from a module", "Exports a module", "Defines a class in Python", "Executes the main block of code"}
    };

    private int[] correctAnswers = {0, 1, 0, 1, 2, 0, 0, 1, 2, 0};

// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_python_quiz);

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
                    Toast.makeText(PythonQuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(PythonQuizActivity.this,WelcomeActivity.class);
        startActivity(intent);
    }
}

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

public class CppQuizActivity extends AppCompatActivity {

    private String currentSubject;
    private int currentQuestionIndex = 0;
    private int score = 0;
    Button btnSubmit;

    private String[] questions = {
            "Which of the following is the correct syntax to declare a class in C++?",
            "What is the purpose of the 'new' keyword in C++?",
            "What is the output of the code snippet: 'cout << sizeof(3.14)' in C++?",
            "In C++, what is the difference between 'cin' and 'getline'?",
            "What is the role of the 'const' keyword in C++?",
            "Which operator is used for dynamic memory allocation in C++?",
            "What is the purpose of the 'virtual' keyword in C++?",
            "In C++, how do you achieve function overloading?",
            "What is the output of the code snippet: 'int x = 5; cout << ++x + x++' in C++?",
            "What is the purpose of the 'namespace' keyword in C++?"
    };

    private String[][] options = {
            {"class MyClass { };", "MyClass : class { };", "class = MyClass { };", "MyClass class { };"},
            {"To allocate memory for an array", "To create a new instance of a class", "To allocate memory on the heap", "To declare a variable"},
            {"4", "8", "2", "Error"},
            {"'cin' is used for input of strings, 'getline' is used for input of numeric values", "'cin' is used for input of numeric values, 'getline' is used for input of strings", "'cin' is used for input of both strings and numeric values, 'getline' is used for input of strings", "'cin' and 'getline' are equivalent"},
            {"To indicate that a variable cannot be modified", "To define a constant value", "To allocate memory dynamically", "To create an object of a class"},
            {"'alloc'", "'malloc'", "'new'", "'allocate'"},
            {"To indicate that a function can be overridden in derived classes", "To declare a function in a base class", "To allocate memory for a function", "To define a pure virtual function"},
            {"By defining multiple functions with the same name but different parameter lists", "By defining a function inside another function", "By defining a function with a different return type", "Function overloading is not possible in C++"},
            {"11", "10", "12", "Error"},
            {"To define a class in C++", "To declare variables in C++", "To organize code into separate scopes", "To specify the standard C++ library"}
    };

    private int[] correctAnswers = {0, 2, 1, 3, 1, 2, 0, 0, 1, 2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpp_quiz);

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
                    Toast.makeText(CppQuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(CppQuizActivity.this,WelcomeActivity.class);
        startActivity(intent);
    }
}

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

public class JavaQuizActivity extends AppCompatActivity {

    private String currentSubject;
    private int currentQuestionIndex = 0;
    private int score = 0;
    Button btnSubmit;

    private String[] questions = {
            "What is the purpose of the 'final' keyword in Java?",
            "Which of the following is NOT a primitive data type in Java?",
            "What is the default value of a local variable in Java?",
            "What is the Java Virtual Machine (JVM) responsible for?",
            "What is the difference between '==' and '.equals()' in Java?",
            "What is the purpose of the 'static' keyword in Java?",
            "How is an interface different from an abstract class in Java?",
            "What is the purpose of the 'super' keyword in Java?",
            "What is the output of the code snippet: 'System.out.println(1 + '2' + 3);'?",
            "What is the purpose of the 'try', 'catch', and 'finally' blocks in exception handling?"
    };

    private String[][] options =
            {
            {"To indicate that a variable's value cannot be changed", "To declare a constant", "To define a class as unextendable", "All of the above"},
            {"boolean", "char", "string", "float"},
            {"0", "null", "undefined", "It depends on the type of the variable"},
            {"To compile Java source code", "To execute Java bytecode", "To provide a runtime environment for Java programs", "To manage memory allocation for Java programs"},
            {"'==' is used for object reference comparison, '.equals()' is used for content comparison", "'.equals()' is used for object reference comparison, '==' is used for content comparison", "'==' is used for both object reference and content comparison", "'.equals()' is used for both object reference and content comparison"},
            {"To indicate that a method belongs to the class rather than an instance of the class", "To indicate that a variable is shared among all instances of the class", "To indicate that a class cannot be instantiated", "All of the above"},
            {"An interface can have default method implementations, while an abstract class cannot", "An abstract class can have constructors, while an interface cannot", "An interface can have multiple inheritance, while an abstract class cannot", "An abstract class can have final methods, while an interface cannot"},
            {"To refer to the immediate parent class object", "To call a method from the parent class", "To invoke the parent class constructor", "To access static members of the parent class"},
            {"123", "6", "15", "Error"},
            {"To handle runtime errors", "To try out new code", "To define custom exceptions", "To handle compile-time errors"}
    };

    private int[] correctAnswers = {0, 2, 1, 2, 0, 3, 2, 0, 3, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_quiz);

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
                    Toast.makeText(JavaQuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(JavaQuizActivity.this,WelcomeActivity.class);
        startActivity(intent);
    }
}

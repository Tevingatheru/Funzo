package com.learner.funzo.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.learner.funzo.R
import com.learner.funzo.model.Question
import com.learner.funzo.model.QuestionType
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.viewModel.QuizActivityViewModel
import com.learner.funzo.viewModel.constant.ScoreConstants


class ExamActivity : AppCompatActivity() {

    private val viewModel: QuizActivityViewModel by viewModels()
    private lateinit var navController: NavController

    companion object {
        const val examKey = "exam"
        private const val TAG = "ExamActivity"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.example_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item1 -> {
                FirebaseUtil.logout(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        setContentView(R.layout.fragment_multiple_choice)
        setContentView(R.layout.fragment_open_ended_question)
        setContentView(R.layout.fragment_open_ended_question)

        viewModel.setExam(intent.getParcelableExtra(examKey)!!)
        ScoreConstants.resetScore(viewModel.getTotalNoOfQuestions(), viewModel.getThreshold())
        viewModel.setExamActivity(this)
        val question: Question = this.getCurrentQuestion()

        when (question.questionType) {
//            QuestionType.TRUE_FALSE -> {
//                setTFQuestion(question)
//            }
            QuestionType.MULTIPLE_CHOICE -> {
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

                navController = navHostFragment.navController
                navController.navigate(resId = R.id.multipleChoiceFragment)

                initMCQView()
                viewModel.setMCQuestion(question)
            }
//            QuestionType.OPEN_ENDED -> {
//                setOEQuestion(question)
//            }
            else -> {
                Toast.makeText(this, "No display for this type of question", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun initMCQView() {
        viewModel.initMCQQuestion(optionA = findViewById<TextView>(R.id.mcqOptionA),
            optionB = findViewById<TextView>(R.id.mcqOptionB),
            optionC = findViewById<TextView>(R.id.mcqOptionC),
            optionD = findViewById<TextView>(R.id.mcqOptionD),
            submitButton = findViewById<Button>(R.id.mcqSubmitBtn),
            questionTextView = findViewById<TextView>(R.id.mcqQuestionText),
            progressBar = findViewById<ProgressBar>(R.id.mcqProgressBar),
            progressText = findViewById<TextView>(R.id.mcqProgressBarText))
    }

    private fun getCurrentQuestion() = viewModel.getCurrentQuestionByPosition()

}

package com.learner.funzo.viewModel

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.learner.funzo.R
import com.learner.funzo.model.Exam
import com.learner.funzo.model.Options
import com.learner.funzo.model.Question
import com.learner.funzo.view.activity.ExamActivity

import com.learner.funzo.viewModel.constant.ScoreConstants
import com.learner.funzo.viewModel.nav.NavigationHandler

class QuizActivityViewModel : ViewModel(),  View.OnClickListener {
    private var currentPosition:Int = 0
    private val score : ScoreConstants = ScoreConstants

    private lateinit var optionB: TextView
    private lateinit var optionC: TextView
    private lateinit var optionD: TextView
    private lateinit var submitButton: Button
    private lateinit var optionA: TextView
    private lateinit var  progressText: TextView
    private lateinit var  progressBar: ProgressBar
    private lateinit var  questionTextView: TextView

    private lateinit var  examActivity: ExamActivity
    val rIdMcqOptionA = R.id.mcqOptionA
    val rIdMcqOptionB = R.id.mcqOptionB
    val rIdMcqOptionC = R.id.mcqOptionC
    val rIdMcqOptionD = R.id.mcqOptionD
    companion object {
        const val FINISH = "Finish"
        const val NEXT = "Next"
        const val SUBMIT = "Submit"
    }

    private var exam: Exam? = null
    var selectedOption: String? = null
    private var correctOption: String? = null

    fun navigateToResultActivity(applicationContext: Context) {
        NavigationHandler.navigateToResultActivity(applicationContext, score)
    }

    fun getCurrentPosition(): Int {
        return currentPosition
    }

    fun nextPosition() {
        ++currentPosition
    }

    fun setExam(exam: Exam) {
        this.exam = exam
    }

    fun getQuestions(): List<Question> {
        return exam!!.questions
    }

    fun getThreshold(): Int {
        return this.exam!!.threshold
    }

    fun getTotalNoOfQuestions(): Int {
        return exam!!.questions.size
    }

    private fun setCorrectOption(correctOption: String) {
        this.correctOption = correctOption
    }

    fun getCurrentQuestionByPosition(): Question {
        return this.getQuestions()[this.currentPosition]
    }

    private fun getCorrectOption(): String? {
        return this.correctOption
    }

    private fun isInputCorrect(question: Question, selectedOption: String?): Boolean {
        return question.correctOption.toString() != selectedOption
    }


    private fun turnRed(selectedOption: String?, applicationContext: ExamActivity) {

        val optionATextView = applicationContext.findViewById<TextView>(rIdMcqOptionA)

        val optionBTextView = applicationContext.findViewById<TextView>(rIdMcqOptionB)

        val optionCTextView = applicationContext.findViewById<TextView>(rIdMcqOptionC)

        val optionDTextView = applicationContext.findViewById<TextView>(rIdMcqOptionD)

        when(selectedOption) {
            "A" -> {
                optionATextView.background = ContextCompat
                    .getDrawable(applicationContext,
                        R.drawable.wrong_option_text_background
                    )
            }
            "B" -> {
                optionBTextView.background = ContextCompat
                    .getDrawable(applicationContext,
                        R.drawable.wrong_option_text_background
                    )
            }
            "C" -> {
                optionCTextView.background = ContextCompat
                    .getDrawable(applicationContext,
                        R.drawable.wrong_option_text_background
                    )
            }
            "D" -> {
                optionDTextView.background = ContextCompat
                    .getDrawable(applicationContext,
                        R.drawable.wrong_option_text_background
                    )
            }
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(this.optionA)
        options.add(this.optionB)
        options.add(this.optionC)
        options.add(this.optionD)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                examActivity,
                R.drawable.default_text_background
            )
        }
    }

    private fun setView(
        question: Question?
    ) {
        this.questionTextView.text = question!!.question
        this.optionA.text = question.optionA
        this.optionB.text = question.optionB
        this.optionC.text = question.optionC
        this.optionD.text = question.optionD
    }

    private fun setProgressBar() {
        val currentPosition = this.getCurrentPosition()
        progressBar.progress = currentPosition
        val text = "$currentPosition / ${this.getTotalNoOfQuestions()}"
        progressText.text = text
    }

    fun initMCQQuestion(
        optionA: TextView,
        optionB: TextView,
        optionC: TextView,
        optionD: TextView,
        submitButton: Button,
        questionTextView: TextView,
        progressBar: ProgressBar,
        progressText: TextView
    ) {
        this.optionA = optionA
        this.optionB = optionB
        this.optionC = optionC
        this.optionD = optionD
        this.submitButton = submitButton
        this.questionTextView = questionTextView
        this.progressBar = progressBar
        this.progressText = progressText
        this.setOnClickListener()
    }

    private fun setOnClickListener () {
        optionA.setOnClickListener(this)
        optionB.setOnClickListener(this)
        optionC.setOnClickListener(this)
        optionD.setOnClickListener(this)
        submitButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when(view.id) {
                rIdMcqOptionA -> {
                    selectedOptionView(option = examActivity.findViewById(rIdMcqOptionA), selectedOption = "A")
                }

                rIdMcqOptionB -> {
                    selectedOptionView(option = examActivity.findViewById(rIdMcqOptionB), selectedOption = "B")
                }

                rIdMcqOptionC -> {
                    selectedOptionView(option = examActivity.findViewById(rIdMcqOptionC), selectedOption = "C")
                }

                rIdMcqOptionD -> {
                    selectedOptionView(option = examActivity.findViewById(rIdMcqOptionD), selectedOption = "D")
                }

                R.id.mcqSubmitBtn -> {
                    if (isExamComplete()) {
                        completeExamProcess()
                    }
                    else if(this.selectedOption != null ) {
                        incompleteExamProcess()
                    }
                    else if (isNextPhase()) {
                        setMCQuestion(getCurrentQuestionByPosition())
                    }
                    else
                    {
                        noAnswerSelectedProcess()
                    }
                }
            }
        }
    }

    private fun isNextPhase(): Boolean {
        return submitButton.text == NEXT
    }

    private fun noAnswerSelectedProcess() {
        Toast.makeText(
            examActivity,
            "You must select an answer",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun completeExamProcess() {
        Toast.makeText(
            examActivity,
            "You have completed the quiz",
            Toast.LENGTH_SHORT
        ).show()
        this.navigateToResultActivity(applicationContext = examActivity)
    }

    private fun incompleteExamProcess() {
        if (submitButton.text == FINISH) {
            when {
                this.getCurrentPosition() < this.getTotalNoOfQuestions() -> {
                    setMCQuestion(question = this.getCurrentQuestionByPosition())
                }
            }
        } else {
            validateAnswer(this.selectedOption)

            this.getCorrectOption()?.let { showCorrectOption(it) }

            setSubmitButtonText(submitButton)
            this.selectedOption = null
            this.nextPosition()
        }
    }

    private fun setSubmitButtonText(submitButton: Button) {
        if (isExamComplete()) {
            submitButton.text = FINISH
        } else {
            submitButton.text = NEXT
        }
    }

    private fun showCorrectOption (answer: String) {
        when(answer) {
            Options.A.toString() -> {
                examActivity.findViewById<TextView>(rIdMcqOptionA).background = ContextCompat.getDrawable(
                    examActivity, R.drawable.correct_option_text_background
                )
            }
            Options.B.toString() -> {
                examActivity.findViewById<TextView>(rIdMcqOptionB).background = ContextCompat.getDrawable(
                    examActivity, R.drawable.correct_option_text_background
                )
            }
            Options.C.toString() -> {
                examActivity.findViewById<TextView>(rIdMcqOptionC).background = ContextCompat.getDrawable(
                    examActivity, R.drawable.correct_option_text_background
                )
            }
            Options.D.toString() -> {
                examActivity.findViewById<TextView>(rIdMcqOptionD).background = ContextCompat.getDrawable(
                    examActivity, R.drawable.correct_option_text_background
                )
            }
        }
    }

    private fun validateAnswer(selectedOption: String?) {
        val question = this.getCurrentQuestionByPosition()

        if (validateInput(question, selectedOption)) {
            correctAnswerView(selectedOption)
        } else {
            ScoreConstants.addToCorrectResults()
        }
    }

    private fun correctAnswerView(selectedOption: String?) {
        this.turnRed(selectedOption = selectedOption, applicationContext = examActivity)
    }

    private fun validateInput(
        question: Question,
        selectedOption: String?
    ) = this.isInputCorrect(question = question, selectedOption = selectedOption)

    fun setMCQuestion(question: Question) {
        this.defaultOptionsView()
        this.setView(question)
        this.setCorrectOption(question.correctOption.toString())
        this.setProgressBar()
    }

    private fun isExamComplete() = this.getCurrentPosition() >= this.getTotalNoOfQuestions()

    fun setExamActivity(examActivity: ExamActivity) {
        this.examActivity = examActivity
    }

    private fun selectedOptionView(option: TextView, selectedOption: String) {
        this.defaultOptionsView()
        this.selectedOption = selectedOption
        this.setSelectedOptionView(option = option)
    }

    private fun setSelectedOptionView(option: TextView) {
        option.typeface = Typeface.DEFAULT_BOLD
        option.setTextColor(Color.parseColor("#000000"))
        option.background = ContextCompat.getDrawable(examActivity,
            R.drawable.selected_option_text_background
        )
    }
}

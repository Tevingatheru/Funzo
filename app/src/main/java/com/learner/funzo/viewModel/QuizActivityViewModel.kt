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
import com.learner.funzo.view.QuizActivity
import com.learner.funzo.viewModel.constant.ScoreConstants
import com.learner.funzo.viewModel.nav.NavigationHandler

class QuizActivityViewModel : ViewModel(),  View.OnClickListener {
    private var currentPosition:Int = 0
    private val score : ScoreConstants = ScoreConstants

    private var tvOptionB: TextView? = null
    private var tvOptionC: TextView? = null
    private var tvOptionD: TextView? = null
    private var btnSubmit: Button? = null
    private var tvOptionA: TextView? = null
    private var tvProgress: TextView? = null
    private var pbProgressBar: ProgressBar? = null
    private var tvQuestion: TextView? = null
    private var quizActivity: QuizActivity? = null

    val mcqOptionA = R.id.tvOptionA
    val mcqOptionB = R.id.tvOptionB
    val mcqOptionC = R.id.tvOptionC
    val mcqOptionD = R.id.tvOptionD
    companion object {
        const val FINISH = "Finish"
        const val NEXT = "Next"
        const val SUBMIT = "Submit"
    }

    private var exam: Exam? = null
    var selectedOption: String? = null
    private var correctOption: String? = null

    fun onDestroy() {
        tvOptionA = null
        tvOptionB = null
        tvOptionC = null
        tvOptionD = null
        this.tvProgress = null
        this.pbProgressBar = null
        this.tvQuestion = null
        this.quizActivity = null
    }

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

    fun getCurrentQuestionByPosition(): Question {
        return this.getQuestions()[this.currentPosition]
    }

    private fun isInputCorrect(question: Question, selectedOption: String?): Boolean {
        return question.correctOption.toString() != selectedOption
    }


    private fun turnRed(selectedOption: String?, applicationContext: QuizActivity) {

        val optionATextView = applicationContext.findViewById<TextView>(mcqOptionA)

        val optionBTextView = applicationContext.findViewById<TextView>(mcqOptionB)

        val optionCTextView = applicationContext.findViewById<TextView>(mcqOptionC)

        val optionDTextView = applicationContext.findViewById<TextView>(mcqOptionD)

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

    private fun setOptionsToDefaultView() {
        val options = ArrayList<TextView>()

        options.add(quizActivity!!.findViewById(mcqOptionA))
        options.add(quizActivity!!.findViewById(mcqOptionB))
        options.add(quizActivity!!.findViewById(mcqOptionC))
        options.add(quizActivity!!.findViewById(mcqOptionD))

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                quizActivity!!,
                R.drawable.default_text_background
            )
        }
    }

    private fun setTextView(
        question: Question
    ) {
        this.tvQuestion!!.text = question.question
        this.tvOptionA!!.text = question.optionA
        this.tvOptionB!!.text = question.optionB
        this.tvOptionC!!.text = question.optionC
        this.tvOptionD!!.text = question.optionD
    }

    private fun setProgressBar() {
        val currentPosition = this.getCurrentPosition()
        pbProgressBar!!.progress = currentPosition
        val text = "$currentPosition / ${this.getTotalNoOfQuestions()}"
        tvProgress!!.text = text
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
        this.tvOptionA = optionA
        this.tvOptionB = optionB
        this.tvOptionC = optionC
        this.tvOptionD = optionD
        this.btnSubmit = submitButton
        this.tvQuestion = questionTextView
        this.pbProgressBar = progressBar
        this.tvProgress = progressText
        this.setOnClickListener()
    }

    private fun setOnClickListener () {
        tvOptionA!!.setOnClickListener(this)
        tvOptionB!!.setOnClickListener(this)
        tvOptionC!!.setOnClickListener(this)
        tvOptionD!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when(view.id) {
                mcqOptionA -> {
                    selectedOptionView(option = quizActivity!!.findViewById(mcqOptionA), selectedOption = "A")
                }

                mcqOptionB -> {
                    selectedOptionView(option = quizActivity!!.findViewById(mcqOptionB), selectedOption = "B")
                }

                mcqOptionC -> {
                    selectedOptionView(option = quizActivity!!.findViewById(mcqOptionC), selectedOption = "C")
                }

                mcqOptionD -> {
                    selectedOptionView(option = quizActivity!!.findViewById(mcqOptionD), selectedOption = "D")
                }

                R.id.submitBtn -> {
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
        return btnSubmit!!.text == NEXT
    }

    private fun noAnswerSelectedProcess() {
        Toast.makeText(
            quizActivity!!,
            "You must select an answer",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun completeExamProcess() {
        Toast.makeText(
            quizActivity!!,
            "You have completed the quiz",
            Toast.LENGTH_SHORT
        ).show()
        this.navigateToResultActivity(applicationContext = quizActivity!!)
    }

    private fun incompleteExamProcess() {
        if (btnSubmit!!.text == FINISH) {
            when {
                this.getCurrentPosition() < this.getTotalNoOfQuestions() -> {
                    setMCQuestion(question = this.getCurrentQuestionByPosition())
                }
            }
        } else {
            validateAnswer(this.selectedOption)
            showCorrectOption(correctOption!!)
            setSubmitButtonText(btnSubmit!!)
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
                quizActivity!!.findViewById<TextView>(mcqOptionA).background = ContextCompat.getDrawable(
                    quizActivity!!, R.drawable.correct_option_text_background
                )
            }
            Options.B.toString() -> {
                quizActivity!!.findViewById<TextView>(mcqOptionB).background = ContextCompat.getDrawable(
                    quizActivity!!, R.drawable.correct_option_text_background
                )
            }
            Options.C.toString() -> {
                quizActivity!!.findViewById<TextView>(mcqOptionC).background = ContextCompat.getDrawable(
                    quizActivity!!, R.drawable.correct_option_text_background
                )
            }
            Options.D.toString() -> {
                quizActivity!!.findViewById<TextView>(mcqOptionD).background = ContextCompat.getDrawable(
                    quizActivity!!, R.drawable.correct_option_text_background
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
        this.turnRed(selectedOption = selectedOption, applicationContext = quizActivity!!)
    }

    private fun validateInput(
        question: Question,
        selectedOption: String?
    ) = this.isInputCorrect(question = question, selectedOption = selectedOption)

    fun setMCQuestion(question: Question) {
        this.setOptionsToDefaultView()
        this.setTextView(question)
        this.correctOption = question.correctOption.toString()
        this.setProgressBar()
    }

    private fun isExamComplete() = this.getCurrentPosition() >= this.getTotalNoOfQuestions()

    fun setExamActivity(quizActivity: QuizActivity) {
        this.quizActivity = quizActivity
    }

    private fun selectedOptionView(option: TextView, selectedOption: String) {
        this.setOptionsToDefaultView()
        this.selectedOption = selectedOption
        this.setSelectedOptionView(option = option)
    }

    private fun setSelectedOptionView(option: TextView) {
        option.typeface = Typeface.DEFAULT_BOLD
        option.setTextColor(Color.parseColor("#000000"))
        option.background = ContextCompat.getDrawable(quizActivity!!,
            R.drawable.selected_option_text_background
        )
    }
}

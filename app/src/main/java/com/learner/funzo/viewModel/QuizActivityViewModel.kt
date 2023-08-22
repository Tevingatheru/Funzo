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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.learner.funzo.R
import com.learner.funzo.model.Exam
import com.learner.funzo.model.Options
import com.learner.funzo.model.Question
import com.learner.funzo.model.QuestionType
import com.learner.funzo.view.activity.ExamActivity

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
    private var  examActivity: ExamActivity? = null

    val rIdMcqOptionA = R.id.mcqOptionA
    val rIdMcqOptionB = R.id.mcqOptionB
    val rIdMcqOptionC = R.id.mcqOptionC
    val rIdMcqOptionD = R.id.mcqOptionD
    val rIdMcqSubmitBtn = R.id.mcqSubmitBtn
    private lateinit var navController: NavController

    companion object {
        const val FINISH = "Finish"
        const val NEXT = "Next"
        const val SUBMIT = "Submit"
    }

    private var exam: Exam? = null
    var selectedOption: String? = null
    private var correctOption: String? = null

    fun onDestroy() {
        this.tvOptionA = null
        this.tvOptionB = null
        this.tvOptionC = null
        this.tvOptionD = null
        this.tvProgress = null
        this.pbProgressBar = null
        this.tvQuestion = null
        this.examActivity = null
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

    private fun setOptionsToDefaultView() {
        val options = ArrayList<TextView>()
        options.add(this.tvOptionA!!)
        options.add(this.tvOptionB!!)
        options.add(this.tvOptionC!!)
        options.add(this.tvOptionD!!)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                examActivity!!,
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

    private fun initMCQQuestion(
    ) {
        this.tvOptionA = examActivity!!.findViewById(rIdMcqOptionA)
        this.tvOptionB = examActivity!!.findViewById(rIdMcqOptionB)
        this.tvOptionC = examActivity!!.findViewById(rIdMcqOptionC)
        this.tvOptionD = examActivity!!.findViewById(rIdMcqOptionD)
        this.btnSubmit = examActivity!!.findViewById(rIdMcqSubmitBtn)
        this.tvQuestion = examActivity!!.findViewById(R.id.multipleChoiceQuestionText)
        this.pbProgressBar = examActivity!!.findViewById(R.id.multipleChoiceProgressBar)
        this.tvProgress = examActivity!!.findViewById(R.id.multipleChoiceProgressBarText)
        tvOptionA!!.setOnClickListener(this)
        tvOptionB!!.setOnClickListener(this)
        tvOptionC!!.setOnClickListener(this)
        tvOptionD!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when(view.id) {
                rIdMcqOptionA -> {
                    selectedOptionView(option = examActivity!!.findViewById(rIdMcqOptionA), selectedOption = "A")
                }

                rIdMcqOptionB -> {
                    selectedOptionView(option = examActivity!!.findViewById(rIdMcqOptionB), selectedOption = "B")
                }

                rIdMcqOptionC -> {
                    selectedOptionView(option = examActivity!!.findViewById(rIdMcqOptionC), selectedOption = "C")
                }

                rIdMcqOptionD -> {
                    selectedOptionView(option = examActivity!!.findViewById(rIdMcqOptionD), selectedOption = "D")
                }

                rIdMcqSubmitBtn -> {
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
                examActivity!!.findViewById<TextView>(rIdMcqOptionA).background = ContextCompat.getDrawable(
                    examActivity!!, R.drawable.correct_option_text_background
                )
            }
            Options.B.toString() -> {
                examActivity!!.findViewById<TextView>(rIdMcqOptionB).background = ContextCompat.getDrawable(
                    examActivity!!, R.drawable.correct_option_text_background
                )
            }
            Options.C.toString() -> {
                examActivity!!.findViewById<TextView>(rIdMcqOptionC).background = ContextCompat.getDrawable(
                    examActivity!!, R.drawable.correct_option_text_background
                )
            }
            Options.D.toString() -> {
                examActivity!!.findViewById<TextView>(rIdMcqOptionD).background = ContextCompat.getDrawable(
                    examActivity!!, R.drawable.correct_option_text_background
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
        this.turnRed(selectedOption = selectedOption, applicationContext = examActivity!!)
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

    fun setExamActivity(examActivity: ExamActivity) {
        this.examActivity = examActivity
    }

    private fun selectedOptionView(option: TextView, selectedOption: String) {
        this.setOptionsToDefaultView()
        this.selectedOption = selectedOption
        this.setSelectedOptionView(option = option)
    }

    private fun setSelectedOptionView(option: TextView) {
        option.typeface = Typeface.DEFAULT_BOLD
        option.setTextColor(Color.parseColor("#000000"))
        option.background = ContextCompat.getDrawable(examActivity!!,
            R.drawable.selected_option_text_background
        )
    }

    fun setQuestion(applicationContext: ExamActivity) {
        val question: Question = this.getCurrentQuestionByPosition()

        when (question.questionType) {
            //            QuestionType.TRUE_FALSE -> {
            //                setTFQuestion(question)
            //            }
            QuestionType.MULTIPLE_CHOICE -> {
                applicationContext.setContentView(R.layout.fragment_multiple_choice)
                val navHostFragment = applicationContext.supportFragmentManager
                    .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

                navController = navHostFragment.navController
                navController.navigate(resId = R.id.multipleChoiceFragment)

                initMCQQuestion()
                this.setMCQuestion(question)
            }
            //            QuestionType.OPEN_ENDED -> {
            //                setOEQuestion(question)
            //            }
            else -> {
                Toast.makeText(applicationContext, "No display for this type of question", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}

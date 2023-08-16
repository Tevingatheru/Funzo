package com.learner.funzo.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.learner.funzo.model.Exam
import com.learner.funzo.model.Options
import com.learner.funzo.model.Question
import com.learner.funzo.viewModel.constant.ScoreConstants
import com.learner.funzo.viewModel.nav.NavigationHandler

class QuizActivityViewModel : ViewModel() {
    private var currentPosition:Int = 0
    private val score : ScoreConstants = ScoreConstants
    companion object {
        const val FINISH = "Finish"
        const val NEXT = "Next"
        const val SUBMIT = "Submit"
    }

    private var questionList: ArrayList<Question>? = null
    private var exam: Exam? = null
    var selectedOption: String? = null
    private var correctOption: Options? = null

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

    fun setCorrectOption(correctOption: Options) {
        this.correctOption = correctOption
    }

    fun getCurrentQuestionByPosition(currentPosition: Int): Question {
        return this.getQuestions()[currentPosition]
    }

    fun getCorrectOption(): Options? {
        return this.correctOption
    }
}

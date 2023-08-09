package com.learner.funzo.viewModel.constant

import android.os.Parcelable
import com.learner.funzo.model.Exam
import com.learner.funzo.model.Question
import kotlinx.parcelize.Parcelize

@Parcelize
object ExamConstants : Parcelable {
    private val threshold: Int = 100
    private var questions: ArrayList<Question>? = null

    fun createExam(subject: String): ExamConstants {
        questions = QuestionConstants.getQuestionsBySubjectName(subject)
        return this
    }

    private fun getQuestions(): Exam {
        return Exam(threshold, questions!!)
    }

    fun getExam(): Exam {
        return getQuestions();
    }
}

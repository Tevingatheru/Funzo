package com.learner.funzo.viewModel.constant

import android.os.Parcelable
import com.learner.funzo.model.Exam
import kotlinx.parcelize.Parcelize

@Parcelize
object ExamConstants : Parcelable {
    private val PASSING_GRADE: Int = 100

    private fun getQuestions(): Exam {
        return Exam(PASSING_GRADE, QuestionConstants.defaultQuestionsArray)
    }

    fun getExam(): Exam {
        return getQuestions();
    }
}

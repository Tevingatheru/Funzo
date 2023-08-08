package com.learner.funzo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
object ExamConstants : Parcelable {
    private var threshold: Int? = null
    private var questions: ArrayList<Question>? = null

    private fun getQuestions(): Exam {
        return Exam(threshold!!, questions!!)
    }

    fun getExam(): Exam {
        return getQuestions();
    }
}

package com.learner.funzo.viewModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
object ScoreConstants : Parcelable {
    private var threshold: Int = 0
    private var totalQuestions: Int = 0

    private var correctlyAnswered: Int = 0

    fun addToCorrectResults() {
        correctlyAnswered++
    }

    fun resetScore(questionsSize: Int, setThreshold: Int) {
        totalQuestions =  questionsSize
        correctlyAnswered = 0
        threshold = setThreshold
    }

    fun  getTotalNumberOfQuestions(): Int {
        return totalQuestions
    }

    fun  getScore(): Int {
        return correctlyAnswered
    }

    fun passed(): Boolean {
        return thresholdMet()
    }

    private fun thresholdMet(): Boolean {
        val scorePercentage = (correctlyAnswered / totalQuestions) * 100
        return scorePercentage >= threshold
    }
}

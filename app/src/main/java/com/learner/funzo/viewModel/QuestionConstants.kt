package com.learner.funzo.viewModel

import com.learner.funzo.model.Options
import com.learner.funzo.model.Question

object QuestionConstants {
    const val TOTAL_QUESTIONS: String = "total_questions"

    fun getQuestionsBySubjectName(subjectName: String): ArrayList<Question> {
        return getQuestions()
    }

    private fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()
        questionsList.add(
            Question(
                id = 1,
                question = "Can you see this?",
                image = "",
                optionA = "No",
                optionB = "Maybe",
                optionC = "Next page",
                optionD = "Yes",
                correctOption = Options.D
            ))
        return questionsList
    }
}
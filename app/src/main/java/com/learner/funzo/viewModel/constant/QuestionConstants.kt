package com.learner.funzo.viewModel.constant

import com.learner.funzo.model.Options
import com.learner.funzo.model.Question

object QuestionConstants {
    const val TOTAL_QUESTIONS: String = "total_questions"

    fun getQuestionsBySubjectName(): ArrayList<Question> {
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

        questionsList.add(
            Question(
                id = 2,
                question = "Can you see this? (Yes or No)",
                image = "",
                optionA = "Yes",
                optionB = "No",
                optionC = "Next page",
                optionD = "Zzz",
                correctOption = Options.A
            ))
        return questionsList
    }
}
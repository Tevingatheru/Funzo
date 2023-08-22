package com.learner.funzo.viewModel.constant

import com.learner.funzo.model.Options
import com.learner.funzo.model.Question
import com.learner.funzo.model.QuestionType

object QuestionConstants {
    const val TOTAL_QUESTIONS: String = "total_questions"
    val defaultQuestionsArray: ArrayList<Question> = getQuestions()

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
                correctOption = Options.D.toString(),
                questionType = QuestionType.MULTIPLE_CHOICE
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
                correctOption = Options.A.toString(),
                questionType = QuestionType.MULTIPLE_CHOICE
            ))

        questionsList.add(
            Question(
                id = 3,
                question = "1 + 1 = 2",
                image = "",
                optionA = "Yes",
                optionB = "No",
                optionC = null,
                optionD = null,
                correctOption = true.toString(),
                questionType = QuestionType.TRUE_FALSE
            ))
        return questionsList
    }
}

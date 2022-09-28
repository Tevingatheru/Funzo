package com.learner.funzo

object QuestionConstants {
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = ""

    fun getQuestionsBySubjectName(subjectName: String): ArrayList<Question> {
        return getQuestions()
    }

    private fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()
        questionsList.add(Question(1, "Can you see this?","","No","Maybe","Next page","Yes",Options.D))
        return questionsList
    }
}
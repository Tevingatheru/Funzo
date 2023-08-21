package com.learner.funzo.viewModel.constant

import com.learner.funzo.model.Subject
import java.util.*

object SubjectConstants {
    fun getSubjects() : ArrayList<Subject> {
        val topics = ArrayList<Subject>()
        topics.add(Subject(id = 1, name = "Test", category = "General"))
        return topics
    }
}

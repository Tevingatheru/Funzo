package com.learner.funzo.viewModel

import com.learner.funzo.model.Subject
import java.util.*

object SubjectConstants {
    fun getSubjects() : ArrayList<Subject> {
        val topics = ArrayList<Subject>()
        topics.add(Subject(1, "Test", "General"))
        return topics
    }
}

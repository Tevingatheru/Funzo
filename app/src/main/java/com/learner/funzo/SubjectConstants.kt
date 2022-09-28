package com.learner.funzo

import java.util.*

object SubjectConstants {
    fun getSubjects() : ArrayList<Subject> {
        val topics = ArrayList<Subject>()
        topics.add(Subject(1, "Test", "General"))
        return topics
    }
}
package com.learner.funzo.mapper

import com.learner.funzo.model.Subject
import com.learner.funzo.model.retrofit.dto.SubjectDto
import com.learner.funzo.model.retrofit.response.SubjectListResponse

object SubjectDtoMapper {
    fun mapSubjectListResponseToList(subjectListResponse: SubjectListResponse): List<SubjectDto> {
        var subjectList : List<SubjectDto> = mutableListOf()
        subjectListResponse.subjects.forEach{
            subjectList = subjectList.plus(SubjectDto(name = it.name!!, category = it.category!!))
        }
        return subjectList
    }

    fun mapSubjectListToList(subjects: List<Subject>): List<SubjectDto> {
        var subjectList : List<SubjectDto> = mutableListOf()
        subjects.forEach{
            subjectList = subjectList.plus(SubjectDto(name = it.name, category = it.category))
        }
        return subjectList
    }
}

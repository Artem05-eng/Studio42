package com.example.studio42.domain.entity

data class Employer (
        val id: String,
        val name: String,
        val open_vacancies: String,
        val logo_urls: ObjectUrl?
    )

data class EmployerFound(
    val items: List<Employer>,
    val found: Int
)

data class ObjectUrl(
    val original: String
)
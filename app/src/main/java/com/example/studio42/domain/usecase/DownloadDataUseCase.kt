package com.example.studio42.domain.usecase

import com.example.studio42.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DownloadDataUseCase @Inject constructor(private val repository: Repository) {

    operator suspend fun invoke(text: String, type: String?, flag: Boolean) = withContext(Dispatchers.IO) {
        repository.getEmployer(text, type, flag)
    }
}
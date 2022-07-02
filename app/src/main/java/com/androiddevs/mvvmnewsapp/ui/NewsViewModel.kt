package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.core.entities.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNews: LiveData<Resource<NewsResponse>> get() = _breakingNews

    private var breakingNewsPage = 1

    init {
        getBreakingNews(countryCode = "us")
    }

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch(Dispatchers.IO) {
        _breakingNews.postValue(Resource.Loading())
        val response =
            newsRepository.getBreakingNews(countryCode = countryCode, pageNumber = breakingNewsPage)
        _breakingNews.postValue(handleBreakingNews(response))

    }

    private fun handleBreakingNews(response: Response<NewsResponse>): Resource<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultData ->
                return Resource.Success(resultData)
            }
        }

        return Resource.Error(response.message())
    }


}
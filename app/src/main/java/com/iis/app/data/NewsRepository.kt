package com.iis.app.data



import com.iis.app.data.model.New

class NewsRepository(val dataSource: NewsDataSource) {
    var news: List<New>? = null
        private set

    suspend fun getNews(token: String):  Result<Any> {
        // handle login

        val result = dataSource.getNews(token)

        if (result is Result.Success) {
            result.data?.let { setNews(it as List<New>) }
        }

        return result
    }

    private fun setNews(news: List<New>) {
        this.news = news
    }
}
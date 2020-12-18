package com.example.Kotlinpicturecrawler.api

object GankApiBuilder {
    fun build(page: Int): String {
        return "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/100/${page}"
    }
}
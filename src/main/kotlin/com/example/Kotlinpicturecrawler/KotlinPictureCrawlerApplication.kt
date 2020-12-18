package com.example.Kotlinpicturecrawler

import com.example.Kotlinpicturecrawler.dao.SearchKeyWordRepository
import com.example.Kotlinpicturecrawler.po.SearchKeyWord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component
import java.io.File

@SpringBootApplication
@EnableScheduling
class KotlinPictureCrawlerApplication

fun main(args: Array<String>) {
	runApplication<KotlinPictureCrawlerApplication>(*args)
}


@Component
@Order(value = Ordered.LOWEST_PRECEDENCE)
class initSearchKeyWordRunner : CommandLineRunner {
	@Autowired
	lateinit var searchKeyWordRepository: SearchKeyWordRepository

	override fun run(vararg args: String) {
		var keyWords = File("搜索关键词列表.data").readLines()
		keyWords.forEach {
			val SearchKeyWord = SearchKeyWord()
			SearchKeyWord.keyWord = it
			searchKeyWordRepository.saveOnNoDuplicateKey(it)
		}
	}
}
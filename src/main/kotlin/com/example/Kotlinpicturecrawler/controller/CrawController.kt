package com.example.Kotlinpicturecrawler.controller

import com.example.Kotlinpicturecrawler.job.BatchUpdateJob
import com.example.Kotlinpicturecrawler.service.CrawImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@RestController
class CrawController {

    @Autowired lateinit var crawImageService: CrawImageService
    @Autowired lateinit var batchUpdateJob: BatchUpdateJob

    @GetMapping("/doBaiduImageCrawJob")
    fun doBaiduImageCrawJob(): String {
        crawImageService.doBaiduImageCrawJob()
        return "doBaiduImageCrawJob JOB Started"
    }

    @GetMapping("/doGankImageCrawJob")
    fun doGankImageCrawJob(): String {
        crawImageService.doGankImageCrawJob()
        return "doBaiduImageCrawJob JOB Started"
    }

    @GetMapping("/doGankImageCrawJob")
    fun BatchUpdateJob(): String {
        batchUpdateJob.job()
        return "BatchUpdateJob Started"
    }


}

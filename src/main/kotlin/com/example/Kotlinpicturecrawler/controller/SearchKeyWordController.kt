package com.example.Kotlinpicturecrawler.controller

import com.example.Kotlinpicturecrawler.dao.SearchKeyWordRepository
import com.example.Kotlinpicturecrawler.po.SearchKeyWord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

@RestController
class SearchKeyWordController {
    @Autowired
    lateinit var searchKeyWordRepository: SearchKeyWordRepository

    @GetMapping("/searchKeyWordJson")
    fun sotuSearchJson(@RequestParam(value = "page", defaultValue = "0") page: Int,
                       @RequestParam(value = "size", defaultValue = "10") size: Int,
                       @RequestParam(value = "searchText", defaultValue = "") searchText: String): Page<SearchKeyWord> {
        return getPageResult(page, size, searchText)
    }

    private fun getPageResult(page: Int, size: Int, searchText: String): Page<SearchKeyWord> {
        val sort = Sort.by(Sort.Direction.DESC, "id")
        val pageable = PageRequest.of(page, size, sort)
        if (searchText == "") {
            return searchKeyWordRepository.findAll(pageable)
        } else {
            return searchKeyWordRepository.search(searchText, pageable)
        }
    }

    @RequestMapping(value = ["save_keyword"], method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun save(@RequestParam("keyWord") keyWord: String): String {
        if (keyWord == "") {
            return "0"
        } else {
            searchKeyWordRepository.saveOnNoDuplicateKey(keyWord)
            return "1"
        }
    }
}
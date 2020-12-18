package com.example.Kotlinpicturecrawler.controller

import com.example.Kotlinpicturecrawler.dao.ImageRepository
import com.example.Kotlinpicturecrawler.po.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest


//@Controller
@RestController
class ImageController {

    @Autowired lateinit var imageRepository: ImageRepository

//    @RequestMapping(value = ["/", "sotu_view"], method = [RequestMethod.GET])
    @GetMapping("/sotu_view")
    fun sotuView(model: Model, request: HttpServletRequest): ModelAndView {
        model.addAttribute("requestURI", request.requestURI)
        return ModelAndView("sotu_view")
    }

//    @RequestMapping(value = ["sotu_gank_view"], method = [RequestMethod.GET])
    @GetMapping("/sotu_gank_view")
    fun sotuGankView(model: Model, request: HttpServletRequest): ModelAndView {
        model.addAttribute("requestURI", request.requestURI)
        return ModelAndView("sotu_gank_view")
    }

//    @RequestMapping(value = ["sotu_favorite_view"], method = [RequestMethod.GET])
    @GetMapping("/sotu_favorite_view")
    fun sotuFavoriteView(model: Model, request: HttpServletRequest): ModelAndView {
        model.addAttribute("requestURI", request.requestURI)
        return ModelAndView("sotu_favorite_view")
    }

//    @RequestMapping(value = ["sotuJson"], method = [RequestMethod.GET])
//    @ResponseBody
    @GetMapping("/sotuJson")
    fun sotuJson(@RequestParam(value = "page", defaultValue = "0") page: Int,
                 @RequestParam(value = "size", defaultValue = "10") size: Int): Page<Image> {
        return getPageResult(page, size)
    }

//    @RequestMapping(value = ["sotuSearchJson"], method = [RequestMethod.GET])
//    @ResponseBody
    @GetMapping("/sotuSearchJson")
    fun sotuSearchJson(@RequestParam(value = "page", defaultValue = "0") page: Int,
                       @RequestParam(value = "size", defaultValue = "10") size: Int,
                       @RequestParam(value = "searchText", defaultValue = "") searchText: String): Page<Image> {
        return getPageResult(page, size, searchText)
    }

//    @RequestMapping(value = ["sotuGankSearchJson"], method = [RequestMethod.GET])
//    @ResponseBody
    @GetMapping("/sotuGankSearchJson")
    fun sotuGankSearchJson(@RequestParam(value = "page", defaultValue = "0") page: Int,
                           @RequestParam(value = "size", defaultValue = "10") size: Int,
                           @RequestParam(value = "searchText", defaultValue = "") searchText: String): Page<Image> {
        return getGankPageResult(page, size, searchText)
    }

//    @RequestMapping(value = ["sotuSearchFavoriteJson"], method = [RequestMethod.GET])
//    @ResponseBody
    @GetMapping("/sotuSearchFavoriteJson")
    fun sotuSearchFavoriteJson(@RequestParam(value = "page", defaultValue = "0") page: Int,
                               @RequestParam(value = "size", defaultValue = "10") size: Int,
                               @RequestParam(value = "searchText", defaultValue = "") searchText: String): Page<Image> {
        return getFavoritePageResult(page, size, searchText)
    }


//    @RequestMapping(value = ["addFavorite"], method = arrayOf(RequestMethod.POST))
//    @ResponseBody
    @PostMapping("/addFavorite")
    fun addFavorite(@RequestParam(value = "id") id: Long): Boolean {
        imageRepository.addFavorite(id)
        return true
    }

//    @RequestMapping(value = ["delete"], method = arrayOf(RequestMethod.POST))
//    @ResponseBody
    @PostMapping("/delete")
    fun delete(@RequestParam(value = "id") id: Long): Boolean {
        imageRepository.delete(id)
        return true
    }


    private fun getPageResult(page: Int, size: Int): Page<Image> {
        val sort = Sort(Sort.Direction.DESC, mutableListOf<String>("id"))
        val pageable = PageRequest.of(page, size, sort)
        return imageRepository.findAll(pageable)
    }

    private fun getPageResult(page: Int, size: Int, searchText: String): Page<Image> {
        val sort = Sort(Sort.Direction.DESC, "id")
        // 注意：PageRequest.of(page,size,sort) page 默认是从0开始
        val pageable = PageRequest.of(page, size, sort)
        if (searchText == "") {
            return imageRepository.findAll(pageable)
        } else {
            return imageRepository.search(searchText, pageable)
        }
    }

    private fun getGankPageResult(page: Int, size: Int, searchText: String): Page<Image> {
        val sort = Sort(Sort.Direction.DESC, "id")
        // 注意：PageRequest.of(page,size,sort) page 默认是从0开始
        val pageable = PageRequest.of(page, size, sort)
        if (searchText == "") {
            return imageRepository.findGankAll(pageable)
        } else {
            return imageRepository.searchGank(searchText, pageable)
        }
    }

    private fun getFavoritePageResult(page: Int, size: Int, searchText: String): Page<Image> {
        val sort = Sort(Sort.Direction.DESC, "id")
        val pageable = PageRequest.of(page, size, sort)
        if (searchText == "") {
            val allFavorite = imageRepository.findAllFavorite(pageable)
            return allFavorite
        } else {
            val searchFavorite = imageRepository.searchFavorite(searchText, pageable)
            return searchFavorite
        }
    }

}
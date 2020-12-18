package com.example.Kotlinpicturecrawler.dao

import com.example.Kotlinpicturecrawler.po.Image
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ImageRepository : PagingAndSortingRepository<Image, Long> {

    //使用#{#entityName} 代替本来实体的名称，而Spring Data JPA 会自动根据 Image 实体上对应的 @Entity(name = "Image") 或者是默认的@Entity，来自动将实体名称填入SQL 语句中
    @Query("SELECT a from #{#entityName} a where a.isDeleted=0 and a.category like %:category% order by a.gmtModified desc")
    fun findByCategory(@Param("category") category: String): MutableList<Image>

    @Query("select count(*) from #{#entityName} a where a.url = :url")
    fun countByUrl(@Param("url") url: String): Int

    /**源数据列表*/
    @Query("SELECT a from #{#entityName} a where a.isDeleted=0 order by a.id desc") override fun findAll(pageable: Pageable): Page<Image>

    @Query("SELECT a from #{#entityName} a where a.isDeleted=0 and a.category like %:searchText% order by a.id desc")
    fun search(@Param("searchText") searchText: String, pageable: Pageable): Page<Image>

    /**收藏列表*/
    @Query("SELECT a from #{#entityName} a where a.isDeleted=0 and a.isFavorite=1 order by a.gmtModified desc")
    fun findAllFavorite(pageable: Pageable): Page<Image>

    @Query("SELECT a from #{#entityName} a where a.isDeleted=0 and a.isFavorite=1 and a.category like %:searchText% order by a.gmtModified desc")
    fun searchFavorite(@Param("searchText") searchText: String, pageable: Pageable): Page<Image>

    @Modifying
    @Transactional
    @Query("update #{#entityName} a set a.isFavorite=1,a.gmtModified=now() where a.id=:id")
    fun addFavorite(@Param("id") id: Long)

    @Modifying
    @Transactional
    @Query("update #{#entityName} a set a.isDeleted=1 where a.id=:id")
    fun delete(@Param("id") id: Long)

    @Query("SELECT a from #{#entityName} a where a.isDeleted=0 and a.sourceType=1 order by a.id desc")
    fun findGankAll(pageable: Pageable): Page<Image>

    @Query("SELECT a from #{#entityName} a where a.sourceType=1  and a.isDeleted=0 and a.category like %:searchText% order by a.id desc")
    fun searchGank(@Param("searchText") searchText: String, pageable: Pageable): Page<Image>

}
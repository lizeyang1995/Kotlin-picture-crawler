package com.example.Kotlinpicturecrawler.po

import java.util.*
import javax.persistence.*

@Entity
// 建立类别 category 字段和 url 索引
// Index 是@Index 注解，当做参数使用的时候不需要加@
@Table(indexes = arrayOf(
        Index(name = "idx_url", unique = true, columnList = "url"),
        Index(name = "idx_category", unique = false, columnList = "category"))
)
class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1
    @Version
    var version: Int = 0
    @Column(length = 255, unique = true, nullable = false)
    var category: String = ""
    var isFavorite: Int = 0
    @Column(length = 255, unique = true, nullable = false)
    var url: String = ""
    var gmtCreated: Date = Date()
    var gmtModified: Date = Date()
    var isDeleted: Int = 0 //1 Yes 0 No
    var deletedDate: Date = Date()
    @Lob
    //@Lob var imageBlob: ByteArray = byteArrayOf() 这个字段存储图片的 Base64内容
    var imageBlob: ByteArray = byteArrayOf()
    /* 0-Baidu  1-Gank */
    var sourceType: Int = 0

    override fun toString(): String {
        return "Image(id=$id, version=$version, category='$category', isFavorite=$isFavorite, url='$url', gmtCreated=$gmtCreated, gmtModified=$gmtModified, isDeleted=$isDeleted, deletedDate=$deletedDate)"
    }
}
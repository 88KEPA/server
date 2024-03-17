package com.kepa.domain.banner

import com.kepa.common.BaseWithAccountEntity
import com.kepa.convert.StringListConverter
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity

/**
 * packageName    : com.kepa.domain.banner
 * fileName       : Banner
 * author         : hoewoonjeong
 * date           : 3/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/24        hoewoonjeong               최초 생성
 */
@Entity
class Banner(
    @Column(nullable = false)
    @Convert(converter = StringListConverter::class)
    var title: List<String>,
    @Column(nullable = false)
    @Convert(converter = StringListConverter::class)
    var explanation: List<String>,
    @Column(nullable = false)
    var backGroundColor: String,
    @Column(nullable = false)
    var isActive: Boolean,
    var image: String,
    var orderNum: Long,
): BaseWithAccountEntity() {
    fun update(
        title: List<String>,
        explain: List<String>,
        backGroundColor: String,
        isActive: Boolean,
        image: String
    ) {
        this.title = title
        this.explanation = explain
        this.backGroundColor = backGroundColor
        this.isActive = isActive
        this.image = image
    }
}
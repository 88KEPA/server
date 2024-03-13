package com.kepa.domain.banner

import com.kepa.common.BaseEntity
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
): BaseEntity() {
}
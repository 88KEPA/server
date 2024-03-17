package com.kepa.domain.banner

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * packageName    : com.kepa.domain.banner
 * fileName       : BannerRepository
 * author         : hoewoonjeong
 * date           : 3/10/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/10/24        hoewoonjeong               최초 생성
 */
interface BannerRepository : JpaRepository<Banner, Long> {

    fun findAllByIsActiveIsTrue(): List<Banner>

    @Query("UPDATE Banner SET isActive = :isActive WHERE id = :id")
    fun updateActive(@Param(value = "isActive") isActive: Boolean,@Param(value = "id") id: Long)
}
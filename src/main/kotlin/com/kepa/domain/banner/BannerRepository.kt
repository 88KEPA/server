package com.kepa.domain.banner

import org.springframework.data.jpa.repository.JpaRepository

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

    fun findAllByIsActiveIsTrueOrderByOrderNum(): List<Banner>

    fun findAllByOrderByOrderNum(): List<Banner>;
}
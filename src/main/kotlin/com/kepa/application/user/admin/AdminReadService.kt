package com.kepa.application.user.admin

import com.kepa.application.user.admin.dto.request.enums.Sort
import com.kepa.application.user.admin.dto.response.AccountDetailInfo
import com.kepa.application.user.admin.dto.response.ApplyPartnerDetailInfo
import com.kepa.application.user.admin.dto.response.ApplyPartners
import com.kepa.application.user.admin.dto.response.JoinTrainers
import com.kepa.application.user.dto.response.PageResponse
import com.kepa.common.exception.ExceptionCode.NOT_EXSISTS_INFO
import com.kepa.common.exception.KepaException
import com.kepa.domain.partner.PartnerRepository
import com.kepa.domain.user.account.AccountRepository
import com.kepa.domain.user.enums.Role
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AdminReadService(
    private val accountRepository: AccountRepository,
    private val partnerRepository: PartnerRepository,
) {

    fun getJoinTrainer(page: Int, limit: Int, keyword: String?, sort: Sort): PageResponse {
        val findAllTrainers = accountRepository.findAllByEmailOrNameOrPhone(
            keyword = keyword,
            role = Role.TRAINER,
            sort = sort,
        )
        val trainers = findAllTrainers.map { JoinTrainers(id = it.id, name = it.name, birth = it.birth, createdAt = it.createdAt) }
        val totalCount = trainers.size.toLong()
        val totalPageCount: Long = totalCount / limit
        if ((totalCount % limit).toInt() != 0) {
            totalPageCount + 1
        }

        val result = getSliceResult(trainers, page, limit)
        return PageResponse.toResponse(result, totalCount, page, limit, totalPageCount)
    }

    fun getAccountDetailInfo(id: Long): AccountDetailInfo {
        val findAccount = accountRepository.findByIdOrNull(id)
            ?: throw KepaException(NOT_EXSISTS_INFO)
        return AccountDetailInfo.toResponse(findAccount);
    }

    fun getPartners(page: Int, limit: Int, keyword: String?): PageResponse {
        val partners = keyword?.let {
            partnerRepository.findAllByOrganization(keyword)
        } ?: partnerRepository.findAll()
        val applyPartners = partners.map { ApplyPartners(id = it.id, approveStatus = it.approveStatus, organization = it.organization, createdAt = it.createdAt) }

        val totalCount = applyPartners.size.toLong()
        val totalPageCount: Long = totalCount / limit
        if ((totalCount % limit).toInt() != 0) {
            totalPageCount + 1
        }
        val result = getSliceResult(applyPartners, page, limit)
        return PageResponse.toResponse(result, totalCount, page, limit, totalPageCount)
    }

    fun getPartnerDetailInfo(id: Long): ApplyPartnerDetailInfo {
        val partner = partnerRepository.findByIdOrNull(id) ?: throw KepaException(NOT_EXSISTS_INFO)
        return ApplyPartnerDetailInfo.toResponse(partner)
    }

    private fun getSliceResult(data: List<Any>, page: Int, limit: Int): List<Any> {
        val totalCount = data.size.toLong()
        val endIndexTemp = page * limit + limit
        val endIndex = if (endIndexTemp >= totalCount) {
            totalCount - 1
        } else {
            endIndexTemp
        }

        val startIndex = page * limit
        return data.slice(startIndex..endIndex.toInt())
    }

}
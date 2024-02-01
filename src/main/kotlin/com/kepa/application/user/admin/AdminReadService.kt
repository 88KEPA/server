package com.kepa.application.user.admin

import com.kepa.application.user.admin.dto.response.JoinTrainers
import com.kepa.application.user.dto.response.PageResponse
import com.kepa.domain.user.account.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AdminReadService(
    private val accountRepository: AccountRepository,
) {

    fun getJoinTrainer(page: Int, limit: Int, keyword: String?): PageResponse {
        val findAllTrainers = keyword?.let {
            accountRepository.findAllByEmailOrName(keyword, Role.TRAINER)
        } ?: accountRepository.findAllByRole(Role.TRAINER)
            .map { JoinTrainers(it.name, it.birth, it.createdAt) }

        val totalCount = findAllTrainers.size.toLong()
        val endIndexTemp = page * limit + limit
        val endIndex = if(endIndexTemp >= totalCount) {
            totalCount - 1
        }else {
            endIndexTemp
        }

        val startIndex = page * limit
        val result = findAllTrainers.slice(startIndex..endIndex.toInt())
        return PageResponse.toResponse(result, totalCount ,page,limit)
    }
}
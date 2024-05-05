package com.kepa.domain.user.account

import com.kepa.application.user.admin.dto.request.enums.Sort
import com.kepa.domain.user.account.QAccount.account
import com.kepa.domain.user.enums.Role
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository


@Repository
class AccountQueryDslRepositoryImpl(
    private val query: JPAQueryFactory,
) : AccountQueryDslRepository {
    override fun findAllByEmailOrNameOrPhone(keyword: String?, role: Role, sort: Sort): List<Account> {
        return query.selectFrom(account)
            .where(eqKeyword(keyword))
            .orderBy(getSort(sort))
            .fetch()
    }

    fun eqKeyword(keyword: String?) : BooleanExpression? {
        return keyword?.let {
             account.email.eq(keyword).or(account.phone.eq(keyword).or(account.name.eq(keyword)))
        }
    }

    fun getSort(sort: Sort):  OrderSpecifier<*> {
        if(sort == Sort.DESC) {
            return OrderSpecifier(Order.DESC, account.createdAt)
        }
         return OrderSpecifier(Order.ASC, account.createdAt)
    }

}
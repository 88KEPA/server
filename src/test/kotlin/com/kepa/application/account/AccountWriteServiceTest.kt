package com.kepa.application.account

import com.kepa.application.account.dto.request.AccountJoin
import com.kepa.common.exception.KepaException
import com.kepa.domain.account.AccountRepository
import com.kepa.domain.account.Gender
import com.kepa.domain.account.LoginType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class AccountWriteServiceTest(
    @Autowired private val accountWriteService: AccountWriteService,
    @Autowired private val accountRepository: AccountRepository,
) : BehaviorSpec({
    Given("회원가입에서") {
        val name = "테스트"
        val loginId = "test"
        val email = "test@naver.com"
        val password = "test"
        val phone = "01012341234"
        val confirmPassword = "test"
        val zipCode = "zipcode"
        val jibunAddress = "jibunAddress"
        val jibunAddressDetail = "jibunAddressDetail"
        val roadAddress = "roadAddress"
        val roadAddressDetail = "roadAddressDetail"
        val gender: Gender = Gender.MALE
        val loginType: LoginType = LoginType.ORIGIN
        val birth = LocalDate.of(1928,1,5)
        val accountJoin = AccountJoin(
            name = name,
            loginId = loginId,
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            phone = phone,
            zipCode = zipCode,
            jibunAddress = jibunAddress,
            jibunAddressDetail = jibunAddressDetail,
            roadAddress = roadAddress,
            roadAddressDetail = roadAddressDetail,
            gender = gender,
            loginType = loginType,
            birth = birth
        )
        val savedAccount = accountRepository.save(accountJoin.create())
        When("비밀번호와 비밀번호 확인이 다르면") {
            val diffConfirmPassword = "diffPassword"
            val accountJoinPasswordCheck = AccountJoin(
                name = name,
                loginId = loginId,
                email = email,
                password = password,
                confirmPassword = diffConfirmPassword,
                phone = phone,
                zipCode = zipCode,
                jibunAddress = jibunAddress,
                jibunAddressDetail = jibunAddressDetail,
                roadAddress = roadAddress,
                roadAddressDetail = roadAddressDetail,
                gender = gender,
                loginType = loginType,
                birth = birth
            )
            Then("예외가 발생한다") {
                shouldThrow<KepaException> {
                    accountWriteService.join(accountJoinPasswordCheck)
                }
            }
        }
        When("이미 가입된 아이디일 경우") {
            val alreadyLoginId = "test"
            Then("예외가 발생한다.") {
                shouldThrow<KepaException> {
                    println(savedAccount.loginId)
                    require(savedAccount.loginId != alreadyLoginId) {
                        accountWriteService.join(accountJoin)
                    }
                }
            }
        }
        When("이미 가입된 이메일일 경우 경우") {
            val alreadyEmail = "test@naver.com"
            Then("예외가 발생한다.") {
                shouldThrow<KepaException> {
                    require(savedAccount.email != alreadyEmail) {
                        accountWriteService.join(accountJoin)
                    }
                }
            }
        }
    }
})

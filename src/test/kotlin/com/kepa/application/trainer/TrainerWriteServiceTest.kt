package com.kepa.application.trainer

import com.kepa.application.user.trainer.dto.request.TrainerJoin
import com.kepa.application.user.trainer.TrainerWriteService
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.trainer.TrainerRepository
import com.kepa.domain.user.trainer.Gender
import com.kepa.domain.user.trainer.LoginType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDate

@SpringBootTest
class TrainerWriteServiceTest(
    @Autowired private val trainerWriteService: TrainerWriteService,
    @Autowired private val trainerRepository: TrainerRepository,
    @Autowired private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : BehaviorSpec({
    Given("회원가입에서") {
        val name = "테스트"
        val email = "test@naver.com"
        val password = "test"
        val phone = "01012341234"
        val confirmPassword = "test"
        val zipCode = "zipcode"
        val jibunAddress = "jibunAddress"
        val jibunAddressDetail = "jibunAddressDetail"
        val gender: Gender = Gender.MALE
        val loginType: LoginType = LoginType.ORIGIN
        val birth = LocalDate.of(1928,1,5)
        val trainerJoin = TrainerJoin(
            name = name,
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            phone = phone,
            address = zipCode,
            addressMeta = jibunAddress,
            addressDetail = jibunAddressDetail,
            gender = gender,
            loginType = loginType,
            birth = birth
        )
        val savedAccount = trainerRepository.save(trainerJoin.create(bCryptPasswordEncoder.encode(password)))
        When("비밀번호와 비밀번호 확인이 다르면") {
            val diffConfirmPassword = "diffPassword"
            val trainerJoinPasswordCheck = TrainerJoin(
                name = name,
                email = email,
                password = password,
                confirmPassword = diffConfirmPassword,
                phone = phone,
                address = zipCode,
                addressMeta = jibunAddress,
                addressDetail = jibunAddressDetail,
                gender = gender,
                loginType = loginType,
                birth = birth
            )
            Then("예외가 발생한다") {
                shouldThrow<KepaException> {
                    trainerWriteService.join(trainerJoinPasswordCheck)
                }
            }
        }
        When("이미 가입된 이메일일 경우 경우") {
            val alreadyEmail = "test@naver.com"
            Then("예외가 발생한다.") {
                shouldThrow<KepaException> {
                    require(savedAccount.email != alreadyEmail) {
                        trainerWriteService.join(trainerJoin)
                    }
                }
            }
        }
    }
})

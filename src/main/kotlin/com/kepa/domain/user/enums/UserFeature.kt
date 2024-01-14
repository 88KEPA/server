
enum class Gender(
    val value: String
) {
    MALE("남성"), FEMALE("여성")
}

enum class LoginType(
    val type: String
) {
    KAKAO("카카오"), ORIGIN("사이트 회원가입")
}

enum class Role(
    val type: String
) {
    ADMIN("관리자"), USER("일반 사용자"), TRAINER("트레이너")
}

enum class CertType(
    val type: String,
) {
    EMAIL("이메일"), PHONE("핸드폰")
}


import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("수정할 사용자 정보")
data class UpdateInfo(
    @ApiModelProperty("이메일")
    val email: String,
    @ApiModelProperty("주소")
    val address: String,
    @ApiModelProperty("부가 주소정보")
    val addressMeta: String,
    @ApiModelProperty("상세주소")
    val addressDetail: String,
    @ApiModelProperty("핸드폰 번호")
    val phone: String,
)
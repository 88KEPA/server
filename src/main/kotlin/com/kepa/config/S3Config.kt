package com.kepa.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * packageName    : com.kepa.config
 * fileName       : S3Config
 * author         : hoewoonjeong
 * date           : 3/2/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/2/24        hoewoonjeong               최초 생성
 */
@Configuration
class S3Config(
    @Value("\${cloud.aws.credentials.access-key}")
    val accessKey: String,
    @Value("\${cloud.aws.credentials.secret-key}")
    val secretKey: String,
    @Value("\${cloud.aws.region.static}")
    val region: String
) {
    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val credentials = BasicAWSCredentials(accessKey, secretKey)

        return AmazonS3ClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .build() as AmazonS3Client
    }
}
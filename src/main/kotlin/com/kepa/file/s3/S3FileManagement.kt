package com.kepa.file.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.kepa.file.validate.FileValidate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * packageName    : com.kepa.s3
 * fileName       : S3FileManagement
 * author         : hoewoonjeong
 * date           : 3/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/24        hoewoonjeong               최초 생성
 */
@Component
class S3FileManagement(
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,
    private val amazonS3: AmazonS3,
) {
    fun uploadImages(multipartFile: List<MultipartFile>): List<String> {
        return multipartFile.map {
            val originalFilename = it.originalFilename ?: ""
            FileValidate.checkImageFormat(originalFilename)
            val fileName = "${UUID.randomUUID()}-${originalFilename}"
            val objectMetadata = ObjectMetadata()
            objectMetadata.contentType = "/image/${getFileExtension(originalFilename)}"
            objectMetadata.contentLength = it.inputStream.available().toLong()
            amazonS3.putObject(bucket, fileName, it.inputStream, objectMetadata)
            amazonS3.getUrl(bucket, fileName).toString()
        }
    }

    fun uploadImage(multipartFile: MultipartFile): String {
        val originalFilename = multipartFile.originalFilename ?: ""
        FileValidate.checkImageFormat(originalFilename)
        val fileName = "${UUID.randomUUID()}-${originalFilename}"
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = "/image/${getFileExtension(originalFilename)}"
        objectMetadata.contentLength = multipartFile.inputStream.available().toLong()
        amazonS3.putObject(bucket, fileName, multipartFile.inputStream, objectMetadata)
        return getFile(fileName)
    }

    fun getFile(fileName: String): String {
        return amazonS3.getUrl(bucket, fileName).toString()
    }

    private fun getFileExtension(fileName: String): String {
        val extensionIndex = fileName.lastIndexOf('.')
        return fileName.substring(extensionIndex + 1)

    }

}
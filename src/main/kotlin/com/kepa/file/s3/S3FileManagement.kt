package com.kepa.file.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
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
    @Value("\${cloud.aws.credentials.access-key}")
    val accessKey: String,
) {
    companion object {
        const val TYPE_IMAGE = "image"
    }
    fun uploadImage(multipartFile: MultipartFile): String {
        val originalFilename = multipartFile.originalFilename
            ?: throw KepaException(ExceptionCode.WRONG_FORMAT_FILE_NAME)
        FileValidate.checkImageFormat(originalFilename)
        val fileName = "${UUID.randomUUID()}-${originalFilename}"
        val objectMetadata = setFileDateOption(
            type = TYPE_IMAGE,
            file = getFileExtension(originalFilename),
            multipartFile = multipartFile
        )
        amazonS3.putObject(bucket, fileName, multipartFile.inputStream, objectMetadata)
        return fileName
    }

    fun getFile(fileName: String): String {
        return amazonS3.getUrl(bucket,fileName).toString()
    }

    fun delete(fileName: String) {
        amazonS3.deleteObject(bucket,fileName)
    }

    private fun getFileExtension(fileName: String): String {
        val extensionIndex = fileName.lastIndexOf('.')
        return fileName.substring(extensionIndex + 1)
    }

    private fun setFileDateOption(
        type: String,
        file: String,
        multipartFile: MultipartFile
    ): ObjectMetadata {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = "/${type}/${getFileExtension(file)}"
        objectMetadata.contentLength = multipartFile.inputStream.available().toLong()
        return objectMetadata
    }


}
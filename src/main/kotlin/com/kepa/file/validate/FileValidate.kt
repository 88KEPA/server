package com.kepa.file.validate

import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException

/**
 * packageName    : com.kepa.file.validate
 * fileName       : FileValidate
 * author         : hoewoonjeong
 * date           : 3/9/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/9/24        hoewoonjeong               최초 생성
 */
class FileValidate {

    companion object {
        private val IMAGE_EXTENSIONS: List<String> = listOf("jpg", "png", "gif", "webp")
        private val FILE_EXTENSIONS: List<String> = listOf("pdf", "hwp", "xlsx", "pptx", "docx")
        fun checkImageFormat(fileName: String) {
            val extensionIndex = fileName.lastIndexOf('.')
            if(extensionIndex == -1) {
                throw KepaException(ExceptionCode.NOT_EXSITS_FILE_EXTENSION)
            }
            val extension = fileName.substring(extensionIndex + 1)
            require(IMAGE_EXTENSIONS.contains(extension)) {
                throw KepaException(ExceptionCode.NOT_SUPPORT_FILE_EXTENSION)
            }
        }

        fun checkFileFormat(fileName: String) {
            val extensionIndex = fileName.lastIndexOf('.')
            if(extensionIndex == -1) {
                throw KepaException(ExceptionCode.NOT_EXSITS_FILE_EXTENSION)
            }
            val extension = fileName.substring(extensionIndex + 1).toLowerCase()
            require(FILE_EXTENSIONS.contains(extension)) {
                throw KepaException(ExceptionCode.NOT_SUPPORT_FILE_EXTENSION)
            }
        }
    }
}
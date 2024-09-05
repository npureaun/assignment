package team.sparta.kotlin.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import team.sparta.kotlin.exception.dto.ErrorDto
import java.lang.NullPointerException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotFoundException(e: ModelNotFoundException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto(e.message, "404"))
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto(e.message, "400"))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto(e.message, "400"))
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorDto(e.message, "500"))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorDto(e.message, "403"))
    }

    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointerExceptionException(e: NullPointerException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorDto(e.message, "401"))
    }
}
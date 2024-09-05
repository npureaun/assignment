package team.sparta.kotlin.exception

data class AccessDeniedException(
    private val text: String
) : SecurityException(
    "Access Denied: $text"
)
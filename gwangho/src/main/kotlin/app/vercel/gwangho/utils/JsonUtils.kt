package app.vercel.gwangho.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@PublishedApi
internal val objectMapper = jacksonObjectMapper()

@PublishedApi
internal val staticResourcePath: Path = Paths.get("src", "main", "resources", "static")
    .toAbsolutePath()
    .normalize()

fun jsonSave(relativePath: String, data: Any) {
    val targetPath = resolveJsonPath(relativePath)
    Files.createDirectories(targetPath.parent)
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(targetPath.toFile(), data)
}

inline fun <reified T> jsonLoad(relativePath: String): T {
    val targetPath = resolveJsonPath(relativePath)

    require(Files.exists(targetPath)) {
        "JSON file not found: $targetPath"
    }

    return objectMapper.readValue(targetPath.toFile(), object : TypeReference<T>() {})
}

@PublishedApi
internal fun resolveJsonPath(relativePath: String): Path {
    require(relativePath.isNotBlank()) {
        "relativePath must not be blank"
    }

    val normalizedPath = relativePath.replace("\\", "/")
    val fileName = if (normalizedPath.endsWith(".json")) normalizedPath else "$normalizedPath.json"
    val targetPath = staticResourcePath.resolve(fileName).normalize()

    require(targetPath.startsWith(staticResourcePath)) {
        "Path must stay inside src/main/resources/static"
    }

    return targetPath
}

package kz.efc.kgd.astana1.astana1.logs.controller

import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


@RestController
@CrossOrigin
class LogController {

    @RequestMapping(path = ["/download"], method = [RequestMethod.GET])
    @Throws(IOException::class)
    fun download(@RequestParam("name") name: String): ResponseEntity<Resource?>? {
//        val file = File(SERVER_LOCATION.toString() + File.separator + image + EXTENSION)
        val file = File("logs/$name")
        val header = HttpHeaders()
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$name")
        header.add("Cache-Control", "no-cache, no-store, must-revalidate")
        header.add("Pragma", "no-cache")
        header.add("Expires", "0")
        val path: Path = Paths.get(file.getAbsolutePath())
        val resource = ByteArrayResource(Files.readAllBytes(path))
        return ResponseEntity.ok()
            .headers(header)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource)
    }
}
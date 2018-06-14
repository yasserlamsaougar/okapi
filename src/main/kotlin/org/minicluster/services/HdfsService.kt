package org.minicluster.services

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.javalin.ApiBuilder.*
import io.javalin.Context
import io.javalin.Javalin
import org.apache.http.HttpStatus
import org.minicluster.helpers.hdfs.HdfsHelper
import java.nio.file.Paths
import javax.activation.MimetypesFileTypeMap

class HdfsService(val kodein: Kodein) : Service {

    val hdfsHelper: HdfsHelper = kodein.instance()
    override fun setup(app: Javalin) {
        app.routes {
            path("hdfs") {
                path("file") {
                    path("upload") {
                        post(this::uploadFile)
                    }
                    path("download") {
                        get(this::downloadFile)
                    }
                    path("list") {
                        get(this::listFiles)
                    }
                    path("read") {
                        get(this::readFile)
                    }
                    delete(this::deletePath)
                }
            }
        }
    }

    fun listFiles(ctx: Context) {
        val path = ctx.queryParam("path")!!
        val filter = ctx.queryParam("prefix").orEmpty()
        val recursive = ctx.queryParamOrDefault("recursive", "false").toBoolean()
        val listOfFiles = hdfsHelper.listFiles(path, recursive) {
            it.name.startsWith(prefix = filter)
        }
        ctx.json(mapOf(
                "files" to listOfFiles.map{
                    it.name
                })
        )
    }

    fun deletePath(ctx: Context) {
        val path = ctx.queryParam("path")!!
        val recursive = ctx.queryParamOrDefault("recursive", "false").toBoolean()
        if (hdfsHelper.fileExists(path)) {
            hdfsHelper.deletePath(path, recursive)
            ctx.status(HttpStatus.SC_OK)
        } else {
            ctx.status(HttpStatus.SC_NOT_FOUND)
            ctx.json(mapOf(
                    "message" to "$path doesn't exist"
            ))
        }
    }

    fun uploadFile(ctx: Context) {
        ctx.uploadedFiles("files").forEach {
            val dest = ctx.queryParamOrDefault("dest", it.name)
            hdfsHelper.writeStream(it.content, dest, true)
        }
        ctx.status(HttpStatus.SC_CREATED)
    }


    fun downloadFile(ctx: Context) {
        val path = ctx.queryParam("path")!!
        if (hdfsHelper.fileExists(path)) {
            if (hdfsHelper.isSimpleFile(path)) {
                ctx.contentType(MimetypesFileTypeMap().getContentType(Paths.get(path).fileName.toFile()))
                ctx.header("Content-Disposition", "attachment; filename=\"${Paths.get(path).fileName}\"")
                ctx.result(hdfsHelper.readStream(path))
            } else {
                ctx.status(HttpStatus.SC_BAD_REQUEST)
                ctx.json(mapOf(
                        "message" to "$path is a directory"
                ))
            }
        } else {
            ctx.status(HttpStatus.SC_NOT_FOUND)
            ctx.json(mapOf(
                    "message" to "$path doesn't exist"
            ))
        }
    }

    fun readFile(ctx: Context) {
        val path = ctx.queryParam("path")!!
        if (hdfsHelper.fileExists(path)) {
            if (hdfsHelper.isSimpleFile(path)) {
                ctx.json(mapOf(
                        "content" to hdfsHelper.readText(path)))
            } else {
                ctx.status(HttpStatus.SC_BAD_REQUEST)
                ctx.json(mapOf(
                        "message" to "$path is a directory"
                ))
            }
        } else {
            ctx.status(HttpStatus.SC_NOT_FOUND)
            ctx.json(mapOf(
                    "message" to "$path doesn't exist"
            ))
        }
    }

}
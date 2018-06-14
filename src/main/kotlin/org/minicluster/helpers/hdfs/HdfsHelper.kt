package org.minicluster.helpers.hdfs

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.FileUtil
import org.apache.hadoop.fs.Path
import org.minicluster.helpers.config.ConfigHelper
import org.minicluster.helpers.kerberos.AuthHelper
import java.io.File
import java.io.IOException
import java.io.InputStream


class HdfsHelper(val kodein: Kodein) {
    val configuration = Configuration(true)
    private val configHelper: ConfigHelper = kodein.instance()
    private val authHelper: AuthHelper = kodein.instance()
    val fs: FileSystem

    init {
        configuration.addResource(Path(configHelper.servicesConfig.coreSite().toUri()))
        configuration.addResource(Path(configHelper.servicesConfig.hdfsSite().toUri()))
        authHelper.authenticate()
        fs = FileSystem.get(configuration)
        Runtime.getRuntime().addShutdownHook(Thread {
            fs.close()
        })
    }

    fun listFiles(path: String, recursive: Boolean = false, filter: (Path) -> Boolean = { true }): MutableList<Path> {
        authHelper.authenticate()
        val listOfFiles = mutableListOf<Path>()
        listFilesRecu(Path(path), listOfFiles, recursive, filter)
        return listOfFiles
    }

    private fun listFilesRecu(path: Path, output: MutableList<Path>, recursive: Boolean, filter: (Path) -> Boolean) {
        val hadoopListFiles = fs.listStatus(path, filter)
        val partitions = hadoopListFiles.partition { it.isFile }
        partitions.first.forEach { output.add(it.path) }
        partitions.second.forEach {
            try {
                if(recursive) {
                    listFilesRecu(it.path, output, recursive, filter)
                }
                else {
                    output.add(it.path)
                }
            } catch (e: IOException) {

            }
        }
    }

    fun deletePath(path: String, recursive: Boolean = false) {
        authHelper.authenticate()
        fs.delete(Path(path), recursive)
    }

    fun writeFile(inputFile: String, dest: String, overwrite: Boolean = true): Boolean {
        authHelper.authenticate()
        if (overwrite) {
            fs.delete(Path(dest), true)
        }
        return FileUtil.copy(File(inputFile), fs, Path(dest), false, configuration)
    }

    fun readText(inputFile: String): String {
        readStream(inputFile).use {
            return it.reader().readText()
        }
    }

    fun readStream(inputFile: String): InputStream {
        authHelper.authenticate()
        return fs.open(Path(inputFile))
    }

    fun writeStream(inputStream: InputStream, dest: String, overwrite: Boolean = true): Long {
        authHelper.authenticate()
        val file = fs.create(Path(dest), overwrite)
        val bytesWritten = inputStream.copyTo(file)
        file.close()
        return bytesWritten
    }

    fun fileExists(inputFile: String): Boolean {
        authHelper.authenticate()
        return fs.exists(Path(inputFile))
    }

    fun isSimpleFile(inputFile: String): Boolean {
        authHelper.authenticate()
        return fs.isFile(Path(inputFile))
    }

}
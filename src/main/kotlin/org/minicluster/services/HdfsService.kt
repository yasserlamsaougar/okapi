package org.minicluster.services

import com.github.salomonbrys.kodein.Kodein
import io.javalin.Javalin

class HdfsService(val kodein: Kodein) : Service {

    override fun setup(app: Javalin) {
        val ns = "hdfs"
        app.get("/$ns/hello") {
            it.result("hello $ns")
        }
    }
}
package org.minicluster.services

import io.javalin.Javalin

interface Service {
    fun setup(app: Javalin)
}
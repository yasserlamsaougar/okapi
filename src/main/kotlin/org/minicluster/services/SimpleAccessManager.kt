package org.minicluster.services

import io.javalin.Context
import io.javalin.Handler
import io.javalin.security.AccessManager
import io.javalin.security.Role

class SimpleAccessManager : AccessManager {

    override fun manage(handler: Handler, ctx: Context, permittedRoles: MutableList<Role>) {

    }

}
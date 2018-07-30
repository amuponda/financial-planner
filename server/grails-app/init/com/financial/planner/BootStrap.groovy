package com.financial.planner

class BootStrap {

    def init = { servletContext ->
        new Role(authority: "ROLE_USER").save()
    }
    def destroy = {
    }
}

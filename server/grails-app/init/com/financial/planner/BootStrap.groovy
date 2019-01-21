package com.financial.planner

class BootStrap {

    def init = { servletContext ->
        if (!Role.findByAuthority('ROLE_USER')) {
            new Role(authority: 'ROLE_USER').save(flush: true)
        }
    }

    def destroy = {
    }
}

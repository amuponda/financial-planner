package com.financial.planner

class UrlMappings {

    static mappings = {
        "/api/signup"(controller: 'user', action: 'signup')
        "/api/user"(controller: 'user', action: 'index')
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}

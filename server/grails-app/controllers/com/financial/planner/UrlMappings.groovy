package com.financial.planner

import grails.util.Environment

class UrlMappings {

    static mappings = {
        if ( Environment.current == Environment.PRODUCTION ) {
            '/'(uri: '/index.html')
        } else {
            '/'(controller: 'application', action:'index')
        }
        "/api/signup"(controller: 'user', action: 'signup')
        "/api/user"(controller: 'user', action: 'index')

        "/api/account/create"(controller: 'account', action: 'createAccount')
        "/api/accounts"(controller: 'account', action: 'getUserAccounts')

        "/api/iae"(controller: 'incomeAndExpenses', action: 'getIncomeAndExpenses')
        "/api/iae/create"(controller: 'incomeAndExpenses', action: 'create')
        "/api/iae/$id"(controller: 'incomeAndExpenses', action: 'getBill', method: "GET")
        "/api/iae/$id"(controller: 'incomeAndExpenses', action: 'update', method: "PUT")

        "/api/iae/categories"(controller: 'utility', action: 'categories')
        "/api/iae/regularity"(controller: 'utility', action: 'regularity')

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}

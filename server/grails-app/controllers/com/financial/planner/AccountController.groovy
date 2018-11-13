package com.financial.planner

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import org.springframework.http.HttpStatus

class AccountController extends RestfulController<Account> {

    static responseFormats = ['json', 'xml']

    def springSecurityService

    AccountController() {
        super(Account)
    }

    def index() { }

    @Secured(['ROLE_USER'])
    def create() {
        User user = springSecurityService.getCurrentUser()
        Account account = new Account(name: params.name, user: user)
        if (account.hasErrors()) {
            respond account.errors
            return
        }
        saveResource(account)
        respond(account, status: HttpStatus.CREATED)
    }
}

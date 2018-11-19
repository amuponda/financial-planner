package com.financial.planner

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import org.springframework.http.HttpStatus

@Secured(['ROLE_USER'])
class AccountController extends RestfulController<Account> {

    static responseFormats = ['json', 'xml']

    def springSecurityService

    AccountController() {
        super(Account)
    }

    def index() { }

    def create() {
        User user = springSecurityService.getCurrentUser()
        Account account = new Account(name: params.name, user: user)
        if (!account.validate()) {
            respond account.errors
            return
        }
        saveResource(account)
        respond(account, status: HttpStatus.CREATED)
    }

    def userAccounts() {
        List<Account> accounts = Account.findAllByUser(springSecurityService.currentUser)
        respond(accounts)
    }
}

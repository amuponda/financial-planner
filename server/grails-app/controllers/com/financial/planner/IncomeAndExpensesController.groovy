package com.financial.planner

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus

@Secured(['ROLE_USER'])
class IncomeAndExpensesController extends RestfulController<IncomeAndExpenses> {
	static responseFormats = ['json']

    def springSecurityService
    def messageSource

    IncomeAndExpensesController() {
        super(IncomeAndExpenses)
    }

    @Override
    def create() {
        IncomeAndExpenses iae = new IncomeAndExpenses(params)
        if (iae.hasErrors()) {
            respond iae.errors
        }
        withOwnAccount(iae.account) {
            saveResource(iae)
            respond(iae, status: HttpStatus.CREATED)
        }
    }

    def withOwnAccount(Account account, closure) {
        if (account.user == springSecurityService.currentUser) {
            closure()
        } else {
            String message = messageSource.getMessage('springSecurity.forbidden', null, LocaleContextHolder.locale)
            respond(message, status: HttpStatus.FORBIDDEN)
        }
    }
}

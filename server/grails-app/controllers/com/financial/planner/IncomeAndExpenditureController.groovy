package com.financial.planner

import grails.rest.*
import grails.converters.*
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus

class IncomeAndExpenditureController extends RestfulController<IncomeAndExpenditure> {
	static responseFormats = ['json']

    def springSecurityService
    def messageSource

    IncomeAndExpenditureController() {
        super(IncomeAndExpenditure)
    }

    def createIncomeOrExpenditure() {
        IncomeAndExpenditure iae = new IncomeAndExpenditure(params)
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

package com.financial.planner

import com.financial.planner.enums.Type
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
        iae.nextDue = iae.startDate
        if (iae.hasErrors()) {
            respond iae.errors
        }
        withOwnAccount(iae.account) {
            saveResource(iae)
            respond(iae, status: HttpStatus.CREATED)
        }
    }

    def getBills() {
        List bills = IncomeAndExpenses.findAllByTypeAndAccountInList(Type.EXPENSE, Account.findAllByUser(springSecurityService.currentUser))
        respond(bills)
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

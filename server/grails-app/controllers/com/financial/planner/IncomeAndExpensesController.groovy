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
        iae.nextDue = iae.startDate
        if (iae.hasErrors()) {
            respond iae.errors
        }
        withOwnAccount(iae.account) {
            saveResource(iae)
            respond(iae, status: HttpStatus.CREATED)
        }
    }

    @Override
    def update() {
        println("${params}")
        withBill(params.long('id')) { IncomeAndExpenses bill ->
            withOwnAccount(bill.account) {
                super.update()
            }
        }
    }

    def getIncomeAndExpenses() {
        List<Account> accounts = Account.findAllByUser(springSecurityService.currentUser)
        List bills = accounts ? IncomeAndExpenses.findAllByAccountInList(accounts) : []
        respond(bills)
    }

    def getBill(Long id) {
        withBill(id) { IncomeAndExpenses bill ->
            withOwnAccount(bill.account) {
                List<Transaction> transactions = Transaction.findAllByBill(bill)
                respond([bill: bill, transactions: transactions])
            }
        }
    }

    def withBill(Long id, closure) {
        IncomeAndExpenses bill = IncomeAndExpenses.get(id)
        if (bill == null) {
            super.notFound()
            return
        } else {
            closure(bill)
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

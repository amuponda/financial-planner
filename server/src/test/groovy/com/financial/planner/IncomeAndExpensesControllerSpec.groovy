package com.financial.planner

import grails.buildtestdata.TestDataBuilder
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest
import groovy.util.logging.Slf4j
import org.springframework.context.MessageSource
import spock.lang.Unroll

@Slf4j
class IncomeAndExpensesControllerSpec extends HibernateSpec implements ControllerUnitTest<IncomeAndExpensesController>,  TestDataBuilder {
    List<Class> getDomainClasses() {
        [Account, Transaction]
    }

    @Unroll("#testCase")
    void "add a new bill to the database"() {
        setup:
        User user = build(User)
        build(Account, user: user, name: 'ING')

        User notMe = build(User, username: 'notMe@test.com')
        build(Account, user: notMe, name: 'CBA')

        controller.springSecurityService = Stub(SpringSecurityService) {
            getCurrentUser() >> user
        }
        controller.messageSource = Stub(MessageSource) {
            getMessage(_, _, _) >> 'You do not have permissions to perform that action.'
        }

        when: "Request to create a new bill is made"
        request.method = 'POST'
        Map bill = ['account.id': accountId, name: 'Test Bill', amount: 20.0g, category: 'GROCERIES', repeats: 'WEEKLY',
                    startDate: new Date().format('dd/MM/yyyy'), endDate: (new Date() + 28).format('dd/MM/yyyy'), type: 'EXPENSE']

        bill.each {k, v -> params[k] = v }
        controller.create()

        then:
        response.status == responseStatus

        where:
        accountId   || responseStatus   || testCase
        1           || 201              || "Create bill on one of your accounts"
        2           || 403              || "You attempt to create a new bill for an account you don't own"
    }

    void "request a bill"() {
        setup:
        User user = build(User)
        Account account = build(Account, user: user)
        IncomeAndExpenses bill = build(IncomeAndExpenses, account: account)
        build(Transaction, bill: bill)

        controller.springSecurityService = Stub(SpringSecurityService) {
            getCurrentUser() >> user
        }

        when: "Request a bill"
        request.method = 'GET'
        params.id = bill.id
        controller.getBill()

        then:
        response.status == 200
        response.json.bill.id == bill.id
        response.json.transactions.size() == 1
    }

    void "requesting a bill that does not exist"() {
        setup:
        User user = build(User)

        controller.springSecurityService = Stub(SpringSecurityService) {
            getCurrentUser() >> user
        }

        when: "Request a bill that does not exist"
        request.method = 'GET'
        params.id = 200
        controller.getBill()

        then: "not found"
        response.status == 404
    }
}
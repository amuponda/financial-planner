package com.financial.planner

import grails.buildtestdata.TestDataBuilder
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest
import groovy.util.logging.Slf4j
import org.springframework.context.MessageSource
import spock.lang.Shared
import spock.lang.Unroll

@Slf4j
class IncomeAndExpensesControllerSpec extends HibernateSpec implements ControllerUnitTest<IncomeAndExpensesController>,  TestDataBuilder {
    @Shared Account notMine
    List<Class> getDomainClasses() {
        [Account]
    }

    @Unroll("#testCase")
    void "add a new bill to the database"() {
        setup:
        User user = build(User)
        Account account = build(Account, user: user, name: 'ING')

        User notMe = build(User, username: 'notMe@test.com')
        notMine = build(Account, user: notMe, name: 'CBA')

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
}
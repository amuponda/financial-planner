package com.financial.planner

import com.financial.planner.enums.Category
import com.financial.planner.enums.Repeat
import grails.buildtestdata.TestDataBuilder
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest

class IncomeAndExpensesControllerSpec extends HibernateSpec implements ControllerUnitTest<IncomeAndExpensesController>,  TestDataBuilder {

    def setup() {
    }

    def cleanup() {
    }

    List<Class> getDomainClasses() {
        [Account]
    }

    void "add a new bill to the database"() {
        setup:
        User user = build(User)
        Account account = build(Account, user: user, name: 'ING')
        controller.springSecurityService = Stub(SpringSecurityService) {
            getCurrentUser() >> user
        }

        when: "Request to create a new bill is made"
        request.method = 'POST'
        Map bill = ['account.id': account.id, name: 'Test Bill', amount: 20.0g, category: Category.GROCERIES, repeats: 'WEEKLY',
                    startDate: new Date().format('dd/MM/yyyy'), endDate: (new Date() + 28).format('dd/MM/yyyy'), type: 'EXPENSE']

        bill.each {k, v -> params[k] = v }
        controller.create()

        then:
        response.status == 201
        /*
        when: "You attempt to create a new bill for an account you don't own"
        User notMe = build(User, username: 'notMe@test.com')
        Account notMine = build(Account, user: notMe, name: 'CBA')
        params['account.id'] = notMine.id

        controller.create()

        then: "Access is denied"
        response.status == 403
        */
    }
}
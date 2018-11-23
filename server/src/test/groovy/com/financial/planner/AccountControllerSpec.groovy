package com.financial.planner

import grails.buildtestdata.TestDataBuilder
import grails.plugin.springsecurity.SpringSecurityService
import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Unroll

class AccountControllerSpec extends HibernateSpec implements ControllerUnitTest<AccountController>, TestDataBuilder {

    def setup() {
    }

    def cleanup() {
    }

    List<Class> getDomainClasses() {
        [Account]
    }

    @Unroll("Test case: #testCase")
    void "create a new back account"() {
        setup:
        User user = build(User)
        build(Account, user: user, name: 'Already Exists')
        controller.springSecurityService = Stub(SpringSecurityService) {
            getCurrentUser() >> user
        }

        when: "request to create new account is made"
        request.method = 'POST'
        params['name'] = name
        controller.createAccount()

        then:
        response.status == status

        where:
        name                || status   || testCase
        'CBA Smart Access'  || 201      || 'valid account name'
        ''                  || 422      || 'account name cannot be null'
        'Already Exists'    || 422      || 'Account names should be unique within the user context'
    }
}
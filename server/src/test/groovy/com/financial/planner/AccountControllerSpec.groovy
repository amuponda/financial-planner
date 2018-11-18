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

    @Unroll("Account name: #name")
    void "create a new back account"() {
        setup:
        User user = build(User)
        controller.springSecurityService = Stub(SpringSecurityService) {
            getCurrentUser() >> user
        }

        when: "request to create new account is made"
        request.method = 'POST'
        params['name'] = name
        controller.create()

        then:
        response.status == status

        where:
        name                || status
        'CBA Smart Access'  || 201
        ''                  || 422
    }
}
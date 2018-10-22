package com.financial.planner

import grails.gorm.transactions.Rollback
import grails.plugins.rest.client.RestBuilder
import grails.testing.mixin.integration.Integration
import groovy.json.JsonBuilder
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

//@see com.financial.planner.RestAuthenticationFailureHandler

@Integration
@Rollback
class UserControllerIntSpec extends Specification{

    @Shared RestBuilder rest = new RestBuilder()

    def setup() {
        new User(username: 'accLocked@email.com', password: 'password', firstName: 'Test', lastName: 'Tester', accountLocked: true).save()
        new User(username: 'testlogin@email.com', password: 'password', firstName: 'Test', lastName: 'Tester').save(flush: true)
    }


    /*
    @Unroll("#testCase")
    def "test error handling of login attempts"() {
        setup:
        Map credentials = [username: email, password: 'password']
        JsonBuilder builder = new JsonBuilder(credentials)

        when:
        def resp = rest.post("http://localhost:${serverPort}/api/login") {
            accept('application/json')
            contentType('application/json')
            json(builder.toString())
        }

        then:
        resp.status == status
        resp.json.message == message

        where:
        email                   | status    | message                           | testCase
        'notexist@email.com'    | 401       | "Your credentials are incorrect." | "bad credentials"
        //'accLocked@email.com'   | 401       | "Authentication failed."          | "anything else other than bad credentials"
        //'testlogin@email.com'   | 200       | null                              | "successful login"
    }
    */
}

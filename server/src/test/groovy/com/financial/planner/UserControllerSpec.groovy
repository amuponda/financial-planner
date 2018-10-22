package com.financial.planner

import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest
import groovy.json.JsonBuilder
import spock.lang.Unroll

class UserControllerSpec extends HibernateSpec implements ControllerUnitTest<UserController> {

    def setup() {
        new Role(authority: "ROLE_USER").save()
    }

    def cleanup() {
    }

    @Unroll("#testCase")
    void "create a new user account"() {
        setup:
        Map user = [firstName: 'Test', lastName: 'Tester', username: email, password: "pass1234"]
        JsonBuilder builder = new JsonBuilder(user)

        when: "request to create new user is sent"
        request.method = 'POST'
        request.json = builder.toString()
        controller.signup()

        then:
        response.status == responseStatus
        response.json.id  == userId
        if (userId) UserRole.findByUser(User.get(userId)) != null

        where:
        email               | responseStatus    | userId    | testCase
        'a@testing.com.au'  | 201               | 1         | "valid email"
        'invalid'           | 422               | null      | "invalid email"

    }
}
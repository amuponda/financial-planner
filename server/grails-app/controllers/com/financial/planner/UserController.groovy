package com.financial.planner

import grails.gorm.transactions.Transactional
import grails.rest.*
import grails.converters.*

import static org.springframework.http.HttpStatus.CREATED

class UserController extends RestfulController<User> {
	static responseFormats = ['json', 'xml']

    UserController() {
        super(User)
    }

    def index() { }

    @Transactional
    def signup() {
        User user = createResource()
        user.validate()
        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors // STATUS CODE 422
            return
        }
        saveResource(user)
        UserRole.create(user, Role.findByAuthority('ROLE_USER'), true)
        respond user, status: CREATED
    }
}

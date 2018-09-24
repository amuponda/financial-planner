package com.financial.planner

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*

import static org.springframework.http.HttpStatus.CREATED

@Secured(['ROLE_USER'])
class UserController extends RestfulController<User> {
	static responseFormats = ['json', 'xml']

    UserController() {
        super(User)
    }

    def index() { }

    @Secured(['ROLE_ANONYMOUS'])
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

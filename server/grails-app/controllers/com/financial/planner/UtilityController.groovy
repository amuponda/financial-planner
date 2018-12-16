package com.financial.planner

import com.financial.planner.enums.Category
import com.financial.planner.enums.Repeat
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*

@Secured(['ROLE_USER'])
class UtilityController {

	static responseFormats = ['json']
	
    def categories() {
        List categories = Category.values().inject([]) { cats, cat ->
            cats << [name: "${cat}", label: cat.label]
        }
        respond categories
    }

    def regularity() {
        List repeats = Repeat.values().inject([]) { reps, rep ->
            reps << [name: "${rep}", label: rep.label]
        }
        respond repeats
    }
}

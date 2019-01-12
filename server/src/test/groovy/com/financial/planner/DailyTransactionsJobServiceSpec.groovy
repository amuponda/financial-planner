package com.financial.planner

import spock.lang.Specification
import static org.quartz.DateBuilder.translateTime

class DailyTransactionsJobServiceSpec extends Specification {

    void "test that job start date is set correctly"() {
        setup:
        DailyTransactionsJobService service = new DailyTransactionsJobService()
        when:
        service.buildTriggers()

        then: "The correct job time is set based on the set timezone"
        translateTime(service.triggers[0].startTime, service.timeZone, TimeZone.getDefault()).format('HH:mm') == '23:55'
    }
}

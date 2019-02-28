package com.financial.planner

import com.financial.planner.enums.Repeat
import spock.lang.Specification

class RepeatSpec extends Specification {

    def "test repeats same day each month" () {
        when: "I call getNextDue method on enum"
        Date currentNextDue = new Date().clearTime()
        Date nextDue = Repeat.SAME_DAY_EACH_MONTH.getNextDue(currentNextDue)

        Calendar now = Calendar.getInstance()
        now.setTime(currentNextDue)
        Calendar next = Calendar.getInstance()
        next.setTime(nextDue)

        then: "Bill should be due on the same day next month"
        now.get(Calendar.DAY_OF_MONTH) == next.get(Calendar.DAY_OF_MONTH)
        (now.get(Calendar.MONTH) + 1) % 11 == next.get(Calendar.MONTH)

    }
}

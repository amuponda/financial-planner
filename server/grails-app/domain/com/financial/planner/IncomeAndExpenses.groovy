package com.financial.planner

import com.financial.planner.enums.Category
import com.financial.planner.enums.Repeats
import com.financial.planner.enums.Type

class IncomeAndExpenditure {

    Date startDate
    Date endDate
    String name
    Repeats repeats
    BigDecimal amount
    Category category
    Type type

    static belongsTo = [account: Account]

    static constraints = {
        endDate nullable: true
        amount min: 0.0g
    }
}

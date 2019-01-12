package com.financial.planner

import com.financial.planner.enums.Category
import com.financial.planner.enums.Repeat
import com.financial.planner.enums.Type
import grails.databinding.BindUsing

class IncomeAndExpenses {

    Date startDate
    Date endDate
    Date nextDue
    String name
    BigDecimal amount
    @BindUsing({obj, source ->
        Repeat.valueOf(source['repeats'])
    })
    Repeat repeats
    @BindUsing({obj, source ->
        Category.valueOf(source['category'])
    })
    Category category
    @BindUsing({obj, source ->
        Type.valueOf(source['type'])
    })
    Type type

    static belongsTo = [account: Account]

    static constraints = {
        endDate nullable: true
        nextDue nullable: true
        amount min: 0.01g
    }
}

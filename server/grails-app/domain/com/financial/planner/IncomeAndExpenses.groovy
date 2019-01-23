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
        def rep = source['repeats']
        rep instanceof Repeat ? rep : Repeat.valueOf(rep)
    })
    Repeat repeats

    @BindUsing({obj, source ->
        def cat = source['category']
        cat instanceof Category ? cat : Category.valueOf(cat)
    })
    Category category

    @BindUsing({obj, source ->
        def type = source['type']
        type instanceof Type ? type : Type.valueOf(source['type'])
    })
    Type type

    static belongsTo = [account: Account]

    static constraints = {
        endDate nullable: true
        nextDue nullable: true
        amount min: 0.01g
    }
}

package com.financial.planner

class Account {

    String name
    BigDecimal balance = 0.0g

    static belongsTo = [user: User]

    static hasMany = [incomeAndExpenses: IncomeAndExpenses]

    static constraints = {
        name maxSize: 50,  unique: true
    }
}

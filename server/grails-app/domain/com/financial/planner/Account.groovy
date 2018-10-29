package com.financial.planner

class Account {

    String name

    static belongsTo = [user: User]

    static hasMany = [incomeAndExpenses: IncomeAndExpenditure]

    static constraints = {
    }
}

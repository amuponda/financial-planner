package com.financial.planner

class Transaction {
    Date date
    BigDecimal amount

    static belongsTo = [account: Account]
}

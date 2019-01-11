package com.financial.planner

import com.financial.planner.enums.Type
import grails.gorm.transactions.Transactional

@Transactional
class TransactionsService {

    def createTransactions(Date startDate) {
        List results = IncomeAndExpenses.executeQuery("from IncomeAndExpense iae where nextDue between :start and :end", [start: startDate - 1, end: startDate])
        results.each { IncomeAndExpenses iae ->
            Transaction transaction = new Transaction(date: iae.nextDue, account: iae.account)
            transaction.amount = iae.type == Type.INCOME ? iae.amount : -iae.amount
            transaction.save()
            iae.nextDue = iae.repeats.getNextDue(iae.nextDue)
            iae.save()
        }
    }
}

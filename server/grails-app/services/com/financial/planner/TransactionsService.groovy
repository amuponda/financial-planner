package com.financial.planner

import com.financial.planner.enums.Type
import grails.gorm.transactions.Transactional

@Transactional
class TransactionsService {

    def createTransactions(Date date) {
        List results = IncomeAndExpenses.executeQuery("from IncomeAndExpenses iae where nextDue between :start and :end", [start: date - 1, end: date])
        results.each { IncomeAndExpenses iae ->
            Transaction transaction = new Transaction(date: iae.nextDue, account: iae.account)
            transaction.amount = iae.type == Type.INCOME ? iae.amount : -iae.amount
            transaction.save()
            Date nextDue = iae.repeats.getNextDue(iae.nextDue)
            iae.nextDue = iae.endDate ? (nextDue >= iae.endDate ? null : nextDue) : nextDue
            iae.save()
        }
    }
}

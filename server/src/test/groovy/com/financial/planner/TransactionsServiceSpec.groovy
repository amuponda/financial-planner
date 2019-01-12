package com.financial.planner

import grails.buildtestdata.TestDataBuilder
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class TransactionsServiceSpec extends HibernateSpec implements ServiceUnitTest<TransactionsService>, TestDataBuilder{

    def setup() {
    }

    def cleanup() {
    }

    void "test creation of transactions"() {
        setup: "Create Income and expenses"
        Date now = new Date().clearTime()
        User user = build(User)
        Account account = build(Account, user: user)

        IncomeAndExpenses income = build(IncomeAndExpenses, account: account, startDate: now, nextDue: now,
                type: 'INCOME', amount: 20.0g, repeats: 'WEEKLY')
        IncomeAndExpenses expense = build(IncomeAndExpenses, account: account, startDate: now-1, nextDue: now-1,
                type: 'EXPENSE', amount: 5.0g, repeats: 'FORTNIGHTLY')
        IncomeAndExpenses lastBillPayment = build(IncomeAndExpenses, account: account, startDate: now-14, nextDue: now,
                endDate: now, type: 'EXPENSE', amount: 6.0g, repeats: 'FORTNIGHTLY')
        build(IncomeAndExpenses, account: account, startDate: now+1, nextDue: now+1)

        when: "we attempt to create transactions for yesterday and today's IaEs"
        service.createTransactions(now)
        List<Transaction> transactions = Transaction.getAll()

        then: "New transactions should be added to the DB"
        transactions.size() == 3
        transactions.amount == [20, -5, -6]
        income.nextDue == income.repeats.getNextDue(now)
        expense.nextDue == expense.repeats.getNextDue(now-1)
        lastBillPayment.nextDue == null
    }
}

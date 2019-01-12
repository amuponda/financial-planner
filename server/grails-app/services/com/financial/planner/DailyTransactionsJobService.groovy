package com.financial.planner

import com.agileorbit.schwartz.SchwartzJob
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException


import static org.quartz.DateBuilder.newDateInTimezone

@CompileStatic
@Slf4j
class DailyTransactionsJobService implements SchwartzJob {

	final int HOUR = 23
	final int MINUTE = 55
	final int SECONDS = 0
	final TimeZone timeZone = TimeZone.getTimeZone('Etc/GMT+14')

	TransactionsService transactionsService

	@Transactional
	void execute(JobExecutionContext context) throws JobExecutionException {
		transactionsService.createTransactions(newDateInTimezone(timeZone).build().clearTime())
	}

	Date dailyDate() {
		Date startAt = newDateInTimezone(timeZone).atHourMinuteAndSecond(HOUR, MINUTE, SECONDS).build()
		if (startAt.before(newDateInTimezone(timeZone).build())) {
			startAt += 1
		}
		log.info("Trigger start date set to ${startAt}")
		startAt
	}

	void buildTriggers() {
		triggers << factory('daily job to create transactions @ 23:55')
				.startAt(dailyDate())
				.intervalInDays(1)
				.timeZone(timeZone)
				.build()
	}
}

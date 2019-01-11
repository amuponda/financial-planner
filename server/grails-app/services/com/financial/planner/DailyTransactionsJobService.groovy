package com.financial.planner

import com.agileorbit.schwartz.SchwartzJob
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

import static org.quartz.DateBuilder.newDateInTimezone
import static org.quartz.DateBuilder.todayAt
import static org.quartz.DateBuilder.tomorrowAt
import static org.quartz.DateBuilder.translateTime

@CompileStatic
@Slf4j
class DailyTransactionsJobService implements SchwartzJob {

	final int HOUR = 23
	final int MINUTE = 55
	final int SECONDS = 0
	final TimeZone timeZone = TimeZone.getTimeZone('GMT-12:00')

	TransactionsService transactionsService

	@Transactional
	void execute(JobExecutionContext context) throws JobExecutionException {
		transactionsService.createTransactions(newDateInTimezone(timeZone).build().clearTime())
	}

	Date dailyDate() {
		Date startAt = translateTime(todayAt(HOUR, MINUTE, SECONDS), TimeZone.getDefault(), timeZone)
		if (startAt.before(newDateInTimezone(timeZone).build())) {
			return translateTime(tomorrowAt(HOUR, MINUTE, SECONDS), TimeZone.getDefault(), timeZone)
		}
		startAt
	}

	void buildTriggers() {
		triggers << factory('daily job to create transactions @ 23:55')
				.startAt(dailyDate())
				.intervalInDays(1)
				.timeZone(timeZone)
				.build()

		/* Cron format */
		/*
		triggers << factory('daily job to create transactions @ 23:55')
				.cronSchedule('0 55 23 1/1 * ?')
				.timeZone(timeZone)
				.build()
				*/
	}
}

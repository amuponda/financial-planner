package financial.planner

import com.agileorbit.schwartz.SchwartzJob
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import static org.quartz.DateBuilder.todayAt
import static org.quartz.DateBuilder.tomorrowAt

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
		Calendar cal = Calendar.getInstance(timeZone)
		transactionsService.createTransactions(cal.getTime().clearTime())
	}

	Date dailyDate() {
		Date startAt = todayAt(HOUR, MINUTE, SECONDS)
		if (startAt.before(new Date())) {
			return tomorrowAt(HOUR, MINUTE, SECONDS)
		}
		startAt
	}

	void buildTriggers() {
		/*
		triggers << factory('daily job to create transactions @ 23:55')
		.startAt(dailyDate())
		.intervalInDays(1)
		.timeZone(timeZone)
		.build()
		*/

		triggers << factory('daily job to create transactions @ 23:55')
				.cronSchedule('0 55 23 1/1 * ?')
				.timeZone(timeZone)
				.build()
	}
}

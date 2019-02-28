package com.financial.planner.enums

import groovy.time.TimeCategory

enum Repeat {
    ADHOC('Adhoc') {
        @Override
        Date getNextDue(Date currentNextDue) {
            currentNextDue
        }
    },
    ONCE_OFF('Once Off') {
        @Override
        Date getNextDue(Date currentNextDue) {
            currentNextDue
        }
    },
    WEEKLY('Every Week') {
        @Override
        Date getNextDue(Date currentNextDue) {
            use (TimeCategory) {
                currentNextDue + 1.week
            }
        }
    },
    FORTNIGHTLY('Every fortnight') {
        @Override
        Date getNextDue(Date currentNextDue) {
            use (TimeCategory) {
                currentNextDue + 2.weeks
            }
        }
    },
    EVERY_4_WEEKS('Every 4 Weeks') {
        @Override
        Date getNextDue(Date currentNextDue) {
            use (TimeCategory) {
                currentNextDue + 4.week
            }
        }
    },
    EVERY_MONTH('Every month') {
        @Override
        Date getNextDue(Date currentNextDue) {
            use (TimeCategory) {
                currentNextDue + 1.month
            }
        }
    },
    SAME_DAY_EACH_MONTH('Same day each month') {
        @Override
        Date getNextDue(Date currentNextDue) {
            Calendar cal = Calendar.getInstance()
            cal.setTime(currentNextDue)
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1)
            cal.getTime()
        }
    },
    EVERY_2_MONTHS('Every 2 Months') {
        @Override
        Date getNextDue(Date currentNextDue) {
            use (TimeCategory) {
                currentNextDue + 2.months
            }
        }
    },
    EVERY_3_MONTHS('Every 3 Months') {
        @Override
        Date getNextDue(Date currentNextDue) {
            use (TimeCategory) {
                currentNextDue + 3.months
            }
        }
    },
    EVERY_6_MONTHS('Every 6 Months') {
        @Override
        Date getNextDue(Date currentNextDue) {
            use (TimeCategory) {
                currentNextDue + 6.months
            }
        }
    },
    EVERY_12_MONTHS('Every 12 Months') {
        @Override
        Date getNextDue(Date currentNextDue) {
            use (TimeCategory) {
                currentNextDue + 1.year
            }
        }
    }

    private String label


    Repeat(String label) {
        this.label = label
    }

    String getLabel() {
        this.label
    }

    abstract Date getNextDue(Date currentDueDate)
}
package com.financial.planner.enums

enum Repeat {
    ADHOC('Adhoc'),
    ONCE_OFF('Once Off'),
    WEEKLY('Every Week'),
    FORTNIGHTLY('Every fortnight'),
    EVERY_4_WEEKS('Every 4 Weeks'),
    EVERY_MONTH('Every month'),
    EVERY_2_MONTHS('Every 2 Months'),
    EVERY_3_MONTHS('Every 3 Months'),
    EVERY_6_MONTHS('Every 6 Months'),
    EVERY_12_MONTHS('Every 12 Months')

    private String label

    Repeat(String label) {
        this.label = label
    }
}
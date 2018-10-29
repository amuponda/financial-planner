package com.financial.planner.enums

enum Category {
    BANKING_AND_FINANCE('Banking and Finance'),
    ENTERTAINMENT('Entertainment'),
    FOOD_AND_DRINKS('Food and Drinks'),
    GROCERIES('Groceries'),
    HEALTH_AND_BEAUTY('Health and Beauty'),
    HOLIDAY_AND_TRAVEL('Holiday and Travel'),
    HOME('Home'),
    HOUSEHOLD_UTILITIES('Household Utilities'),
    INCOME('IncomeAndExpenditure'),
    INSURANCE('Insurance'),
    KIDS('Kids'),
    PETS('Pets'),
    SHOPPING('Shopping'),
    TRANSFERRING_MONEY('Transferring Money'),
    TRANSPORT('Transport'),
    WORK_AND_STUDY('Work and Study'),
    MISC('Miscellaneous'),
    CUSTOM('Custom')

    Category(String label) {
        this.label = label
    }
}
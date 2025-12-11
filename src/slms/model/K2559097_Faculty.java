package slms.model;

import slms.strategy.K2559097_FacultyFineStrategy;

public class K2559097_Faculty extends K2559097_User {
    public K2559097_Faculty(String userID, String name) {
        // Automatically assign the Faculty Fine Strategy (20 LKR)
        super(userID, name, new K2559097_FacultyFineStrategy());
    }

    @Override
    public int getLoanPeriodDays() { return 30; }

    @Override
    public int getBorrowLimit() { return 10; }

    @Override
    public String getMembershipType() { return "Faculty"; }
}

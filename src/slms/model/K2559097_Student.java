package slms.model;

import slms.strategy.K2559097_StudentFineStrategy;

public class K2559097_Student extends K2559097_User {
    public K2559097_Student(String userID, String name) {
        // Automatically assign the Student Fine Strategy (50 LKR)
        super(userID, name, new K2559097_StudentFineStrategy());
    }

    @Override
    public int getLoanPeriodDays() { return 14; }

    @Override
    public int getBorrowLimit() { return 5; }

    @Override
    public String getMembershipType() { return "Student"; }
}

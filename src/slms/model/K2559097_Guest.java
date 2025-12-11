package slms.model;

import slms.strategy.K2559097_GuestFineStrategy;

public class K2559097_Guest extends K2559097_User {
    public K2559097_Guest(String userID, String name) {
        // Automatically assign the Guest Fine Strategy (100 LKR)
        super(userID, name, new K2559097_GuestFineStrategy());
    }

    @Override
    public int getLoanPeriodDays() { return 7; }

    @Override
    public int getBorrowLimit() { return 2; }

    @Override
    public String getMembershipType() { return "Guest"; }
}

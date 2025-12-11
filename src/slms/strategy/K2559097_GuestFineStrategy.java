package slms.strategy;

public class K2559097_GuestFineStrategy implements K2559097_FineStrategy {
    @Override
    public double calculateFine(int daysLate) {
        // Guests: LKR 100/day
        return daysLate * 100.0;
    }
}

package slms.strategy;

public class K2559097_StudentFineStrategy implements K2559097_FineStrategy {
    @Override
    public double calculateFine(int daysLate) {
        // Students: LKR 50/day
        return daysLate * 50.0;
    }
}

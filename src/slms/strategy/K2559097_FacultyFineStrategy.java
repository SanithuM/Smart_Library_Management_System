package slms.strategy;

public class K2559097_FacultyFineStrategy implements K2559097_FineStrategy {
    @Override
    public double calculateFine(int daysLate) {
        // Faculty: LKR 20/day
        return daysLate * 20.0;
    }
}

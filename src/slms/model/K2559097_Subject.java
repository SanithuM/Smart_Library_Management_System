package slms.model;

public interface K2559097_Subject {
    void attach(K2559097_Observer o);
    void detach(K2559097_Observer o);
    void notifyObservers(String message);
}

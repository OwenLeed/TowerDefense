
public interface StatSubject {
	public void registerStatObserver(StatObserver obs);
	public void removeStatObserver(StatObserver obs);
	public void notifyStatObservers();
}

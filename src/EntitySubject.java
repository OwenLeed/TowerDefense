
public interface EntitySubject {
	public void registerEntityObserver(EntityObserver obs);
	public void removeEntityObserver(EntityObserver obs);
	public void notifyEntityObservers();
}

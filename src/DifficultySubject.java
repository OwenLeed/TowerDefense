
public interface DifficultySubject {
	public void registerDifficultyObserver(DifficultyObserver o);
	public void removeDifficultyObserver(DifficultyObserver o);
	public void notifyDifficultyObservers();
}

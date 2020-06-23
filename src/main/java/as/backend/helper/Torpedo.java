package as.backend.helper;

public class Torpedo {
	public Torpedo() {
		this(null, "Torpedo");
	}

	public Torpedo(Runnable runnable) {
		this(runnable, "Torpedo");
	}

	public Torpedo(String name) {
		this(null, name);
	}

	public Torpedo(Runnable runnable, String name) {

	}

	public void trigger() {

	}

	public void triggerNow() {

	}

	public boolean isActive() {
		return false;
	}

	public void setRunnable(Runnable runnable) {

	}
}

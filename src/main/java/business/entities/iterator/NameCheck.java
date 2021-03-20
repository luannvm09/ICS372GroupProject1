package business.entities.iterator;
/**
 * Interface used to ensure that only objects with a nameCheck method can use the NameFilteredIerator.
 *
 */
public interface NameCheck {
	
	public boolean nameCheck(String name);
}

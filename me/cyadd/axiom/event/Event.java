package me.cyadd.axiom.event;

/**
 * This is an event handling system. It will be annotation based.
 * 
 * 	8888888888                           888    
 *	888                                  888    
 *	888                                  888    
 *	8888888   888  888  .d88b.  88888b.  888888 
 *	888       888  888 d8P  Y8b 888 "88b 888    
 *	888       Y88  88P 88888888 888  888 888    
 *	888        Y8bd8P  Y8b.     888  888 Y88b.  
 *	8888888888  Y88P    "Y8888  888  888  "Y888                                                                                
 * 
 * @author Wulf
 * @since 07/06/2013
 */
public abstract class Event {

	private String name;

	/**
	 * The default constructor is defined for cleaner code. This constructor
	 * assumes the event is synchronous.
	 */
	public Event() {
		this(false);
	}

	/**
	 * This constructor is used to explicitly declare an event as synchronous or
	 * asynchronous.
	 * 
	 * @param isAsync
	 *            true indicates the event will fire asynchronously, false by
	 *            default from default constructor
	 */
	public Event(final boolean isAsync) {
	}

	/**
	 * Convenience method for providing a user-friendly identifier. By default,
	 * it is the event's class's {@linkplain Class#getSimpleName() simple name}.
	 * 
	 * @return name of this event
	 */
	public String getEventName() {
		if (this.name == null) {
			this.name = this.getClass().getSimpleName();
		}
		return this.name;
	}

	/**
	 * This is the enum for setting the priority of the modules Event. There are
	 * three main Events;<br>
	 * <code>DENY, DEFAULT and ALLOW</code>.
	 */
	public enum Result {

		/**
		 * Deny the event. Depending on the event, the action indicated by the
		 * event will either not take place or will be reverted. Some actions
		 * may not be denied.
		 */
		DENY,
		/**
		 * Neither deny nor allow the event. The client will proceed with its
		 * normal handling.
		 */
		DEFAULT,
		/**
		 * Allow / Force the event. The action indicated by the event will take
		 * place if possible, even if the server would not normally allow the
		 * action. Some actions may not be allowed.
		 */
		ALLOW;
	}

}

package com.comandulli.lib;

/**
 * Pending value helps you when you need to use a changing value,
 * yet still declare it as final to be used in inner classes/blocks.
 *
 * It has a boolean check if it has already been synced.
 *
 * @param <T> the type of the value
 */
public class PendingValue<T> {

	private T value;
	private boolean pending = true;

    /**
     * Instantiates a new Pending value.
     *
     * @param defaultValue the default value
     */
    public PendingValue(T defaultValue) {
		this.value = defaultValue;
	}

    /**
     * Gets the value.
     *
     * @return the value
     */
    public T getValue() {
		return value;
	}

    /**
     * Sets the value.
     *
     * @param value the value
     */
    public void setValue(T value) {
		this.value = value;
	}

    /**
     * Sets as pending value.
     *
     * @param pending the pending
     */
    public void setPending(boolean pending) {
		this.pending = pending;
	}

    /**
     * If the value is still pending.
     *
     * @return the boolean
     */
    public boolean isPending() {
		return pending;
	}

}

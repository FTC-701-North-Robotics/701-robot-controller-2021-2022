package org.firstinspires.ftc.teamcode.vision;

/**
 * Vision interface
 */
public interface Vision {
	/**
	 * Gets the target level for auto
	 * @return Target Level Enum
	 */
	TargetLevel getTargetLevel();

	/**
	 * Closes camera stream
	 */
	void close();

	/**
	 * Opens a ftc dashboard camera stream
	 */
	void openDashboardStream();

	void closeDashboardStream();
}

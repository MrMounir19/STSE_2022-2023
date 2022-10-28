package WarehouseRobot;

import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class SensorControl {

    /**
     * @param sp The sample provider to use.
     * @return The first distance from the sample provider.
     */
    private static int getSPDistance(SampleProvider sp) {
        float[] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        return (int) sample[0];
    }

    /**
     * Get front sensor distance.
     */
    public static int getFrontSensorDistance() {
        Delay.msDelay(1);
        SampleProvider sample_provider = RobComponents.ussF.getDistanceMode();
        return getSPDistance(sample_provider);
    }

    /**
     * Get left sensor distance.
     */
    public static int getLeftSensorDistance() {
        Delay.msDelay(1);
        SampleProvider sample_provider = RobComponents.ussL.getDistanceMode();
        return getSPDistance(sample_provider);
    }

}

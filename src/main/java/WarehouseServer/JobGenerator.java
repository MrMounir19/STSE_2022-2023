package WarehouseServer;

import WarehouseShared.Config;
import WarehouseShared.Job;
import WarehouseShared.Position;
import com.google.gson.JsonObject;

import java.util.Random;

/**
 * Util class to generate random jobs for simulation of the system.
 *
 * @author Maxim
 * @author Thimoty
 * @since 14/01/2023
 */
public class JobGenerator {
    static Random random = new Random();
    static int minimumJobCount = 10;

    protected static JsonObject getPositionLimits() {
        return Config.getConfig().get("positionLimits").getAsJsonObject();
    }

    protected static float getMinX() {
        return getPositionLimits().get("xmin").getAsFloat();
    }

    protected static float getMaxX() {
        return getPositionLimits().get("xmax").getAsFloat();
    }

    protected static float getMinY() {
        return getPositionLimits().get("ymin").getAsFloat();
    }
    protected static float getMaxY() {
        return getPositionLimits().get("ymax").getAsFloat();
    }

    public static void seedRandom(long seed) {
        random = new Random(seed);
    }

    public static boolean needsJobs() {
        return JobStorage.toDoJobs.size() < minimumJobCount + RobotStorage.getRobots().size();
    }

    public static void generateJob() {
        Job newJob = new Job();
        newJob.setDestination(getRandomPosition());
        JobStorage.addToDoJob(newJob);
    }

    protected static Position getRandomPosition() {
        float xCor = (random.nextFloat() * (getMaxX() - getMinX())) + getMinX();
        float yCor = (random.nextFloat() * (getMaxY() - getMinY())) + getMinY();

        return new Position(xCor, yCor);
    }
}

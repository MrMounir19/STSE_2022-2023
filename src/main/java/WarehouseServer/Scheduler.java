package WarehouseServer;

import WarehouseShared.Job;

public class Scheduler {
    public static Job requestJob(RobotObject robot) {

        if(JobStorage.toDoJobs.isEmpty()){
            System.out.println("No Jobs Available");
            return null;
        }

        Job fetchedJob = findNearestJob(robot);

        if(fetchedJob == null){
            System.out.println("No Nearest Job");
            return null;
        }

        JobStorage.addInProgressJob(fetchedJob);
        //Add job to RobotInformation/RobotObject?

        return fetchedJob;
    }

    private static Job findNearestJob(RobotObject robot) {
        Job nearestJob = null;
        float nearestDistance = Float.MAX_VALUE;
        for (Job job : JobStorage.toDoJobs) {
            if (job.getSourcePosition() == null) {
                continue;
            }
            float distance = robot.position.distanceTo(job.getSourcePosition());
            if (distance < nearestDistance) {
                nearestJob = job;
                nearestDistance = distance;
            }
        }

        if (nearestJob == null) {
            if (JobStorage.toDoJobs.size() > 0) {
                nearestJob = JobStorage.toDoJobs.get(0);
            }
        }

        return nearestJob;
    }

    public static void finishJob(RobotObject robot, Job job) {
        // check if robot has any jobs at all
//        if(robot.jobs.isEmpty()){
//            System.out.println("No Jobs Available");
//            return;
//        }

        JobStorage.addFinishedJob(robot,job);

        //Remove object from robot's joblist
    }

}

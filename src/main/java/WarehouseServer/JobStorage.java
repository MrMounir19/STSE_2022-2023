package WarehouseServer;

import WarehouseShared.Job;

import java.util.ArrayList;

/**
 * Util class to create messages of the system
 *
 * @author Maxim
 * @author Thimoty
 * @since 10/12/2022
 */
public class JobStorage {
    public static ArrayList<Job> toDoJobs = new ArrayList<>();
    public static ArrayList<Job> inProgressJobs = new ArrayList<>();
    public static ArrayList<Job> finishedJobs = new ArrayList<>();

    public static void addToDoJob(Job job){
        System.out.println("Adding Job to Queue" + job.toString());
        toDoJobs.add(job);
        System.out.println("Added a Job to the Queue: " + toDoJobs.get(toDoJobs.size() - 1));
    }

    public static void addInProgressJob(Job job){
        if(!toDoJobs.contains(job)){
            System.out.println("Job does not exist in to-do Jobs");
            return;
        }

        System.out.println("Removing Job from to-do Jobs");
        toDoJobs.remove(job);
        System.out.println("Job removed from to-do Jobs");

        System.out.println("Adding Job to in-progress Jobs: " + job);
        inProgressJobs.add(job);
        System.out.println("Added a Job to the in-progress list: " + inProgressJobs.get(inProgressJobs.size() - 1));

        // Todo: Also set this if it already had a start time? (For example when job is handed back to the system for whatever reason)
        job.setStartTime();
    }

    public static void addFinishedJob(RobotObject robot, Job job){
        if(!inProgressJobs.contains(job)){
            System.out.println("Job does not exist in in-progress Jobs");
            return;
        }

        System.out.println("Robot: " + robot.toString() + " finished job: " + job);

        System.out.println("Removing Job from in-progress Jobs");
        inProgressJobs.remove(job);
        System.out.println("Job removed from in-progress Jobs");

        System.out.println("Adding Job to finished Jobs: " + job);
        finishedJobs.add(job);
        System.out.println("Added a Job to the finished list: " + finishedJobs.get(finishedJobs.size() - 1));

        job.setFinishedTime();

        System.out.println("Job duration: " + job.jobDuration().toString());
    }

}

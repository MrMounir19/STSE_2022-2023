package WarehouseServer;

import WarehouseShared.Job;

import java.awt.*;
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

    public static void addToDoJob(Job job) {
        System.out.println("Adding Job to Queue" + job.toString());
        toDoJobs.add(job);
        System.out.println("Added a Job to the Queue: " + toDoJobs.get(toDoJobs.size() - 1));
    }

    public static void addInProgressJob(Job job) {
        if(!toDoJobs.contains(job)){
            System.out.println("Job does not exist in to-do Jobs");
            return;
        }

        System.out.println("Removing Job from to-do Jobs");
        toDoJobs.remove(job);
        System.out.println("Job removed from to-do Jobs");

        System.out.println("Adding Job to in-progress Jobs: " + job.toString());
        inProgressJobs.add(job);
        System.out.println("Added a Job to the in-progress Jobs: " + inProgressJobs.get(inProgressJobs.size() - 1));

        // Todo: Also set this if it already had a start time? (For example when job is handed back to the system for whatever reason)
        job.setStartTime();
    }

    public static void addFinishedJob(RobotObject robot, Job job) {
        if(!inProgressJobs.contains(job)){
            System.out.println("Job does not exist in in-progress Jobs");
            return;
        }

        System.out.println("Robot: " + robot.toString() + " finished job: " + job.toString());

        System.out.println("Removing Job from in-progress Jobs");
        inProgressJobs.remove(job);
        System.out.println("Job removed from in-progress Jobs");

        System.out.println("Adding Job to finished Jobs: " + job);
        finishedJobs.add(job);
        System.out.println("Added a Job to the finished Jobs: " + finishedJobs.get(finishedJobs.size() - 1));

        job.setFinishedTime();

        System.out.println("Job duration: " + job.jobDuration().toString());
    }

    public static void failJob(RobotObject robot, Job job) {
        if(!inProgressJobs.contains(job)){
            System.out.println("Job does not exist in in-progress Jobs");
            return;
        }

        System.out.println("Robot: " + robot.toString() + "failed job: " + job.toString());

        System.out.println("Removing Job from in-progress Jobs");
        inProgressJobs.remove(job);
        System.out.println("Job removed from in-progress Jobs");

        System.out.println("Adding Job back to to-do jobs: " + job);
        toDoJobs.add(job);
        System.out.println("Added a Job to the to-do list: " + toDoJobs.get(toDoJobs.size() - 1));
    }

    public static ArrayList<Job> allJobs() {
        ArrayList<Job> joinedList = new ArrayList<>();
        joinedList.addAll(toDoJobs);
        joinedList.addAll(inProgressJobs);
        joinedList.addAll(finishedJobs);
        return joinedList;
    }
    
    public static Job getFromId(int id) {
        for (Job job : allJobs()) {
            if (job.getId() == id) {
                return job;
            }
        }
        return null;
    }
}

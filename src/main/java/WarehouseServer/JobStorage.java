package WarehouseServer;

import WarehouseShared.Job;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Util class to store to-do, in-progress, and finished jobs.
 *
 * @author Maxim
 * @author Thimoty
 * @since 10/12/2022
 */
public class JobStorage {
    public static ArrayList<Job> toDoJobs = new ArrayList<>();
    public static ArrayList<Job> inProgressJobs = new ArrayList<>();
    public static ArrayList<Job> finishedJobs = new ArrayList<>();
    private static int jobIdCounter = 0;

    public static HashMap<RobotObject, ArrayList<Job>> robotJobs = new HashMap<>();

    public static int getJobIdCounter() {
        return jobIdCounter;
    }

    protected static void incrementJobIdCounter() {
        jobIdCounter += 1;
    }

    public static void addToDoJob(Job job) {
        if (job.getId() == -1) {
            job.setId(getJobIdCounter());
            incrementJobIdCounter();
        }
        System.out.println("Adding Job to Queue: " + job.toString());
        toDoJobs.add(job);
        System.out.println("Added a Job to the Queue: " + toDoJobs.get(toDoJobs.size() - 1).toString());
    }

    public static void registerRobotInRobotJobs(RobotObject robot) {
        if(!robotJobs.containsKey(robot)) {
            robotJobs.put(robot, new ArrayList<>());
        }
    }

    public static void addJobToRobot(RobotObject robot, Job job){
        registerRobotInRobotJobs(robot);
        try{
            robotJobs.get(robot).add(job);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeJobFromRobot(RobotObject robot, Job job){
        if(!robotJobs.containsKey(robot)){
            throw new RuntimeException();
        }

        try{
            robotJobs.get(robot).remove(job);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkRobotIdle(RobotObject robot) {
        if(!robotJobs.containsKey(robot)) {
            registerRobotInRobotJobs(robot);
            return true;
        }

        return robotJobs.get(robot).isEmpty();
    }

    public static void addInProgressJob(Job job) {
        if(!toDoJobs.contains(job)){
            System.out.println("Job does not exist in to-do Jobs: " + job.toString());
            return;
        }

        System.out.println("Removing Job from to-do Jobs: " + job.toString());
        toDoJobs.remove(job);
        System.out.println("Job removed from to-do Jobs: " + job.toString());

        System.out.println("Adding Job to in-progress Jobs: " + job.toString());
        inProgressJobs.add(job);
        System.out.println("Added a Job to the in-progress Jobs: " + inProgressJobs.get(inProgressJobs.size() - 1));

        // Todo: Also set this if it already had a start time? (For example when job is handed back to the system for whatever reason)
        job.setStartTime();
    }

    public static void addFinishedJob(RobotObject robot, Job job) {
        if(!inProgressJobs.contains(job)){
            System.out.println("Job does not exist in in-progress Jobs: " + job.toString());
            return;
        }

        System.out.println("Robot: " + robot.toString() + " finished job: " + job.toString());

        System.out.println("Removing Job from in-progress Jobs: " + job.toString());
        inProgressJobs.remove(job);
        System.out.println("Job removed from in-progress Jobs: " + job.toString());

        System.out.println("Adding Job to finished Jobs: " + job.toString());
        finishedJobs.add(job);
        System.out.println("Added a Job to the finished Jobs: " + finishedJobs.get(finishedJobs.size() - 1).toString());

        job.setFinishedTime();

        System.out.println("Job duration: " + job.jobDuration().toString());
    }

    public static void failJob(RobotObject robot, Job job) {
        if(!inProgressJobs.contains(job)){
            System.out.println("Job does not exist in in-progress Jobs: " + job.toString());
            return;
        }

        System.out.println("Robot: " + robot.toString() + "failed job: " + job.toString());

        System.out.println("Removing Job from in-progress Jobs: " + job.toString());
        inProgressJobs.remove(job);
        System.out.println("Job removed from in-progress Jobs: " + job.toString());

        System.out.println("Adding Job back to to-do Jobs: " + job.toString());
        toDoJobs.add(job);
        System.out.println("Added a Job to the to-do Jobs: " + toDoJobs.get(toDoJobs.size() - 1).toString());
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

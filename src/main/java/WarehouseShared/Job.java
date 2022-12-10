package WarehouseShared;

import Enums.JobType;

import java.util.ArrayList;

public class Job {
    public JobType action;
    //TODO fix with tuples or something
    public ArrayList<ArrayList<Integer>> path;

    public String getAction() {
        return action.toString();
    }

    public void setAction(String action) {
        this.action = JobType.valueOf(action);
    }

    public ArrayList<ArrayList<Integer>> getPath() {
        return path;
    }

    public void setPath(ArrayList<ArrayList<Integer>> path) {
        this.path = path;
    }
}


package Utils;

import java.util.ArrayList;

public class Job {
    public String action;
    //TODO fix with tuples or something
    public ArrayList<ArrayList<Integer>> path;
    public ArrayList<Integer> currentGoal = null;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<ArrayList<Integer>> getPath() {
        return path;
    }

    public void setPath(ArrayList<ArrayList<Integer>> path) {
        this.path = path;
    }

    public ArrayList<Integer> getCurrentGoal() {
        if (currentGoal == null) {
            currentGoal = path.remove(0);
        }
        return currentGoal;

    }

    public void setCurrentGoal(ArrayList<Integer> currentGoal) {
        this.currentGoal = currentGoal;
    }
}


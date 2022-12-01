package Utils;

import java.util.ArrayList;

public class Job {
    public String type;
    //TODO fix with tuples or something
    public ArrayList<ArrayList<Integer>> path;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ArrayList<Integer>> getPath() {
        return path;
    }

    public void setPath(ArrayList<ArrayList<Integer>> path) {
        this.path = path;
    }
}


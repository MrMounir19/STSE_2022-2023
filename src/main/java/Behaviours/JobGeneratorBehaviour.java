package Behaviours;

import WarehouseServer.JobGenerator;
import jade.core.behaviours.CyclicBehaviour;

public class JobGeneratorBehaviour extends CyclicBehaviour {
    static int blockDuration = 10_000;

    @Override
    public void action() {
        while(JobGenerator.needsJobs()) {
            JobGenerator.generateJob();
        }
        block(blockDuration);
    }
}

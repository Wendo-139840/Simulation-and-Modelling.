import java.util.ArrayList;
import java.util.List;

public class QueueStatistics {

    private SimulationData simulation;

    public QueueStatistics(SimulationData simulation) {
        this.simulation = simulation;
    }

    public void calculateStatistics() {
        // Average Waiting Time
        double totalWaitingTime = simulation.waiting_time.stream().mapToDouble(Double::doubleValue).sum();
        double avgWaitingTime = totalWaitingTime / simulation.waiting_time.size();

        // Average Time in System
        double totalTimeInSystem = simulation.time_in_system.stream().mapToDouble(Double::doubleValue).sum();
        double avgTimeInSystem = totalTimeInSystem / simulation.time_in_system.size();

        // Average Service Time
        double totalServiceTime = simulation.service_time.stream().mapToDouble(Double::doubleValue).sum();
        double avgServiceTime = totalServiceTime / simulation.service_time.size();

        // Average Number in Queue
        double totalNumberInQueue = simulation.number_in_queue.stream().mapToInt(Integer::intValue).sum();
        double avgNumberInQueue = (double) totalNumberInQueue / simulation.number_in_queue.size();

        // Average Number in System
        double totalNumberInSystem = simulation.number_in_system.stream().mapToInt(Integer::intValue).sum();
        double avgNumberInSystem = (double) totalNumberInSystem / simulation.number_in_system.size();

        // Total Server Idle Time
        double totalIdleTime = simulation.server_idle_time.stream().mapToDouble(Double::doubleValue).sum();

        // Server Utilization
        double lastServiceEndTime = simulation.service_end.get(simulation.service_end.size() - 1);
        double serverUtilization = totalServiceTime / lastServiceEndTime;

        // Print the results
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Time in System: " + avgTimeInSystem);
        System.out.println("Average Service Time: " + avgServiceTime);
        System.out.println("Average Number in Queue: " + avgNumberInQueue);
        System.out.println("Average Number in System: " + avgNumberInSystem);
        System.out.println("Total Server Idle Time: " + totalIdleTime);
        System.out.println("Server Utilization: " + serverUtilization);
    }

    public static void main(String[] args) {
        SimulationData simulationData = new SimulationData();
        simulationData.runSimulation();
        QueueStatistics stats = new QueueStatistics(simulationData);
        stats.calculateStatistics();
    }
}

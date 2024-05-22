import java.util.ArrayList;
import java.util.List;

public class SimulationData {
    public List<Integer> customer_number;
    public List<Double> inter_arrival_time;
    public List<Double> service_time;
    public List<Double> clock_time;
    public List<Double> service_start;
    public List<Double> service_end;
    public List<Integer> number_in_system;
    public List<Integer> number_in_queue;
    public List<Double> waiting_time;
    public List<Double> time_in_system;
    public List<Double> server_idle_time;

    private int nbrInSys;
    private int nbrInQ;

    public SimulationData() {
        customer_number = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { // Start from 1 to 10
            customer_number.add(i);
        }
        inter_arrival_time = List.of(1.9, 1.3, 1.1, 1.0, 2.2, 2.1, 1.8, 2.8, 2.7, 2.4);
        service_time = List.of(1.7, 1.8, 1.5, 0.9, 0.6, 1.7, 1.1, 1.8, 0.8, 0.5);
        clock_time = new ArrayList<>();
        service_start = new ArrayList<>();
        service_end = new ArrayList<>();
        number_in_system = new ArrayList<>();
        number_in_queue = new ArrayList<>();
        waiting_time = new ArrayList<>();
        time_in_system = new ArrayList<>();
        server_idle_time = new ArrayList<>();

        nbrInSys = 0;
        nbrInQ = 0;
    }

    public void runSimulation() {
        for (int i = 0; i < customer_number.size(); i++) {
            // Calculate clock time (arrival time)
            double c;
            if (clock_time.isEmpty()) {
                c = inter_arrival_time.get(i);
            } else {
                c = inter_arrival_time.get(i) + clock_time.get(i - 1);
            }
            clock_time.add(Math.round(c * 10.0) / 10.0);

            // Calculate service start time
            double ss;
            if (service_start.isEmpty()) {
                ss = clock_time.get(i);
            } else {
                ss = Math.max(clock_time.get(i), service_end.get(i - 1));
            }
            service_start.add(Math.round(ss * 10.0) / 10.0);

            // Calculate service end time
            double se = service_start.get(i) + service_time.get(i);
            service_end.add(Math.round(se * 10.0) / 10.0);

            // Calculate server idle time
            double sit = i > 0 ? service_start.get(i) - service_end.get(i - 1) : 0;
            server_idle_time.add(Math.round(sit * 10.0) / 10.0);

            // Calculate waiting time in queue
            double wiq = service_start.get(i) - clock_time.get(i);
            waiting_time.add(Math.round(wiq * 10.0) / 10.0);

            // Calculate time in system
            double tis = waiting_time.get(i) + service_time.get(i);
            time_in_system.add(Math.round(tis * 10.0) / 10.0);

            // Update number in system and number in queue
            if (i == 0) {
                nbrInSys = 1;
            } else {
                nbrInSys = 0;
                for (int j = 0; j <= i; j++) {
                    if (clock_time.get(i) < service_end.get(j)) {
                        nbrInSys++;
                    }
                }
            }
            number_in_system.add(nbrInSys);
            nbrInQ = Math.max(0, nbrInSys - 1);
            number_in_queue.add(nbrInQ);
        }
    }
}

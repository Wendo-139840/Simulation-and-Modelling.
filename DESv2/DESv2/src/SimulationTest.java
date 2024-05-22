import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationTest extends JFrame {
    private List<Integer> n;
    private List<Double> iat;
    private List<Double> serviceTime;
    private List<Double> clockTime;
    private List<Double> serviceStarts;
    private List<Double> serviceEnds;
    private List<Integer> numberInSystem;
    private List<Integer> numberInQueue;
    private List<Double> waitingTime;
    private List<Double> timeInSystem;
    private List<Double> serverIdleTime;

    private int nbrInSys;
    private int nbrInQ;

    public SimulationTest() {
        n = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { // Start from 1 to 10
            n.add(i);
        }
        iat = List.of(1.9, 1.3, 1.1, 1.0, 2.2, 2.1, 1.8, 2.8, 2.7, 2.4);
        serviceTime = List.of(1.7, 1.8, 1.5, 0.9, 0.6, 1.7, 1.1, 1.8, 0.8, 0.5);
        clockTime = new ArrayList<>();
        serviceStarts = new ArrayList<>();
        serviceEnds = new ArrayList<>();
        numberInSystem = new ArrayList<>();
        numberInQueue = new ArrayList<>();
        waitingTime = new ArrayList<>();
        timeInSystem = new ArrayList<>();
        serverIdleTime = new ArrayList<>();

        nbrInSys = 0;
        nbrInQ = 0;

        setTitle("Simulation Results");
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        runSimulation();
    }

    public void runSimulation() {
        for (int i = 0; i < n.size(); i++) {
            // Calculate clock time (arrival time)
            double c;
            if (clockTime.isEmpty()) {
                c = iat.get(i);
            } else {
                c = iat.get(i) + clockTime.get(i - 1);
            }
            clockTime.add(Math.round(c * 10.0) / 10.0);

            // Calculate service start time
            double ss;
            if (serviceStarts.isEmpty()) {
                ss = clockTime.get(i);
            } else {
                ss = Math.max(clockTime.get(i), serviceEnds.get(i - 1));
            }
            serviceStarts.add(Math.round(ss * 10.0) / 10.0);

            // Calculate service end time
            double se = serviceStarts.get(i) + serviceTime.get(i);
            serviceEnds.add(Math.round(se * 10.0) / 10.0);

            // Calculate server idle time
            double sit = i > 0 ? serviceStarts.get(i) - serviceEnds.get(i - 1) : 0;
            serverIdleTime.add(Math.round(sit * 10.0) / 10.0);

            // Calculate waiting time in queue
            double wiq = serviceStarts.get(i) - clockTime.get(i);
            waitingTime.add(Math.round(wiq * 10.0) / 10.0);

            // Calculate time in system
            double tis = waitingTime.get(i) + serviceTime.get(i);
            timeInSystem.add(Math.round(tis * 10.0) / 10.0);

            // Update number in system and number in queue
            if (i == 0) {
                nbrInSys = 1;
            } else {
                nbrInSys = 0;
                for (int j = 0; j <= i; j++) {
                    if (clockTime.get(i) < serviceEnds.get(j)) {
                        nbrInSys++;
                    }
                }
            }
            numberInSystem.add(nbrInSys);
            nbrInQ = Math.max(0, nbrInSys - 1);
            numberInQueue.add(nbrInQ);
        }

        // Display the results in a JTable
        displayResultsInTable();
    }

    private void displayResultsInTable() {
        String[] columnNames = {
                "Customer", "IAT", "Clock Time", "Service Time", "Start", "End", "In System", "In Queue", "Wait Time", "Time In System", "Idle Server"
        };

        Object[][] data = new Object[n.size()][columnNames.length];
        for (int i = 0; i < n.size(); i++) {
            data[i][0] = n.get(i);
            data[i][1] = iat.get(i);
            data[i][2] = clockTime.get(i);
            data[i][3] = serviceTime.get(i);
            data[i][4] = serviceStarts.get(i);
            data[i][5] = serviceEnds.get(i);
            data[i][6] = numberInSystem.get(i);
            data[i][7] = numberInQueue.get(i);
            data[i][8] = waitingTime.get(i);
            data[i][9] = timeInSystem.get(i);
            data[i][10] = serverIdleTime.get(i);
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationTest simulation = new SimulationTest();
            simulation.setVisible(true);
        });
    }
}
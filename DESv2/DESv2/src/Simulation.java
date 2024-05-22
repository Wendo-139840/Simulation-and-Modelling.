import javax.swing.*;
import java.awt.*;

public class Simulation extends JFrame {
    private SimulationData data;

    public Simulation() {
        data = new SimulationData();

        setTitle("Simulation Results");
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        data.runSimulation();

        SimulationTable simulationTable = new SimulationTable(data);
        add(simulationTable.createTable(), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Simulation simulation = new Simulation();
            simulation.setVisible(true);
        });
    }
}

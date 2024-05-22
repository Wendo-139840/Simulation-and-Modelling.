import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SimulationTable {
    private SimulationData data;

    public SimulationTable(SimulationData data) {
        this.data = data;
    }

    public JScrollPane createTable() {
        String[] columnNames = {
                "Customer", "IAT", "Clock Time", "Service Time", "Start", "End", "In System", "In Queue", "Wait Time", "Time In System", "Idle Server"
        };

        Object[][] tableData = new Object[data.customer_number.size()][columnNames.length];
        for (int i = 0; i < data.customer_number.size(); i++) {
            tableData[i][0] = data.customer_number.get(i);
            tableData[i][1] = data.inter_arrival_time.get(i);
            tableData[i][2] = data.clock_time.get(i);
            tableData[i][3] = data.service_time.get(i);
            tableData[i][4] = data.service_start.get(i);
            tableData[i][5] = data.service_end.get(i);
            tableData[i][6] = data.number_in_system.get(i);
            tableData[i][7] = data.number_in_queue.get(i);
            tableData[i][8] = data.waiting_time.get(i);
            tableData[i][9] = data.time_in_system.get(i);
            tableData[i][10] = data.server_idle_time.get(i);
        }

        JTable table = new JTable(new DefaultTableModel(tableData, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        return scrollPane;
    }
}

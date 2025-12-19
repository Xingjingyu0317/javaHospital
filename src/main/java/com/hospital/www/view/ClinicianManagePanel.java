package com.hospital.www.view;

import com.hospital.www.model.Clinician;
import com.hospital.www.controller.ClinicianController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// clinician management
public class ClinicianManagePanel extends JPanel {
    private JTable clinicianTable;
    private DefaultTableModel tableData;
    private ClinicianController ctrl;

    public ClinicianManagePanel() {
        this.ctrl = new ClinicianController();
        setupInterface();
        loadData();
    }

    private void setupInterface() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel title = new JLabel("Clinician Records");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(title);
        
        return headerPanel;
    }

    private JScrollPane buildTable() {
        String[] cols = {
            "Clinician ID", "Title", "First Name", "Last Name",
            "Speciality", "GMC Number", "Workplace Type", "Status"
        };
        
        tableData = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        clinicianTable = new JTable(tableData);
        clinicianTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clinicianTable.setRowHeight(25);
        
        return new JScrollPane(clinicianTable);
    }

    private void loadData() {
        tableData.setRowCount(0);
        List<Clinician> clinicians = ctrl.getAll();
        
        for (Clinician c : clinicians) {
            Object[] rowData = {
                c.getClinicianId(),
                c.getTitle(),
                c.getFirstName(),
                c.getLastName(),
                c.getSpeciality(),
                c.getGmcNumber(),
                c.getWorkplaceType(),
                c.getEmploymentStatus()
            };
            tableData.addRow(rowData);
        }
    }

    public void refresh() {
        loadData();
    }
}

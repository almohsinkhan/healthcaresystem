import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class DiseasePredictionGUI extends JFrame {
    private JList<String> symptomList;
    private JTextField customInputField;
    private JButton predictButton;
    private JLabel resultLabel;

    public DiseasePredictionGUI() {
        setTitle("Disease Predictor");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for inputs
        JPanel inputPanel = new JPanel(new GridLayout(3, 1));

        // 1. Symptom List (multi-select)
        String[] symptoms = {"fever", "cough", "headache", "fatigue", "nausea", "vomiting", "dizziness"};
        symptomList = new JList<>(symptoms);
        symptomList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        inputPanel.add(new JScrollPane(symptomList));

        // 2. Custom text input
        customInputField = new JTextField();
        customInputField.setToolTipText("Or type symptoms here (comma separated)");
        inputPanel.add(customInputField);

        // 3. Predict button
        predictButton = new JButton("Predict");
        inputPanel.add(predictButton);

        // Add components
        add(inputPanel, BorderLayout.CENTER);
        resultLabel = new JLabel("Prediction: ");
        add(resultLabel, BorderLayout.SOUTH);

        // Button action
        predictButton.addActionListener(e -> predictDisease());

        setVisible(true);
    }

    private void predictDisease() {
        // Collect symptoms from both list and text field
        Set<String> inputSymptoms = new HashSet<>();

        for (String s : symptomList.getSelectedValuesList()) {
            inputSymptoms.add(s.trim().toLowerCase());
        }

        String customText = customInputField.getText().trim();
        if (!customText.isEmpty()) {
            String[] customSymptoms = customText.split(",");
            for (String s : customSymptoms) {
                inputSymptoms.add(s.trim().toLowerCase());
            }
        }

        if (inputSymptoms.isEmpty()) {
            resultLabel.setText("Please select or enter symptoms.");
            return;
        }

        try {
            String[] command = new String[inputSymptoms.size() + 2];
            command[0] = "python3";
            command[1] = "predict.py";
            int i = 2;
            for (String s : inputSymptoms) {
                command[i++] = s;
            }

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File("/home/mohsinkhan/Documents/HealthcarePredictionAI"));
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                resultLabel.setText("Prediction: " + line);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            resultLabel.setText("Error during prediction.");
        }
    }

    public static void main(String[] args) {
        new DiseasePredictionGUI();
    }
}

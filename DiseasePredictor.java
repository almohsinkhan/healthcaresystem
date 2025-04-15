import java.io.*;

public class DiseasePredictor {
    public static void main(String[] args) {
        try {
            String[] symptoms = {"fever", "cough"};  // from GUI later

            String[] command = new String[symptoms.length + 2];
            command[0] = "python3";
            command[1] = "predict.py";
            System.arraycopy(symptoms, 0, command, 2, symptoms.length);

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File("/home/mohsinkhan/Documents/HealthcarePredictionAI"));

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Predicted Disease: " + line);
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

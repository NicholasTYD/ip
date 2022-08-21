package duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Storage {
    private File saveFolder;
    private File saveFile;

    public Storage(String savePath, String saveName) {
        this.saveFolder = new File(savePath);
        this.saveFile = new File(savePath + saveName);
    }

    public void saveFile(TaskList tasks) {
        try {
            if (!saveFolder.exists()) {
                saveFolder.mkdir();
            }
            FileWriter fw = new FileWriter(saveFile);
            for (Task t : tasks.getTasks()) {
                String saveTask = t.parseToSaveData();
                fw.write(saveTask + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to save the file");
        }
    }

    public void loadFile(TaskList tasks) {
        try {
            if (saveFile.exists()) {
                Scanner s = new Scanner(saveFile);
                while (s.hasNext()) {
                    String line = s.nextLine();
                    loadTaskLineTo(tasks, line);
                }
                s.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("The duke scanner is unable to load the information in the file.");
        }
    }

    private void loadTaskLineTo(TaskList tasks, String line) {
        String[] temp = line.split("\\|");
        String[] info = Arrays.copyOf(temp, 7);
        String taskType = info[0];
        int status = Integer.parseInt(info[1]);
        String description = info[2];
        String date1 = info[3];
        String time1 = info[4];
        String date2 = info[5];
        String time2 = info[6];
        switch (taskType) {
        case "T":
            tasks.loadTask(new Todo(description, status));
            break;
        case "D":
            tasks.loadTask(new Deadline(description, status, date1, time1));
            break;
        case "E":
            tasks.loadTask(new Event(description, status, date1, time1, date2, time2));
            break;
        default:
            System.out.println("Unknown task type");
        }
    }
}
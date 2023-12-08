package data;
import com.google.gson.*;
import com.google.gson.stream.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    public static JsonArray readJsonFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            Gson gson = new Gson();
            JsonArray JsonContent = JsonParser.parseString(jsonString.toString()).getAsJsonArray();

            return JsonContent;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveJsonData(String filePath, JsonArray updatedData) {
        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(filePath))) {
            jsonWriter.setIndent("  ");

            Gson gson = new Gson();
            gson.toJson(updatedData, jsonWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getFacultyNames() {
        String filePath = "src/data/Courses.json";
        JsonArray JsonContent = readJsonFile(filePath);
        List<String> facultyNameList = new ArrayList<>();

        for (JsonElement facultyElement : JsonContent) {
            JsonObject facultyObject = facultyElement.getAsJsonObject();
            JsonArray facultiesArray = facultyObject.getAsJsonArray("faculties");

            for (JsonElement faculty : facultiesArray) {
                JsonObject facultyJson = faculty.getAsJsonObject();
                String facultyName = facultyJson.get("facultyName").getAsString();
                facultyNameList.add(facultyName);
            }
        }

        return facultyNameList;
    }

    public static List<String> getProgramNames(String targetFaculty) {
        String filePath = "src/data/Courses.json";
        JsonArray JsonContent = readJsonFile(filePath);
        List<String> programNameList = new ArrayList<>();

        for (JsonElement facultyElement : JsonContent) {
            JsonObject facultyObject = facultyElement.getAsJsonObject();
            JsonArray facultiesArray = facultyObject.getAsJsonArray("faculties");

            for (JsonElement faculty : facultiesArray) {
                JsonObject facultyObject2 = faculty.getAsJsonObject();
                String currentFacultyName = facultyObject2.get("facultyName").getAsString();

                if (currentFacultyName.equals(targetFaculty)) {
                    JsonArray programsArray = facultyObject2.getAsJsonArray("programs");

                    for (JsonElement program : programsArray) {
                        JsonObject programObject = program.getAsJsonObject();
                        String programName = programObject.get("programName").getAsString();
                        programNameList.add(programName);
                    }
                }
            }
        }

        return programNameList;
    }

    public static List<List<String>> getCourses(String specificFacultyName, String specificProgramName) {
        String filePath = "src/data/Courses.json";
        JsonArray jsonContent = readJsonFile(filePath);

        List<String> courseCodeList = new ArrayList<>();
        List<String> courseNameList = new ArrayList<>();
        List<String> lecturerList = new ArrayList<>();

        for (JsonElement facultyElement : jsonContent) {
            JsonObject facultyObject = facultyElement.getAsJsonObject();
            JsonArray facultiesArray = facultyObject.getAsJsonArray("faculties");

            for (JsonElement faculty : facultiesArray) {
                JsonObject facultyObject2 = faculty.getAsJsonObject();
                String currentFacultyName = facultyObject2.get("facultyName").getAsString();

                if (currentFacultyName.equals(specificFacultyName)) {
                    JsonArray programsArray = facultyObject2.getAsJsonArray("programs");

                    for (JsonElement program : programsArray) {
                        JsonObject programObject = program.getAsJsonObject();
                        String currentProgramName = programObject.get("programName").getAsString();

                        if (currentProgramName.equals(specificProgramName)) {
                            JsonArray coursesArray = programObject.getAsJsonArray("courses");

                            for (JsonElement course : coursesArray) {
                                JsonObject courseJson = course.getAsJsonObject();
                                var courseName = courseJson.get("courseName").getAsString();
                                courseNameList.add(courseName);
                                var courseCode = courseJson.get("courseCode").getAsString();
                                courseCodeList.add(courseCode);
                                var lecturer = courseJson.get("lecturer").getAsString();
                                lecturerList.add(lecturer);
                            }
                        }
                    }
                }
            }
        }

        List<List<String>> finalList = new ArrayList<>();
        finalList.add(courseCodeList);
        finalList.add(courseNameList);
        finalList.add(lecturerList);

        return finalList;
    }
}

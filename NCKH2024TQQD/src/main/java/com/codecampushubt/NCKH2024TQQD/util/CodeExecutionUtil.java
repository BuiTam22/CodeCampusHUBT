package com.codecampushubt.NCKH2024TQQD.util;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CodeExecutionUtil {

    // Hàm thực thi lệnh shell (ví dụ: javac Main.java hoặc java Main)
    public static String runCommand(List<String> command, File workingDir) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(workingDir);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        // Thêm timeout
        if (!process.waitFor(10, TimeUnit.SECONDS)) {  // Timeout sau 10 giây
            process.destroyForcibly();
            throw new RuntimeException("Process timeout after 10 seconds");
        }

        String output;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            output = reader.lines().collect(Collectors.joining("\n"));
        }

//        int exitCode = process.waitFor();

        // Trả về output dù lỗi hay không
        if (process.exitValue() != 0) {
            throw new CompilationException("Command failed: " + String.join(" ", command) + "\nOutput: " + output);
        }

        return output;
    }

    // Hàm xoá toàn bộ thư mục và file con bên trong
    public static void deleteDirectoryRecursively(Path path) {
        try {
            if (Files.exists(path)) {
                // Walk ngược lại (xoá file trước, thư mục sau)
                Files.walk(path)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        } catch (IOException e) {
            // Bắt và log lỗi
            e.printStackTrace();
            throw new RuntimeException("Error while deleting files", e); // Ném lại exception nếu cần
        }
    }

    // Tạo exception class riêng để xử lý lỗi biên dịch
    public static class CompilationException extends RuntimeException {
        private final String output;

        public CompilationException(String message) {
            super(message);
            if (message.contains("Output: ")) {
                this.output = message.substring(message.indexOf("Output: ") + 8);
            } else {
                this.output = "";
            }
        }

        public String getOutput() {
            return output;
        }
    }
}



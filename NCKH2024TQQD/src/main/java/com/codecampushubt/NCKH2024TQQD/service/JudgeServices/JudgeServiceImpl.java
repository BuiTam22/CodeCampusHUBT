package com.codecampushubt.NCKH2024TQQD.service.JudgeServices;

import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.codecampushubt.NCKH2024TQQD.util.CodeExecutionUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class JudgeServiceImpl implements JudgeService{
    public JudgeRunResponseDTO runUserCode(JudgeRequestDTO request) {
        // Tạo tên thư mục tạm dựa theo username, ID bài và thời gian để đảm bảo không trùng lặp
        String folderName = "test";

        // Tạo đường dẫn đến thư mục gốc tạm (ví dụ: temp_code/username-ex1-run123456789)
        Path workingDir = Paths.get("Code_Dir", folderName);;

        try {
            // Kiểm tra và tạo thư mục gốc temp_code nếu chưa tồn tại
            Path tempCodeDir = Paths.get("temp_code");
            if (!Files.exists(tempCodeDir)) {
                Files.createDirectories(tempCodeDir);  // Tạo thư mục temp_code nếu không có
            }

            // Tạo thư mục con test (hoặc theo tên được tạo cho bài)
            Files.createDirectories(workingDir);

            // Tạo file code .java trong thư mục vừa tạo
            Path sourceFile = workingDir.resolve("Main.java");
            Files.writeString(sourceFile, request.getSourceCode());

            // Biên dịch file Java: javac Main.java
            CodeExecutionUtil.runCommand(List.of("javac", "Main.java"), workingDir.toFile());

            // Chạy file vừa biên dịch: java Main
            String output = CodeExecutionUtil.runCommand(List.of("java", "Main"), workingDir.toFile());

            // Trả kết quả thành công (status = SUCCESS)
            return new JudgeRunResponseDTO(output, "SUCCESS", "");

        } catch (IOException | InterruptedException e) {
            System.out.println("ERROR IOException");
            return new JudgeRunResponseDTO("", "ERROR", e.getMessage());
        } catch (CodeExecutionUtil.CompilationException e) {
            // Xử lý lỗi biên dịch
            return new JudgeRunResponseDTO(e.getOutput(), "COMPILATION_ERROR", "Compilation failed");
        } catch (RuntimeException e) {
            System.out.println("ERROR RuntimeException");
            return new JudgeRunResponseDTO("", "ERROR", e.getMessage());
        } finally {
            // Dọn dẹp thư mục
            try {
                CodeExecutionUtil.deleteDirectoryRecursively(workingDir);
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }}

package com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices;

import com.codecampushubt.NCKH2024TQQD.dao.CodingExerciseRepository;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodingExerciseServiceImpl implements CodingExerciseService {
    private final CodingExerciseRepository codingExerciseRepository;

    @Autowired
    public CodingExerciseServiceImpl(CodingExerciseRepository codingExerciseRepository) {
        this.codingExerciseRepository = codingExerciseRepository;
    }

    @Override
    public List<CodingExerciseDTO> getCodingExerciseDTOByLessonSlug(String theSlug) {
        return codingExerciseRepository.getCodingExerciseDTOByLessonSlug(theSlug);
    }

}

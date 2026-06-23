package com.cmps.spring.repository.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cmps.spring.entity.ExportDestination;
import com.cmps.spring.repository.ExportDestinationRepository;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExportService {

    private final ExportDestinationRepository exportRepository;

    public ExportService(ExportDestinationRepository exportRepository) {
        this.exportRepository = exportRepository;
    }

    /**
     * Registers multiple export destinations in a single transaction.
     */
    public void registerExportDestinations() {
        List<ExportDestination> list = Arrays.asList(
            new ExportDestination(15, "パローヌ国", 200, "中部"), // Removed the L
            new ExportDestination(22, "トカンタ国", 150, "北洋"), // Removed the L
            new ExportDestination(23, "アルファ帝国", 120, "北洋"),
            new ExportDestination(25, "リトール王国", 150, "南洋"),
            new ExportDestination(31, "タハル王国", 240, "北洋"),
            new ExportDestination(32, "サザンナ王国", 80, "南洋"),
            new ExportDestination(33, "マリヨン国", 300, "中部")
        );

        exportRepository.saveAll(list);
    }
}
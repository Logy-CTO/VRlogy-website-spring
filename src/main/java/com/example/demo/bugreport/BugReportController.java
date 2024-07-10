package com.example.demo.bugreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class BugReportController {

    @Autowired
    private BugReportRepository bugReportRepository;

    @PostMapping
    public BugReport createBugReport(@RequestBody BugReport bugReport) {
        return bugReportRepository.save(bugReport);
    }

    @GetMapping
    public List<BugReport> getAllBugReports() {
        return bugReportRepository.findAll();
    }
}

package com.abc.performance.rest;

import com.abc.performance.domain.Result;
import com.abc.performance.service.PerformanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class QueryController {
    private final PerformanceService service;

    @PostMapping("/analyze")
    public ResponseEntity<List<Result>> analyzeQuery(@RequestBody Request request) {
        List<Result> analyze = service.analyze(request.getQueries());
        return ResponseEntity.ok(analyze);
    }
}

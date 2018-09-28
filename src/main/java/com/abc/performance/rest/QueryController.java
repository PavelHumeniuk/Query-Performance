package com.abc.performance.rest;

import com.abc.performance.domain.Report;
import com.abc.performance.service.ContainerResolverService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class QueryController {
    private final ContainerResolverService service;

    /**
     * @param request - {@link Request}
     * @return report - list of report {@link Report}
     */
    @PostMapping("/analyze")
    public ResponseEntity<List<Report>> analyzeQueries(@RequestBody Request request) {
        List<Report> analyze = service.resolve(request.getQueries());
        return ResponseEntity.ok(analyze);
    }
}

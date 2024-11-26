package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.CompanyService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @PostMapping("/company")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        return ResponseEntity.ok(this.companyService.handleSaveCompany(company));
//        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(company));
    }
    @GetMapping("/company")
    public ResponseEntity<ResultPaginationDTO> getAllCompanies(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional

    ) {
        String sCurrent=currentOptional.orElse("");
        String sPageSize=pageSizeOptional.orElse("");
        int current=Integer.parseInt(sCurrent);
        int pageSize=Integer.parseInt(sPageSize);
        Pageable pageable= PageRequest.of(current-1, pageSize);
        return ResponseEntity.ok(this.companyService.handleGetAllCompany(pageable));
    }
    @PutMapping("/company")
    public ResponseEntity<Company>updateCompany (@Valid @RequestBody Company reqCompany) {
        return ResponseEntity.ok(this.companyService.handleUpdateCompany(reqCompany));

    }
    @DeleteMapping("/company/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
         this.companyService.handleDeleteCompany(id);
        return ResponseEntity.ok("Xoá thành công");
    }

}

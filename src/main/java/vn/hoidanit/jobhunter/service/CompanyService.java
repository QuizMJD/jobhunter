package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.dto.Meta;
import vn.hoidanit.jobhunter.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    public Company handleSaveCompany(Company company) {
        return this.companyRepository.save(company);
    }
    public Company getCompanyByID(Long id) {
        return this.companyRepository.findById(id).orElse(null);
    }
    public ResultPaginationDTO handleGetAllCompany(Pageable pageable) {
        Page<Company>pageCompany=this.companyRepository.findAll(pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();
        mt.setPage(pageCompany.getNumber()+1);
        mt.setPageSize(pageCompany.getSize());
        mt.setPages(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getTotalElements());
        rs.setMeta(mt);
        rs.setResult(pageCompany.getContent());
        return rs;

    }
    public Company handleUpdateCompany(Company reqCompany) {
        Company currentCompany=getCompanyByID(reqCompany.getId());
        if (currentCompany != null) {
            currentCompany.setName(reqCompany.getName());
            currentCompany.setDescription(reqCompany.getDescription());
            currentCompany.setAddress(reqCompany.getAddress());
            currentCompany.setLogo(reqCompany.getLogo());

        }
        return this.handleSaveCompany(currentCompany);

    }
    public void handleDeleteCompany(Long id) {
        this.companyRepository.deleteById(id);
    }

}

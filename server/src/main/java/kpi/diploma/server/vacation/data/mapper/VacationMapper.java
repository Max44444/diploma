package kpi.diploma.server.vacation.data.mapper;

import kpi.diploma.server.vacation.data.domain.UserVacations;
import kpi.diploma.server.vacation.data.domain.Vacation;
import kpi.diploma.server.vacation.data.domain.VacationTemplate;
import kpi.diploma.server.vacation.data.dto.CreateVacationRequest;
import kpi.diploma.server.vacation.data.dto.Employee2ApproverDto;
import kpi.diploma.server.vacation.data.dto.UserVacationsData;
import kpi.diploma.server.vacation.data.dto.VacationDetails;
import kpi.diploma.server.vacation.data.repository.VacationTemplateRepository;
import kpi.diploma.server.vacation.exception.TemplateNotFoundException;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class VacationMapper {

    @Autowired
    private VacationTemplateRepository vacationTemplateRepository;

    @Mapping(target = "approver", expression = "java(employee2Approver.approver())")
    @Mapping(target = "employee", expression = "java(employee2Approver.employee())")
    @Mapping(target = "vacationTemplate", source = "templateId", qualifiedByName = "findTemplateById")
    public abstract Vacation mapVacation(CreateVacationRequest request, @Context Employee2ApproverDto employee2Approver);

    @Mapping(target = "template.id", source = "vacationTemplate.id")
    @Mapping(target = "template.name", source = "vacationTemplate.name")
    @Mapping(target = "approver.email", source = "approver.username")
    @Mapping(target = "approver.name", source = "approver.name")
    @Mapping(target = "employee.email", source = "employee.username")
    @Mapping(target = "employee.name", source = "employee.name")
    public abstract VacationDetails mapVacationDetails(Vacation vacation);

    public abstract List<VacationDetails> mapVacationDetails(List<Vacation> vacations);

    @Mapping(target = "template.id", source = "template.id")
    @Mapping(target = "template.name", source = "template.name")
    public abstract UserVacationsData mapUserVacations(UserVacations userVacations);

    public abstract List<UserVacationsData> mapUserVacations(List<UserVacations> userVacations);

    @Named("findTemplateById")
    protected VacationTemplate findTemplateById(UUID templateId) {
        return vacationTemplateRepository.findById(templateId).orElseThrow(TemplateNotFoundException::new);
    }
}

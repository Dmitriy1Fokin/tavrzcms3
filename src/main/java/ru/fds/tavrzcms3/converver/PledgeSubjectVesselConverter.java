package ru.fds.tavrzcms3.converver;

import ru.fds.tavrzcms3.domain.*;
import ru.fds.tavrzcms3.dto.PledgeSubjectVesselDto;
import ru.fds.tavrzcms3.service.*;

import java.util.ArrayList;
import java.util.List;

public class PledgeSubjectVesselConverter implements ConverterDto<PledgeSubjectVessel, PledgeSubjectVesselDto> {

    private final PledgeAgreementService pledgeAgreementService;
    private final CostHistoryService costHistoryService;
    private final MonitoringService monitoringService;
    private final EncumbranceService encumbranceService;
    private final InsuranceService insuranceService;

    public PledgeSubjectVesselConverter(PledgeAgreementService pledgeAgreementService,
                                        CostHistoryService costHistoryService,
                                        MonitoringService monitoringService,
                                        EncumbranceService encumbranceService,
                                        InsuranceService insuranceService) {
        this.pledgeAgreementService = pledgeAgreementService;
        this.costHistoryService = costHistoryService;
        this.monitoringService = monitoringService;
        this.encumbranceService = encumbranceService;
        this.insuranceService = insuranceService;
    }

    @Override
    public PledgeSubjectVessel toEntity(PledgeSubjectVesselDto dto) {
        List<PledgeAgreement> pledgeAgreementCollection = pledgeAgreementService.getPledgeAgreementsByIds(dto.getPledgeAgreementsIds());
        List<CostHistory> historyCollection = costHistoryService.getCostHistorybyIds(dto.getCostHistoriesIds());
        List<Monitoring> monitoringCollection = monitoringService.getMonitoringByIds(dto.getMonitoringIds());
        List<Encumbrance> encumbranceCollection = encumbranceService.getEncumbranceByIds(dto.getEncumbrancesIds());
        List<Insurance> insuranceCollection = insuranceService.getInsurancesByIds(dto.getInsurancesIds());

        return PledgeSubjectVessel.builder()
                .pledgeSubjectId(dto.getPledgeSubjectId())
                .name(dto.getName())
                .liquidity(dto.getLiquidity())
                .rsDz(dto.getRsDz())
                .rsZz(dto.getRsZz())
                .zsDz(dto.getZsDz())
                .zsZz(dto.getZsZz())
                .ss(dto.getSs())
                .dateMonitoring(dto.getDateMonitoring())
                .dateConclusion(dto.getDateConclusion())
                .statusMonitoring(dto.getStatusMonitoring())
                .typeOfCollateral(dto.getTypeOfCollateral())
                .typeOfPledge(dto.getTypeOfPledge())
                .typeOfMonitoring(dto.getTypeOfMonitoring())
                .adressRegion(dto.getAdressRegion())
                .adressDistrict(dto.getAdressDistrict())
                .adressCity(dto.getAdressCity())
                .adressStreet(dto.getAdressStreet())
                .adressBuilbing(dto.getAdressBuilbing())
                .adressPemises(dto.getAdressPemises())
                .insuranceObligation(dto.getInsuranceObligation())
                .pledgeAgreements(pledgeAgreementCollection)
                .costHistories(historyCollection)
                .monitorings(monitoringCollection)
                .encumbrances(encumbranceCollection)
                .insurances(insuranceCollection)
                .imo(dto.getImo())
                .mmsi(dto.getMmsi())
                .flag(dto.getFlag())
                .vesselType(dto.getVesselType())
                .grossTonnage(dto.getGrossTonnage())
                .deadweight(dto.getDeadweight())
                .yearBuilt(dto.getYearBuilt())
                .statusVessel(dto.getStatusVessel())
                .build();
    }

    @Override
    public PledgeSubjectVesselDto toDto(PledgeSubjectVessel entity) {
        List<Long> pledgeAgreementsIds = new ArrayList<>();
        for (PledgeAgreement pa : pledgeAgreementService.getAllPledgeAgreementByPLedgeSubject(entity))
            pledgeAgreementsIds.add(pa.getPledgeAgreementId());

        List<Long> costHistoriesIds = new ArrayList<>();
        for (CostHistory ch : costHistoryService.getCostHistoryPledgeSubject(entity))
            costHistoriesIds.add(ch.getCostHistoryId());

        List<Long> monitoringIds = new ArrayList<>();
        for(Monitoring mon : monitoringService.getMonitoringByPledgeSubject(entity))
            monitoringIds.add(mon.getMonitoringId());

        List<Long> encumbrancesIds = new ArrayList<>();
        for(Encumbrance enc : encumbranceService.getEncumbranceByPledgeSubject(entity))
            encumbrancesIds.add(enc.getEncumbranceId());

        List<Long> insurancesIds = new ArrayList<>();
        for(Insurance ins : insuranceService.getInsurancesByPledgeSubject(entity))
            insurancesIds.add(ins.getInsuranceId());

        StringBuilder fullAddress = new StringBuilder();
        if(!entity.getAdressRegion().isEmpty())
            fullAddress.append(entity.getAdressRegion());
        if(!entity.getAdressDistrict().isEmpty())
            fullAddress.append(", ").append(entity.getAdressDistrict());
        if(!entity.getAdressCity().isEmpty())
            fullAddress.append(", ").append(entity.getAdressCity());
        if(!entity.getAdressStreet().isEmpty())
            fullAddress.append(", ").append(entity.getAdressStreet());
        if(!entity.getAdressBuilbing().isEmpty())
            fullAddress.append(", ").append(entity.getAdressBuilbing());
        if(!entity.getAdressPemises().isEmpty())
            fullAddress.append(", ").append(entity.getAdressPemises());

        return PledgeSubjectVesselDto.builder()
                .pledgeSubjectId(entity.getPledgeSubjectId())
                .name(entity.getName())
                .liquidity(entity.getLiquidity())
                .rsDz(entity.getRsDz())
                .rsZz(entity.getRsZz())
                .zsDz(entity.getZsDz())
                .zsZz(entity.getZsZz())
                .ss(entity.getSs())
                .dateMonitoring(entity.getDateMonitoring())
                .dateConclusion(entity.getDateConclusion())
                .statusMonitoring(entity.getStatusMonitoring())
                .typeOfCollateral(entity.getTypeOfCollateral())
                .typeOfPledge(entity.getTypeOfPledge())
                .typeOfMonitoring(entity.getTypeOfMonitoring())
                .adressRegion(entity.getAdressRegion())
                .adressDistrict(entity.getAdressDistrict())
                .adressCity(entity.getAdressCity())
                .adressStreet(entity.getAdressStreet())
                .adressBuilbing(entity.getAdressBuilbing())
                .adressPemises(entity.getAdressPemises())
                .insuranceObligation(entity.getInsuranceObligation())
                .pledgeAgreementsIds(pledgeAgreementsIds)
                .costHistoriesIds(costHistoriesIds)
                .monitoringIds(monitoringIds)
                .encumbrancesIds(encumbrancesIds)
                .insurancesIds(insurancesIds)
                .imo(entity.getImo())
                .mmsi(entity.getMmsi())
                .flag(entity.getFlag())
                .vesselType(entity.getVesselType())
                .grossTonnage(entity.getGrossTonnage())
                .deadweight(entity.getDeadweight())
                .yearBuilt(entity.getYearBuilt())
                .statusVessel(entity.getStatusVessel())
                .fullAddress(fullAddress.toString())
                .build();
    }
}

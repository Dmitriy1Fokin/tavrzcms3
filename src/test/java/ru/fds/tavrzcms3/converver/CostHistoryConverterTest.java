package ru.fds.tavrzcms3.converver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.fds.tavrzcms3.domain.CostHistory;
import ru.fds.tavrzcms3.domain.PledgeSubject;
import ru.fds.tavrzcms3.dto.CostHistoryDto;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CostHistoryConverterTest {

    @Autowired
    CostHistoryConverter costHistoryConverter;

    @Test
    public void toEntity() {
        CostHistoryDto costHistoryDto = CostHistoryDto.builder()
                .costHistoryId(1L)
                .dateConclusion(new Date())
                .zsDz(1)
                .zsZz(2)
                .rsDz(3)
                .rsZz(4)
                .ss(5)
                .appraiser("appraiser name")
                .appraisalReportNum("report num")
                .appraisalReportDate(new Date())
                .pledgeSubjectId(1L)
                .build();

        CostHistory costHistory = costHistoryConverter.toEntity(costHistoryDto);

        assertEquals(costHistoryDto.getCostHistoryId(), costHistory.getCostHistoryId());
        assertEquals(costHistoryDto.getDateConclusion(), costHistory.getDateConclusion());
        assertEquals(costHistoryDto.getZsDz(), costHistory.getZsDz(), 0);
        assertEquals(costHistoryDto.getZsZz(), costHistory.getZsZz(), 0);
        assertEquals(costHistoryDto.getRsDz(), costHistory.getRsDz(), 0);
        assertEquals(costHistoryDto.getRsZz(), costHistory.getRsZz(),0);
        assertEquals(costHistoryDto.getSs(), costHistory.getSs(), 0);
        assertEquals(costHistoryDto.getAppraiser(), costHistory.getAppraiser());
        assertEquals(costHistoryDto.getAppraisalReportNum(), costHistory.getAppraisalReportNum());
        assertEquals(costHistoryDto.getAppraisalReportDate(), costHistory.getAppraisalReportDate());
        assertEquals(costHistoryDto.getPledgeSubjectId(), costHistory.getPledgeSubject().getPledgeSubjectId());
    }

    @Test
    public void toDto() {
        CostHistory costHistory = CostHistory.builder()
                .costHistoryId(1L)
                .dateConclusion(new Date())
                .zsDz(1)
                .zsZz(2)
                .rsDz(3)
                .rsZz(4)
                .ss(5)
                .appraiser("appraiser name")
                .appraisalReportNum("report num")
                .appraisalReportDate(new Date())
                .pledgeSubject(new PledgeSubject().builder().pledgeSubjectId(1L).build())
                .build();

        CostHistoryDto costHistoryDto = costHistoryConverter.toDto(costHistory);

        assertEquals(costHistoryDto.getCostHistoryId(), costHistory.getCostHistoryId());
        assertEquals(costHistoryDto.getDateConclusion(), costHistory.getDateConclusion());
        assertEquals(costHistoryDto.getZsDz(), costHistory.getZsDz(), 0);
        assertEquals(costHistoryDto.getZsZz(), costHistory.getZsZz(), 0);
        assertEquals(costHistoryDto.getRsDz(), costHistory.getRsDz(), 0);
        assertEquals(costHistoryDto.getRsZz(), costHistory.getRsZz(),0);
        assertEquals(costHistoryDto.getSs(), costHistory.getSs(), 0);
        assertEquals(costHistoryDto.getAppraiser(), costHistory.getAppraiser());
        assertEquals(costHistoryDto.getAppraisalReportNum(), costHistory.getAppraisalReportNum());
        assertEquals(costHistoryDto.getAppraisalReportDate(), costHistory.getAppraisalReportDate());
        assertEquals(costHistoryDto.getPledgeSubjectId(), costHistory.getPledgeSubject().getPledgeSubjectId());


    }
}
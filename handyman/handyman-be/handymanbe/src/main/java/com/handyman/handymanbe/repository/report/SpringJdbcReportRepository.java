package com.handyman.handymanbe.repository.report;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.domain.technician.TechnicianId;
import com.handyman.handymanbe.domain.technician.TechnicianLastName;
import com.handyman.handymanbe.domain.technician.TechnicianName;
import com.handyman.handymanbe.repository.technician.TechnicianRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SpringJdbcReportRepository implements ReportRepository {
        private final JdbcTemplate jdbcTemplate;

        public SpringJdbcReportRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        private final RowMapper<Report> rowMapper = (resultSet, rowNum) -> {
            String technicianId = resultSet.getString("technician_id");
            String serviceId = resultSet.getString("service_id");
            LocalDateTime initDate = resultSet.getTimestamp(3).toLocalDateTime();
            LocalDateTime endDate = resultSet.getTimestamp(4).toLocalDateTime();


            return new Report(
                    technicianId,
                    serviceId,
                    initDate,
                    endDate
            );
        };

    @Override
    public List<Report> list() {
        String sqlQuery = "SELECT * FROM report";
        return jdbcTemplate.query(sqlQuery, rowMapper);
    }

        @Override
        public void create(Report report) {
            String sqlQuery = "INSERT INTO report(technician_id, service_id, init_date, end_date) VALUES(?, ?, ?, ?)";
            jdbcTemplate.update(sqlQuery, ps -> {
                ps.setString(1, report.getTechnicianId());
                ps.setString(2, report.getServiceId());
                ps.setDate(3, Date.valueOf(report.getInitDate().toLocalDate()));
                ps.setDate(4, Date.valueOf(report.getEndDate().toLocalDate()));
            });
        }


}



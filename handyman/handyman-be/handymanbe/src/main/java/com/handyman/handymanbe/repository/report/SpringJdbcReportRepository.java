package com.handyman.handymanbe.repository.report;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.service.ServiceId;
import com.handyman.handymanbe.domain.technician.TechnicianId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SpringJdbcReportRepository implements ReportRepository {
        private final JdbcTemplate jdbcTemplate;

        public SpringJdbcReportRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        private final RowMapper<Report> rowMapper = (resultSet, rowNum) -> {
            TechnicianId technicianId = TechnicianId.fromString(resultSet.getString("technician_id"));
            ServiceId serviceId = ServiceId.fromString(resultSet.getString("service_id"));
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
        String sqlQuery = "INSERT INTO report(technician_id, service_id, init_date, end_date, week_of_year) VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, ps -> {
            ps.setString(1, report.getTechnicianId().toString());
            ps.setString(2, report.getServiceId().toString());
            ps.setTimestamp(3, Timestamp.valueOf(report.getInitDate()));
            ps.setTimestamp(4, Timestamp.valueOf(report.getEndDate()));
            ps.setInt(5, report.getWeekOfYear());
        });
    }

    @Override
    public List<Report> getAllFromTechnicianByWeek(Integer week, TechnicianId id) {
        String query = "SELECT * FROM report WHERE report.week_of_year = ? AND report.technician_id = ? ORDER BY init_date ASC";
        //ORDER BY ASC
        return jdbcTemplate.query(query, rowMapper, week, id.toString());
    }
}



package com.handyman.handymanbe.repository.technician;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.service.ServiceId;
import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.domain.technician.TechnicianId;
import com.handyman.handymanbe.domain.technician.TechnicianLastName;
import com.handyman.handymanbe.domain.technician.TechnicianName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SpringJdbcTechnicianRepository implements TechnicianRepository {
        private final JdbcTemplate jdbcTemplate;

        public SpringJdbcTechnicianRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }
        //https://www.tutorialspoint.com/springjdbc/springjdbc_rowmapper.htm
        private final RowMapper<Technician> rowMapper = (resultSet, rowNum) -> {
            TechnicianId technicianId = TechnicianId.fromString(
                    resultSet.getString("id")
            );
            TechnicianName technicianName = new TechnicianName(resultSet.getString("name"));
            TechnicianLastName technicianLastName = new TechnicianLastName(resultSet.getString("last_name"));
            List<Report> reports = getAllReports(technicianId);

            return new Technician(
                    technicianId,
                    technicianName,
                    technicianLastName,
                    reports
            );
        };
        //Here, we must mapper the report table
        private final RowMapper<Report> reportRowMapper = (resultSet, rowNum) -> {
            TechnicianId technicianId = TechnicianId.fromString(
                resultSet.getString("technician_id")
            );
            ServiceId serviceId = ServiceId.fromString(
                resultSet.getString("service_id"));
            LocalDateTime initDateTime = resultSet.getTimestamp("init_date").toLocalDateTime();
            LocalDateTime endDateTime = resultSet.getTimestamp("end_date").toLocalDateTime();
            return new Report(
                technicianId,
                serviceId,
                initDateTime,
                endDateTime
            );
        };


        @Override
        public List<Technician> list() {
            String sqlQuery = "SELECT * FROM technician";
            return jdbcTemplate.query(sqlQuery, rowMapper);
        }

        @Override
        public Technician findOne(TechnicianId id) {
            String sqlQuery = "SELECT * FROM technician WHERE id = ?";

            return jdbcTemplate.queryForObject(sqlQuery, rowMapper, id.toString());
        }

        @Override
        public void create(Technician technician) {
            String sqlQuery = "INSERT INTO technician(id, name, last_name) VALUES(?, ?, ?)";
            jdbcTemplate.update(sqlQuery, ps -> {
                ps.setString(1, technician.getId().toString());
                ps.setString(2, technician.getName().toString());
                ps.setString(3, technician.getLastName().toString());
            });
        }

        @Override
        public void update(TechnicianId id, Technician technician) {
            String sqlQuery = "UPDATE technician SET name = ?, last_name = ? WHERE id = ?";
            jdbcTemplate.update(sqlQuery, ps -> {
                ps.setString(1, technician.getName().toString());
                ps.setString(2, technician.getLastName().toString());
                ps.setString(3, id.toString());
            });
        }

        @Override
        public void delete(TechnicianId id) {
            String sqlQuery = "DELETE FROM technician WHERE id = ?";
            jdbcTemplate.update(sqlQuery, id.toString());
        }

        @Override
        public List<Report> getAllReports(TechnicianId technicianId){
            //Inner join (Important): https://www.w3schools.com/sql/sql_join_inner.asp
            String query = "SELECT technician_id, service_id, init_date, end_date FROM report INNER JOIN technician ON report.technician_id = technician.id AND report.technician_id = ?";
            return jdbcTemplate.query(query, reportRowMapper, technicianId.toString());
        }
}


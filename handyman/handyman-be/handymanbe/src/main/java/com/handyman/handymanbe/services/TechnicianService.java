package com.handyman.handymanbe.services;

import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.domain.technician.TechnicianId;
import com.handyman.handymanbe.repository.technician.TechnicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianService {

        private TechnicianRepository repository;

        public TechnicianService(TechnicianRepository repository) {
            this.repository = repository;
        }


        public List<Technician> listTechnician() {
            return repository.list();
        }


        public Technician createTechnician(Technician technician) {
            repository.create(technician);
            return technician;
        }


        public Technician getTechnician(TechnicianId technicianId) {
            return repository.findOne(technicianId);
        }


        public void deleteTechnician(TechnicianId technicianId) {
            repository.delete(technicianId);
        }


        public Technician updateTechnician(TechnicianId technicianId, Technician technician) {
            repository.update(technicianId, technician);

            return repository.findOne(technicianId);
        }

}



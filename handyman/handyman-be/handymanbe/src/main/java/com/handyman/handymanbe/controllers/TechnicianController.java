package com.handyman.handymanbe.controllers;


import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.domain.technician.TechnicianId;
import com.handyman.handymanbe.domain.technician.TechnicianLastName;
import com.handyman.handymanbe.domain.technician.TechnicianName;
import com.handyman.handymanbe.model.technician.CreateTechnicianInput;
import com.handyman.handymanbe.model.technician.CreateTechnicianOutput;
import com.handyman.handymanbe.model.technician.UpdateTechnicianInput;
import com.handyman.handymanbe.model.technician.UpdateTechnicianOutput;
import com.handyman.handymanbe.services.TechnicianService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/technician")
public class TechnicianController {
    //Spring boot decorators

    //Here, we will create the endpoints fot communicate us with the frontend using HTTP methods Hand in hand with Api-rest

        //Connect Repository with controller using repository attribute name.
        private final TechnicianService services;

        public TechnicianController(TechnicianService services) {

            this.services = services;
        }
        //Request HTTP using decorators
        @GetMapping
        //Return a technician list
        public List<Technician> listTechnician() {
            return services.listTechnician();
        }
        //Post decorator for create any product from Clientside
        @PostMapping
        public CreateTechnicianOutput createTechnician(@RequestBody CreateTechnicianInput input) {
            TechnicianId random = TechnicianId.random();
            TechnicianName technicianName = new TechnicianName(input.getName());
            TechnicianLastName technicianLastName = new TechnicianLastName(input.getLastName());
            List<Report> reports = new ArrayList<>();

            Technician technician = new Technician(random, technicianName, technicianLastName, reports);
            Technician createdTechnician = services.createTechnician(technician);

            return new CreateTechnicianOutput(createdTechnician);
        }
        //Get technician by id
        @GetMapping(value="/{technicianId}")
        public Technician getTechnician(
                @PathVariable("technicianId") String techId) {
            final TechnicianId id = TechnicianId.fromString(techId);
            return services.getTechnician(id);
        }
        //Delete by id reference
        @DeleteMapping(value = "/{technicianId}")
        public void deleteTechnician(
                @PathVariable("technicianId") String techId) {
            final TechnicianId technicianFound = TechnicianId.fromString(techId);
            services.deleteTechnician(technicianFound);
        }
        //Update a product by id reference
        @PutMapping(value = "/{referenceId}") //Update receive body from client, likewise Post http
        //Post, Patch and Put need a body
        public UpdateTechnicianOutput updateTechnician(@PathVariable("referenceId") String unsafeId, @RequestBody UpdateTechnicianInput input) {
            final TechnicianId id = TechnicianId.fromString(unsafeId);
            List<Report> reports = services.getAllReports(id);
            Technician newTechnician = new Technician(id, new TechnicianName(input.getName()), new TechnicianLastName(input.getLastName()), reports);
            final Technician updated = services.updateTechnician(id, newTechnician);
            return new UpdateTechnicianOutput(updated);
        }
}


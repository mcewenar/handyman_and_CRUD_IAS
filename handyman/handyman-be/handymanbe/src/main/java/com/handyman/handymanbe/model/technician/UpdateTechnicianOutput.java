package com.handyman.handymanbe.model.technician;

import com.handyman.handymanbe.domain.technician.Technician;

public class UpdateTechnicianOutput {
        private Technician technician;

        public UpdateTechnicianOutput() {
        }

        public UpdateTechnicianOutput(Technician technician) {
            this.technician = technician;
        }

        public Technician getTechnician() {
            return technician;
        }

        public void setTechnician(Technician technician) {
            this.technician = technician;
        }

}

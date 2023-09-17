package de.wirschiffendas.algorithmservice.kafka;

import de.wirschiffendas.algorithmservice.data.JobStatus;

import java.util.UUID;

public class EquipJobUpdateMessage {
    public UUID analyzerJobId;
    public int equipJobIndex;
    public JobStatus statusUpdate;

    public EquipJobUpdateMessage(){}

    public EquipJobUpdateMessage(UUID analyzerJobId, int equipJobIndex, JobStatus statusUpdate){
        this.analyzerJobId = analyzerJobId;
        this.equipJobIndex = equipJobIndex;
        this.statusUpdate = statusUpdate;
    }
}

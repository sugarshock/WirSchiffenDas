package de.wirschiffendas.shared_classes.kafka;


import java.util.UUID;
import de.wirschiffendas.shared_classes.data.JobStatus;
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

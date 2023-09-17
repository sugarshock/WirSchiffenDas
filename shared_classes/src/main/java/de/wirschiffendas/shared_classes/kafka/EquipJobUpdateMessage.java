package de.wirschiffendas.shared_classes.kafka;


import java.util.UUID;
import de.wirschiffendas.shared_classes.data.JobStatus;
import lombok.Getter;
import lombok.Setter;

public class EquipJobUpdateMessage {
    @Getter
    @Setter
    public UUID analyzerJobId;
    @Getter
    @Setter
    public int equipJobIndex;
    @Getter
    @Setter
    public JobStatus statusUpdate;

    public EquipJobUpdateMessage(){}

    public EquipJobUpdateMessage(UUID analyzerJobId, int equipJobIndex, JobStatus statusUpdate){
        this.analyzerJobId = analyzerJobId;
        this.equipJobIndex = equipJobIndex;
        this.statusUpdate = statusUpdate;
    }

}

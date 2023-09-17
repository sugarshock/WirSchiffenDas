package de.wirschiffendas.algorithmservice.data.entity;

import de.wirschiffendas.algorithmservice.data.JobStatus;
import lombok.Getter;
import lombok.Setter;

public class EquipJob extends AbstractEntity {
    @Getter
    private EngineConfiguration.OptEquipType equipType;
    @Getter @Setter
    private JobStatus status;

    public EquipJob(EngineConfiguration.OptEquipType equipType)
    {
        this.equipType = equipType;
        status = JobStatus.NOT_STARTED;
    }
}

package de.wirschiffendas.shared_classes.data.entity;

import lombok.Getter;
import lombok.Setter;
import de.wirschiffendas.shared_classes.data.*;

public class EquipJob extends AbstractEntity{
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

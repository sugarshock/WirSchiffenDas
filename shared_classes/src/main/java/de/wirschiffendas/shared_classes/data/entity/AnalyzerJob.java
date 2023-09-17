package de.wirschiffendas.shared_classes.data.entity;

import de.wirschiffendas.shared_classes.data.JobStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AnalyzerJob extends AbstractEntity{
    @Getter
    @Setter
    private EngineConfiguration engineConfiguration;
    @Getter
    @Setter
    private List<EquipJob> equipmentJobs = new ArrayList<>();

    public AnalyzerJob(){
        super();
    }

    public AnalyzerJob(EngineConfiguration engineConfiguration)
    {
        super();
        this.engineConfiguration = engineConfiguration;
        for(EngineConfiguration.OptEquipType equip : engineConfiguration.getOptionalEquipment().keySet())
        {
            equipmentJobs.add(new EquipJob(equip));
        }
    }

    public JobStatus getStatus(){
        JobStatus last = JobStatus.NOT_STARTED;
        for(var job : equipmentJobs){
            if(job.getStatus().ordinal() > last.ordinal()){
                last = job.getStatus();
            }
        }
        return last;
    }
}

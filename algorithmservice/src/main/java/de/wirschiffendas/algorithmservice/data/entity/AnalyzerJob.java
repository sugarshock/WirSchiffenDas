package de.wirschiffendas.algorithmservice.data.entity;

import de.wirschiffendas.algorithmservice.data.JobStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class AnalyzerJob extends AbstractEntity {
    @Getter
    private EngineConfiguration engineConfiguration;
    @Getter
    private List<EquipJob> equipmentJobs = new ArrayList<>();

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

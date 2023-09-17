package de.wirschiffendas.algorithmservice.controller;

import de.wirschiffendas.algorithmservice.data.*;
import de.wirschiffendas.algorithmservice.data.entity.*;
import de.wirschiffendas.algorithmservice.kafka.EquipJobUpdateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Controller
public class AlgorithmController {

    public enum ALGORITHM_GROUP {Starting, Fluids, Electronics, Mechanics}

    @Value(value = "${wirschiffendas.algorithm}")
    private ALGORITHM_GROUP AlgorithmGroup;

    @Autowired
    private KafkaTemplate<String, EquipJobUpdateMessage> kafkaTemplate;

    public void analyze(AnalyzerJob analyzerJob)
    {
        var myJobs = getMyJobs(analyzerJob);

        myJobs.forEach(job -> updateStatusAndSendMessage(analyzerJob, job, JobStatus.RUNNING));

        try {
            // Analyzing
            Thread.sleep(2000 + (long)(Math.random() * 3000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        myJobs.forEach(job -> updateStatusAndSendMessage(analyzerJob, job, getRandomResult()));

    }

    private JobStatus getRandomResult(){
        if(Math.random() > 0.5)
            return JobStatus.OK;
        else
            return JobStatus.FAILED;
    }

    private void updateStatusAndSendMessage(AnalyzerJob analyzerJob, EquipJob equipJob, JobStatus status){
        equipJob.setStatus(status);
        var message = new EquipJobUpdateMessage(
                analyzerJob.getId(),
                analyzerJob.getEquipmentJobs().indexOf(equipJob),
                equipJob.getStatus()
        );

        //Send to Kafka
        CompletableFuture<SendResult<String, EquipJobUpdateMessage>> future = kafkaTemplate.send("jobUpdates", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent job=[" + message.equipJobIndex + "/" + message.statusUpdate +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                throw new MessageDeliveryException("Unable to send job=[" +
                        message.equipJobIndex + "/" + message.statusUpdate + "] due to : " + ex.getMessage());
            }
        });
    }

    private List<EquipJob> getMyJobs(AnalyzerJob analyzerJob){
        var myTypes = getAlgoMappings().get(AlgorithmGroup);

        var myJobs = analyzerJob.getEquipmentJobs().stream().filter(
                    job -> myTypes.contains(job.getEquipType()
                )).toList();
        return myJobs;
    }

    private static Map<ALGORITHM_GROUP, List<EngineConfiguration.OptEquipType>> getAlgoMappings()
    {
        var mapping = new HashMap<ALGORITHM_GROUP, List<EngineConfiguration.OptEquipType>>();

        mapping.put(ALGORITHM_GROUP.Starting, Arrays.asList(
                EngineConfiguration.OptEquipType.StartingSystem,
                EngineConfiguration.OptEquipType.AuxilliaryPTO
        ));

        mapping.put(ALGORITHM_GROUP.Mechanics, Arrays.asList(
                EngineConfiguration.OptEquipType.MountingSystem,
                EngineConfiguration.OptEquipType.PowerTransmission,
                EngineConfiguration.OptEquipType.GearboxOptions
        ));

        mapping.put(ALGORITHM_GROUP.Electronics, Arrays.asList(
                EngineConfiguration.OptEquipType.MonitoringControlSystem,
                EngineConfiguration.OptEquipType.EngineManagementSystem
        ));

        mapping.put(ALGORITHM_GROUP.Fluids, Arrays.asList(
                EngineConfiguration.OptEquipType.OilSystem,
                EngineConfiguration.OptEquipType.FuelSystem,
                EngineConfiguration.OptEquipType.CoolingSystem,
                EngineConfiguration.OptEquipType.ExhaustSystem
        ));

        return mapping;
    }

}

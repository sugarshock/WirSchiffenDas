package de.wirschiffendas.algorithmservice.kafka;

import de.wirschiffendas.algorithmservice.controller.AlgorithmController;
import de.wirschiffendas.algorithmservice.data.entity.AnalyzerJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyzerJobListener {
    @KafkaListener(topics = "analyzer", groupId = "1", containerFactory = "jobUpdateListenerContainerFactory")
    public void listenEquipJobUpdate(AnalyzerJob message, @Autowired AlgorithmController algorithmController) {
        algorithmController.analyze(message);
    }
}

package de.wirschiffendas.algorithmservice.kafka;

import de.wirschiffendas.algorithmservice.controller.AlgorithmController;
import de.wirschiffendas.shared_classes.data.entity.AnalyzerJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyzerJobListener {

    @Autowired
    AlgorithmController algorithmController;
    @KafkaListener(topics = "analyzer", groupId = "1", containerFactory = "jobUpdateListenerContainerFactory")
    public void listenEquipJobUpdate(AnalyzerJob message) {
        algorithmController.analyze(message);
    }
}

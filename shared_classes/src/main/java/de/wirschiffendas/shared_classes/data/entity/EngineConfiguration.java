package de.wirschiffendas.shared_classes.data.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class EngineConfiguration extends AbstractEntity{
    public enum OptEquipType {StartingSystem, AuxilliaryPTO, OilSystem, FuelSystem, CoolingSystem, ExhaustSystem, MountingSystem, EngineManagementSystem, MonitoringControlSystem, PowerTransmission, GearboxOptions};

    @Getter @Setter
    private String name = "New Ship Configuration";
    @Getter
    private HashMap<OptEquipType, String> OptionalEquipment = new HashMap<>();

}

package com.Gr00ze.drones.gui;

import com.Gr00ze.drones.entities.GenericDrone;

public class Screens {
    public static MyScreen getScreen(int entity_id, GenericDrone genericDrone){
        return new MyScreen(entity_id, genericDrone);
    }
}

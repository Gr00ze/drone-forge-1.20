package com.Gr00ze.drones_mod.entities;

public class DronePIDController {
    public float Kp,Ki,Kd;
    public float errorSum = 0, lastError = 0;

    public DronePIDController(float Kp,float Ki,float Kd){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
    }

    public float getAdjustmentFor(float targetRadiantAngle, float currentRadiantAngle){
        float deltaTime = 1; // if every 1 tick
        float error = targetRadiantAngle - currentRadiantAngle;
        errorSum += error;
        float errorRate = error - lastError / deltaTime;

        lastError = error;

        return Kp * error + Ki * errorSum + Kd * errorRate;

    }
}

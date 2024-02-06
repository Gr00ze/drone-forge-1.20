package com.Gr00ze.drones_mod.entities.controllers;

public class PIDController {
    public void set(float value, PIDParameter parameterType) {
        switch (parameterType) {
            case KP:
                this.Kp = value;
                break;
            case KI:
                this.Ki = value;
                break;
            case KD:
                this.Kd = value;
                break;

        }
    }

    public  enum PIDParameter{KP,KI,KD}
    public double Kp,Ki,Kd;
    public double errorSum = 0, lastError = 0;

    public PIDController(double Kp, double Ki, double Kd){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
    }

    public double calculate(double targetValue, double currentValue){

        double error = targetValue - currentValue;
        errorSum += error;
        double errorRate = error - lastError;

        lastError = error;

        return Kp * error + Ki * errorSum + Kd * errorRate;

    }

    public void resetErrors(){
        errorSum = 0;
        lastError = 0;
    }


    public Double getParameter(PIDParameter parameter){
        return switch (parameter) {
            case KD -> this.Kd;
            case KI -> this.Ki;
            case KP -> this.Kp;
        };


    }

}

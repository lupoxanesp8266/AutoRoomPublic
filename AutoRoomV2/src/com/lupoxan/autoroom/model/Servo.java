/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lupoxan.autoroom.model;

import java.io.IOException;

/**
 * Clase para utilizar el servo montado sobre la Cam
 * @since 21/11/2018
 * @author lupo.xan
 * @version 1.4
 */
public class Servo {
    
    private int bcmPin;
    private int pos;
    private Runtime runTime = Runtime.getRuntime();
    
    public Servo(int bcmPin){
        this.bcmPin = bcmPin;
        try {
            runTime.exec("gpio mode " + this.bcmPin + " pwm");
            runTime.exec("gpio pwm-ms");
            runTime.exec("gpio pwmc 192");
            runTime.exec("gpio pwmr 2000");
            runTime.exec("gpio pwm " + this.bcmPin + " 160");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void setPos(int pos) throws IOException{
        this.pos = pos;
        runTime.exec("gpio pwm " + this.bcmPin + " " + this.pos);
    }
    
    public int getPos(){
        return this.pos;
    }
    
}

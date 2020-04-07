/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lupoxan.autoroom.model;


import com.pi4j.temperature.TemperatureScale;
import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;

/**
 * Clase por la que se miden las temperaturas dadas las direcciones de cada sensor (W1)
 * @author lupo.xan
 * @since 23/01/2019
 * @version 0.5
 */
public class Ds18b20 {
    private final W1Master temp = new W1Master();//Nuevo objeto temp de clase W1Master
    private final Ficheros F = new Ficheros();
    /**
     * Constructor por defecto
     */
    public Ds18b20(){
        
    }
    /**
     * Metodo get para temperatura en Celsius
     * @param i True -- Interior; False -- Exterior
     * @return Temperatura en grados Celsius
     */
    protected double getTempC(boolean i){
        //W1Master temp = new W1Master();//Nuevo objeto temp de clase W1Master
        //Busca todos los sensores conectados por ONE-WIRE (W1)
        for(TemperatureSensor device : temp.getDevices(TemperatureSensor.class)){
            //Si contiene la direccion asignada
            if(device.getName().contains(F.prop(Constantes.INTERIOR)) && i == true)//Interior
                //Devuelve el valor de temperatura en celsius
                return device.getTemperature(TemperatureScale.CELSIUS);
            if(device.getName().contains(F.prop(Constantes.EXTERIOR)) && i == false)//Exterior
                return device.getTemperature(TemperatureScale.CELSIUS);
            //Si no, devuelve 0    
        }
        return 0;
    }
}

package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.Constants;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance;

    private Drivetrain () {

    }

    @Override
    public void init () {
        
    }

    @Override
    public void loop () {
        
    }

    @Override
    public void drive (Trajectory2d t) {
        
    }

    public static Drivetrain getInstance() {

        if (instance == null) {
            instance = new Drivetrain();
        }

        return instance;

    }

}

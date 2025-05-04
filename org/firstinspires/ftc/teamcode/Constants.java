package org.firstinspires.ftc.teamcode;

public final class Constants {

    public static final class Gamepad1 {

        public static final double joystickDeadzone = 0.10;

    }

    public static final class Gamepad2 {

        public static final double joystickDeadzone = 0.10;

    }

    public static final class Drivetrain {

        public static final double maxSpeed = 1;

    }

    public static final class Scoring {

        public static final class Arm {
            
            public static final int MIN = 0;
    
            public static final int COLLAPSED_INTO_ROBOT  = 0;
            public static final int COLLECT               = 250;
            public static final int CLEAR_BARRIER         = 230;
            public static final int SCORE_SPECIMEN        = 160;
            public static final int SCORE_SAMPLE_IN_LOW   = 160;
            public static final int ATTACH_HANGING_HOOK   = 120;
            public static final int WINCH_ROBOT           = 15;
            
            public static final int MAX = 3000;
            
            public static final int MAX_POWER = 1;
            
        }

        public static final class Wrist {
            
            final double FOLDED_IN   = 0.8333;
            final double FOLDED_OUT  = 0.5;
            
        }

        public static final class Intake {
            
            public static final double COLLECT    = -1.0;
            public static final double OFF        =  0.0;
            public static final double DEPOSIT    =  0.5;
            
        }
        
    }
    
}

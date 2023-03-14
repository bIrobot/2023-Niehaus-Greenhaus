package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import frc.robot.Constants.DriveConstants;

public class ClawModule {
    private final CANSparkMax m_clawSparkMax;
  
    public ClawModule(int clawCANId) {
        m_clawSparkMax = new CANSparkMax(clawCANId, MotorType.kBrushed);

        m_clawSparkMax.setIdleMode(IdleMode.kBrake);
        m_clawSparkMax.setOpenLoopRampRate(1);

        m_clawSparkMax.burnFlash();
        m_clawSparkMax.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 500);
    }

    public void closeClaw(double speed) {
        m_clawSparkMax.set(speed * -1);
    }

    public void openClaw(double speed) {
        m_clawSparkMax.set(speed);
    }

    public void stopMovingClaw() {
        m_clawSparkMax.set(DriveConstants.kStopClawSpeed);
    }
}

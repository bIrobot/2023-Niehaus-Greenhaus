package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;

public class ArmModule {
    private final CANSparkMax m_armSparkMax;
  
    private final RelativeEncoder m_armEncoder;
  
    private final SparkMaxPIDController m_armPIDController;

    public ArmModule(int armCANId) {
        m_armSparkMax = new CANSparkMax(armCANId, MotorType.kBrushless); 
        m_armEncoder = m_armSparkMax.getEncoder();
        m_armPIDController = m_armSparkMax.getPIDController();
        m_armPIDController.setFeedbackDevice(m_armEncoder);

        // Apply position and velocity conversion factors for the driving encoder. The
        // native units for position and velocity are rotations and RPM, respectively,
        // but we want meters and meters per second to use with WPILib's swerve APIs.
        m_armEncoder.setPositionConversionFactor(ModuleConstants.kDrivingEncoderPositionFactor);
        m_armEncoder.setVelocityConversionFactor(ModuleConstants.kDrivingEncoderVelocityFactor);

        // Set the PID gains for the driving motor. Note these are example gains, and you
        // may need to tune them for your own robot!
        m_armPIDController.setP(ModuleConstants.kDrivingP);
        m_armPIDController.setI(ModuleConstants.kDrivingI);
        m_armPIDController.setD(ModuleConstants.kDrivingD);
        m_armPIDController.setFF(ModuleConstants.kDrivingFF);
        m_armPIDController.setOutputRange(ModuleConstants.kDrivingMinOutput,
            ModuleConstants.kDrivingMaxOutput);

        m_armSparkMax.setIdleMode(IdleMode.kBrake);
        m_armSparkMax.setOpenLoopRampRate(1);

        m_armSparkMax.burnFlash();

        m_armSparkMax.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 500);
        m_armEncoder.setPosition(0);
    }

    public void liftArm() {
        m_armPIDController.setReference(DriveConstants.kLiftArmSpeed, CANSparkMax.ControlType.kVelocity);
    }

    public void lowerArm() {
        m_armPIDController.setReference(DriveConstants.kLowerArmSpeed, CANSparkMax.ControlType.kVelocity);
    }

    public void stopMovingArm() {
        m_armPIDController.setReference(DriveConstants.kStopArmSpeed, CANSparkMax.ControlType.kVelocity);
    }
}

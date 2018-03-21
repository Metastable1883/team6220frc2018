/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6220.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {
    private DifferentialDrive m_myRobot;
    private DigitalInput lsOut = new DigitalInput(1);
    private DigitalInput lsIn = new DigitalInput(2);
    private DigitalInput lsUp = new DigitalInput(3);
    private DigitalInput lsDown = new DigitalInput(4);
    private Counter countOut = new Counter(lsOut);
    private Counter countIn = new Counter(lsIn);
    private Counter countUp = new Counter(lsUp);
    private Counter countDown = new Counter(lsDown);
    private VictorSP grip = new  VictorSP(2);
    private VictorSP height1 = new  VictorSP(1);
    private VictorSP height0  = new VictorSP(0);
    private VictorSP aim = new VictorSP(3);
    private VictorSPX climb1 = new VictorSPX(4);
    private VictorSPX climb2 = new VictorSPX(5);
    //private Spark rSpark = new Spark(0); Fix Sparks
    //private Spark lSpark = new Spark(1);
    private WPI_TalonSRX four = new WPI_TalonSRX(4);
    private WPI_TalonSRX three = new WPI_TalonSRX(3);
    private WPI_TalonSRX two = new WPI_TalonSRX(2);
    private WPI_TalonSRX one = new WPI_TalonSRX(1);
    private SpeedControllerGroup gogo = new SpeedControllerGroup(one,two);
    private SpeedControllerGroup gogo2 = new SpeedControllerGroup(three,four);
    private Solenoid s1 = new Solenoid(0);
    private Solenoid s2 = new Solenoid(1);
    private Compressor c1 = new Compressor(0);
    private XboxController xbox;
    private Joystick stick;
    int time = 750;

    @Override
    public void robotInit()
    {
        m_myRobot = new DifferentialDrive(gogo,gogo2);
        xbox = new XboxController(0);
        stick = new Joystick(1);
    }

    @Override
    public void teleopPeriodic()
    {
        m_myRobot.arcadeDrive(xbox.getX(GenericHID.Hand.kLeft), xbox.getTriggerAxis(GenericHID.Hand.kRight) - (xbox.getTriggerAxis(GenericHID.Hand.kLeft)));
        aim.set(xbox.getY(GenericHID.Hand.kRight));
        if(xbox.getYButton())
        {
            climb1.set(null, 1);
            climb2.set(null, 1);
        }
        if(stick.getY() < 0)//countDown.get() == 0
        {
            height1.set(stick.getY());
            height0.set(stick.getY());
            countUp.reset();
        }
        if(stick.getY() > 0)//countUp.get() == 0
        {
            height1.set(stick.getY());
            height0.set(stick.getY());
            countDown.reset();
        }
		/*if(xbox.getXButton())
		{
			rSpark.set(.3);
			lSpark.set(-.3);
		}
		if(xbox.getAButton())
		{
			rSpark.set(-.3);
			lSpark.set(.3);
		}
		*/
        if(stick.getRawButton(1) == false && stick.getRawButton(2) == false)
        {
            grip.set(0);
        }
        if(stick.getRawButton(4))
        {
            s1.set(true);
            s2.set(false);
        }
        if(stick.getRawButton(3))
        {
            s1.set(false);
            s2.set(true);
        }
    }

    @Override
    public void autonomousPeriodic()
    {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0)
        {
            //RR option A
            if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'R')
            {
                if(time > 700)
                {
                    m_myRobot.arcadeDrive(.5, -90);
                    time--;
                    //straight to switch, place and rush scale
                }
            }
            //RL option A
            if(gameData.charAt(0) == 'R' && gameData.charAt(1) != 'R')
            {
                if(time > 700)
                {
                    m_myRobot.arcadeDrive(.5, -90);
                    time--;
                    //straight to switch, place and rush scale
                }
            }
            //LR option A
            if(gameData.charAt(0) == 'L' && gameData.charAt(1) != 'L')
            {
                if(time>700)
                {
                    m_myRobot.arcadeDrive(.5, -90);
                    time--;
                    //straight to right side of scale and place
                }
            }
            //LL option A
            if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L')
            {
                if(time>700)
                {
                    m_myRobot.arcadeDrive(.5, -90);
                    time--;
                    //place on left side of switch, then find box, then rush scale
                }
            }
        }
    }
}
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
    private WPI_TalonSRX fauh = new WPI_TalonSRX(4);
    private WPI_TalonSRX tree = new WPI_TalonSRX(3);
    private WPI_TalonSRX too = new WPI_TalonSRX(2);
    private WPI_TalonSRX juan = new WPI_TalonSRX(1);
    private SpeedControllerGroup gogo = new SpeedControllerGroup(juan,too);
    private SpeedControllerGroup gogo2 = new SpeedControllerGroup(tree,fauh);
    private XboxController xbax;
    private double dootdoot = 2;
    int time = 750;

    @Override
    public void robotInit()
    {
        m_myRobot = new DifferentialDrive(gogo,gogo2);
        xbax = new XboxController(0);
    }

    @Override
    public void teleopPeriodic()
    {
        m_myRobot.arcadeDrive((0-1)*xbax.getX(GenericHID.Hand.kLeft),(0-1)*(xbax.getTriggerAxis(GenericHID.Hand.kRight)-(xbax.getTriggerAxis(GenericHID.Hand.kLeft))));
    }

    @Override
    public void autonomousPeriodic()
    {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0)
        {
            if(gameData.charAt(0) == 'L')
            {
                if(time > 700)
                {
                    m_myRobot.arcadeDrive(.005, -90);
                    time--;
                }
                else
                {
                    if(time > 700)
                    {
                        m_myRobot.arcadeDrive(.005, -90);
                        time--;
                    }
                }
            }
        }
    }
}
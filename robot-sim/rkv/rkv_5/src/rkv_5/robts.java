/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv_5;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaclient3.FiducialInterface;
import javaclient3.GripperInterface;
import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.fiducial.PlayerFiducialItem;

/**
 *
 * @author mihail
 */
public class robts implements Runnable {

    private PlayerClient client;
    Position2DInterface pos;
    RangerInterface ranger;
    FiducialInterface fiducial;
    GripperInterface gripper;
    private int backSteps;
    private Random rnd;
    
    
     public robts(String host, Integer port) {
        client = new PlayerClient(host, port);
        pos = client.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
        ranger = client.requestInterfaceRanger(0, PlayerConstants.PLAYER_OPEN_MODE);
        fiducial = client.requestInterfaceFiducial(0, PlayerConstants.PLAYER_OPEN_MODE);
        gripper = client.requestInterfaceGripper(0, PlayerConstants.PLAYER_OPEN_MODE);

        client.runThreaded(-1, -1);
         
         
     }
    
    public void run() {
         Double _punktsX = Double.MAX_VALUE;
         Double _punktsY = Double.MAX_VALUE;
         Double _speed = Double.MAX_VALUE;
         Double _angle = Double.MAX_VALUE;
         Boolean _shit = false;
         Boolean _detectNewShit = false;
         Boolean _randomDrive = false;
         Integer _shitNumber = Integer.MAX_VALUE;
         
         rnd = new Random();
         Double wanderDir;
         wanderDir = rnd.nextDouble() * 2 - 1;
         
         String _console = "";
         _punktsX = 0.0;
         _punktsY = 0.0;
        _speed = 0.3;
        _angle = 0.0;
        _shitNumber = 11;
        
        while (true) {
            try {
                
                 if(fiducial.isDataReady()){
                     _console+="Data Redy|Shit("+_shitNumber+")|";
                     PlayerFiducialItem[] _fiducialsDetect = fiducial.getData().getFiducials();
                    // if( _fiducialsDetect.length > 0){
                        for (int i = 0; i < _fiducialsDetect.length; i++) {
                            if(_fiducialsDetect[i].getId()==_shitNumber){
                            _punktsX = _fiducialsDetect[i].getPose().getPx();
                           _punktsY = _fiducialsDetect[i].getPose().getPy();
                           _angle = Math.tan(_punktsY/_punktsX);
                           _console+="X:" + _punktsX.toString()+ " Y:"+ _punktsY.toString();
                           _detectNewShit = true;
                            i=_fiducialsDetect.length;
                            
                            }
                        }
                   
                       
                     //}//_fiducialsDetect.length>0
                     
                     
                 }//fiducial.isDataReady()
                 
                 
                 
                 
                 if(gripper.isDataReady()){
                     
                     //kluciisha satvershana
                     if(gripper.getData().getBeams()>0 && !_shit){
                        _speed = 0.0;
                            _shit = true;
                            
                         gripper.setGripper(2);
                         Thread.sleep(500);
                         pos.setSpeed(_speed , _angle);
                         _detectNewShit = false;
                          _shitNumber = 30;
                             Thread.sleep(1000);
                              pos.setSpeed(-0.5 , _angle);
                               Thread.sleep(5000);
                               pos.setSpeed(1 , -1);
                               Thread.sleep(2000);
                               pos.setSpeed(1 , 0);
                               Thread.sleep(8000);
                     }else{
                        _speed = 0.3;
                     }
                 }//gripper.isDataReady()
                 
                 if(_detectNewShit && _shitNumber==30)
                 {
                     if(_punktsY < 1.5 && _punktsX<1.5)
                     {
                        _shit = false;
                        pos.setSpeed(0.0 , 0.0);
                        gripper.setGripper(1);
                        
                         Thread.sleep(1000);
                         pos.setSpeed(-1 , 0.0);
                          Thread.sleep(5000);
                          pos.setSpeed(1 , 1);
                          Thread.sleep(1000);
                          _shitNumber = 21;
                          _randomDrive = true;
                     }
                    
                 }
                 
                 
                if(_shit && !_detectNewShit || _randomDrive){
                    if (ranger.isDataReady()) {
                        if (backSteps == 0) {
                            Double minLeft = Double.MAX_VALUE;
                            Double minCenter = Double.MAX_VALUE;
                            Double minRight = Double.MAX_VALUE;
                            double[] ranges = ranger.getData().getRanges();
                            int rcnt = ranges.length / 3;
                            for (int i = 0; i < ranges.length; i++) {
                                Double r = ranges[i];
                                if (i <= rcnt && minRight > r) {
                                    minRight = r;
                                } else if (i > rcnt && i < rcnt * 2 && minCenter > r) {
                                    minCenter = r;
                                } else if (i >= rcnt * 2 && minLeft > r) {
                                    minLeft = r;
                                }
                            }
                            if (minCenter < 1) {
                                pos.setSpeed(-0.5, rnd.nextDouble() * 2 - 1);
                                backSteps = 10;
                            } else if (minLeft < 2) {
                                pos.setSpeed(0.7, -1);
                            } else if (minRight < 2) {
                                pos.setSpeed(0.7, 1);
                            } else {
                                wanderDir += rnd.nextDouble() * 0.5 - 0.25;
                                if (wanderDir > 1.0) {
                                    wanderDir = 1.0;
                                } else if (wanderDir < -1.0) {
                                    wanderDir = -1.0;
                                }
                                pos.setSpeed(1.0, wanderDir);
                            }
                        } else {
                            backSteps--;
                        }
                        _console += "\tRandom driving";
                    }//ranger.isDataReady()
                }else{
                         pos.setSpeed(_speed , _angle);
                    }
                
                 
                 
                
                 
                if(_console != "")
                System.out.println(_console);//izdruka konsoli 
                
                _console = "";
                 Thread.yield();
                Thread.sleep(50);
                
            } catch (InterruptedException ex) {
                 System.out.println("Error in loop");//izdruka konsoli 
                Logger.getLogger(robts.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//while loop
        
        
        
        
        
        
    }
    
}

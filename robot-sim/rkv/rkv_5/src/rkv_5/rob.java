/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rkv_5;


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.FiducialInterface;
//import javaclient3.FiducialInterface
import javaclient3.GripperInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.fiducial.PlayerFiducialData;
import javaclient3.structures.fiducial.PlayerFiducialGeom;
import javaclient3.structures.fiducial.PlayerFiducialItem;
/**
 *
 * @author mihail
 */

public class rob implements Runnable {
private PlayerClient client;
    Position2DInterface pos;
    RangerInterface ranger;
    FiducialInterface fiducial;
    GripperInterface gripper;


    Byte backSteps;
    Double wanderDir;
  Random rnd;
    public int atverejs;



    vadiba _vadibasForma;
    //NewJFrame _frame;
   /* public rob() {
        
         

rob_run("localhost", 6665);
 
        //PlayerClient client = new PlayerClient("localhost", 6665);

      
      
        
    //}
    *
    */
     public rob(String host, Integer port) {

        client = new PlayerClient(host, port);

        pos = client.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
        ranger = client.requestInterfaceRanger(0, PlayerConstants.PLAYER_OPEN_MODE);
        fiducial = client.requestInterfaceFiducial(0, PlayerConstants.PLAYER_OPEN_MODE);
        gripper = client.requestInterfaceGripper(0, PlayerConstants.PLAYER_OPEN_MODE);

        client.runThreaded(-1, -1);

        








    }

    public void run() {
        rnd = new Random();
       Byte _backSteps = 0;
        Double _wanderDir = rnd.nextDouble() * 2 - 1;
_vadibasForma = new vadiba();
_vadibasForma.setVisible(true);

boolean _satver = false;
   Double _punktsX = Double.MAX_VALUE;
                Double _punktsY = Double.MAX_VALUE;
                Integer _label = Integer.MAX_VALUE;
                _punktsX = 0.0;
                _punktsY = 0.0;
                 Double _speed = Double.MAX_VALUE;
                 _label = 11;
        while (true) {
        

           

                Double minLeft = Double.MAX_VALUE;
                Double minCenter = Double.MAX_VALUE;
                Double minRight = Double.MAX_VALUE;
                
               
                
             
                
                gripper.setGripper(_vadibasForma.zoklis);//2 aizvērt sensoru 1 atvērt sensoru
                //if(!_satver)
                if(fiducial.isDataReady()){
                //double[] ranges = ranger.getData().getRanges();
                //gripper.setGripper(atverejs);//2 aizvērt sensoru 1 atvērt sensoru
                 
                PlayerFiducialItem[] _fiducialsDetect = fiducial.getData().getFiducials();
                //2 aizvērt sensoru 1 atvērt sensoru
                 
                     //PlayerFiducialData _fidutialData = fiducial.getData();
                    
                     if(_vadibasForma.lasit){
                         if(_fiducialsDetect.length>=1)
                     _vadibasForma.setVal(_fiducialsDetect[0].getPose().getPx(), _fiducialsDetect[0].getPose().getPy(),_fiducialsDetect[0].getId());
                     _vadibasForma.lasit = false;

                     
                    }
/*
 for (int i = 0; i < _fiducialsDetect.length; i++) {
 if(_fiducialsDetect[i].getId()==_label){
 _punktsX = _fiducialsDetect[i].getPose().getPx();
  _punktsY = _fiducialsDetect[i].getPose().getPy();
i=_fiducialsDetect.length;
     }
                    }
                      * 
                      */
                     if(_fiducialsDetect.length>=1){
                          _punktsX = _fiducialsDetect[0].getPose().getPx();
                          _punktsY = _fiducialsDetect[0].getPose().getPy();
                     }
 
               //  _fiducialsDetect[0].getPose().getPx();
               // _fiducialsDetect[0].getPose().getPy();
                }//end fiducial data ready
               
                 //pos.setSpeed( _vadibasForma.atrums, _vadibasForma.lenkis);
     //pirmaa punkta notvershana
             
  //  if(_punktsX < 0.6 && _punktsY <0.6){
 if(gripper.isDataReady()){
        if(gripper.getData().getBeams()>0){
             pos.setSpeed(0.0 , 0.0);
             _label = 30;
             _satver = true;
         }else{
              _speed=_vadibasForma.atrums;
         }
 }
     if(!_satver)
 pos.setSpeed(_speed , Math.tan(_punktsY/_punktsX));



      if(_satver)   {
          gripper.setGripper(2);
      }       else{
      gripper.setGripper(1);
            }


//
//        if(gripper.isDataReady())
//             if(!_satver && gripper.getData().getState() == 1) {
//
//                 pos.setSpeed(-0.5 , Math.tan(_punktsY/_punktsX));
//
//
//
//                // pos.setSpeed(0.0 , Math.tan(_punktsY/_punktsX));
//             }




/*
                 if(_label == 30){
                      try {

                if (ranger.isDataReady()) {
                    if (_backSteps == 0) {
                        Double _minLeft = Double.MAX_VALUE;
                        Double _minCenter = Double.MAX_VALUE;
                        Double _minRight = Double.MAX_VALUE;
                        double[] ranges = ranger.getData().getRanges();
                        int rcnt = ranges.length / 3;
                        for (int i = 0; i < ranges.length; i++) {
                            Double r = ranges[i];
                            if (i <= rcnt && _minRight > r) {
                                _minRight = r;
                            } else if (i > rcnt && i < rcnt * 2 && _minCenter > r) {
                                _minCenter = r;
                            } else if (i >= rcnt * 2 && minLeft > r) {
                                _minLeft = r;
                            }
                        }
                        if (_minCenter < 1) {
                            pos.setSpeed(-0.5, rnd.nextDouble() * 2 - 1);
                            _backSteps = 10;
                        } else if (_minLeft < 2) {
                            pos.setSpeed(0.7, -1);
                        } else if (_minRight < 2) {
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
                        _backSteps--;
                    }

                    Thread.yield();
                    Thread.sleep(50);
                
            }
        } catch (InterruptedException ex) {

        }
              

            }
      * 
 */       
            }


                  
    }
}

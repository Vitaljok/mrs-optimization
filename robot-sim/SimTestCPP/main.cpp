/* 
 * File:   main.cpp
 * Author: robot
 *
 * Created on June 8, 2012, 2:09 PM
 */

#include <cstdlib>
#include <iostream>
#include "libplayerc++/playerc++.h"

/*
 * 
 */
int main(int argc, char** argv) {

    using namespace PlayerCc;

    std::cout << "Start" << std::endl;

    PlayerClient client("localhost", 6650);
    SimulationProxy sim(&client, 0);      

    double x, y, a;

    for (;;) {
        sim.GetPose2d("o1", x, y, a);
        std::cout << "X=" << x << "\tY=" << y << "\tA=" << a << std::endl;
        
        //sim.SetPose2d("obj2", -3, -2, 0);
        
        sleep(1);
    }
    
    
    
    std::cout << "End" << std::endl;

    return 0;
}


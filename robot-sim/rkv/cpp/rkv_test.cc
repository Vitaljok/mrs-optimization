
#include <iostream>
#include <libplayerc++/playerc++.h>

int
main(int argc, char *argv[])
{
  using namespace PlayerCc;

  PlayerClient    robot("localhost");
  Position2dProxy pp(&robot,0);

  for(;;)
  {
    double turnrate, speed;

    // read from the proxies
    robot.Read();

    // command the motors
    pp.SetSpeed(0.5, 0.2);
  }
}


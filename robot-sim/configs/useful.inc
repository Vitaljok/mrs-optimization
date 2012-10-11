# #charger
# model ( 
  # pose [ 2 0 0 0 ]
  # color "yellow"
  # size [ 1  1 1 ]
  # joules -1  # provides infinite energy
  # give_watts 2000 
  # fiducial_return 2 # look for this in the fiducial senso
 
  # alwayson 1 # so we give charge without any explicit subscriber
 # )
 
   # # charging contact
  # model
  # (
    # pose [ 0.65 0 0.2 0 ] 
    # size [1 0.050 0.100] 
    # take_watts 2000.0 
    # color "orange" 
    # obstacle_return 0
  # )s
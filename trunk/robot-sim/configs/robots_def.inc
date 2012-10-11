#========================
# robots
#========================

define wheel model (
  size [ 0.2 0.2 0.3]
  color_rgba [ 0.4 0.1 0.0 1]
)

define unloader  model (
  color "dark green"
  
  block (
    points 6
    point[0] [ 0.0  0.0 ]
    point[1] [ 3.0  0.0 ]
    point[2] [ 3.0  1.0 ]
    point[3] [ 2.0  2.0 ]
    point[4] [ 1.0  2.0 ]
    point[5] [ 0.0  1.0 ]
    z [0 1]
  )
)

define rng_sensor sensor (
  fov 45
  range [ 0 2 ]
)

define robot_base position (
  
  # watts 5000
  # kjoules 100 
  # kjoules_capacity 400
  size [ 1.3 1.3 0.10]
  color_rgba [ 0.8 0.3 0.0 0.2]
  wheel ( pose [ -0.65 -0.65 -0.1 45 ] )
  wheel ( pose [ -0.65  0.65 -0.1 45 ] )
  wheel ( pose [  0.65  0.65 -0.1 45 ] )
  wheel ( pose [  0.65 -0.65 -0.1 45 ] )
  
  velocity_bounds [ -5.0 10.0 0 0 0 0 -90.0 90.0 ]
  
  # fake body
  model (
    color_rgba [ 0.8 0.3 0.0 1]
    size [ 1.3 1.3 0.5]
  )
  
  # # head
  # actuator (    
    # type "linear"
    # min_position 0 #-1.58
    # max_position 10 #1.58
    # max_speed 1

    # size [ 0.1 0.1 1 ]
    # pose [ 0.5 0 0.5 0 ]
  # )
  
  # fake head  
  model (
    size [ 0.7 0.2 0.1]
    pose [ 0.5 0 0.5 -90 ]
    
    color_rgba [ 0.45 0.2 0.0 1]
    
    block (
      points 3
      point[0] [ 0.0 0.0 ]
      point[1] [ 1.0 0.0 ]
      point[2] [ 0.5 1.0 ]
      z [0 1]
    )   
  ) 
  
  # camera
  blobfinder (
    # alwayson 1
    image [ 160 120 ]
    #image_show 1
    range 12.0
    fov 120
    pose [ 0.5 0 0.5 0 ]
  )    
  
  # ranger (
    # alwayson 1
    # # front (custom)
    # rng_sensor ( pose [ 0.65 0 0.1 0 ] range [ 0 3 ] )
    # #front right 
    # rng_sensor ( pose [ 0.65  -0.65 0.1 -45 ] )
    # #right
    # rng_sensor ( pose [ 0.0   -0.65 0.1 -90 ] )
    # #back right
    # rng_sensor ( pose [ -0.65 -0.65 0.1 -135 ] )
    # #back
    # rng_sensor ( pose [ -0.65 0.0  0.1 180 ] )
    # #back left
    # rng_sensor ( pose [ -0.65 0.65 0.1 135 ] )
    # #left
    # rng_sensor ( pose [ 0.0   0.65 0.1  90 ] )
    # #front left
    # rng_sensor ( pose [ 0.65  0.65 0.1  45 ] )   
  # )
  
  # laser ranger
  ranger (
    alwayson 1
    
    sensor (
      range [ 0 4 ]
      fov 360
      samples 45
    )    
  )
  
  # # navigator
  # fiducial (  
  # )
  
  # cropper
  fiducial (
    range_max 0.8
    fov 360
    pose [ 0 0 0 0]
    ignore_zloc 1
    fiducial_key 10
  )
)

define robot_mower robot_base (
  
    unloader ( 
      size [ 1.1 0.7 0.2 ]
      pose [ -0.4 0 0.5 -90 ] 
    )
    
    unloader ( 
      size [ 0.9 0.5 0.1 ]
      pose [ -0.5 0 0.7 -90 ] 
    )
)
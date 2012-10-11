define robot_base position (
  #gui_nose 1
  #gui_grid 1
  drive "car"
  
  size [ 1.2 0.5 0.5 ]
  
  #base
  block (
    points 5
    point[0] [0.0 0.0]
    point[1] [1.0 0.0]
    point[2] [1.2 0.25]
    point[3] [1.0 0.5]
    point[4] [0.0 0.5]
    z [0 0.1]
  )
  
  #head
  block (
    points 4
    point[0] [0.9 0.0]
    point[1] [1.0 0.0]
    point[2] [1.0 0.5]
    point[3] [0.9 0.5]
    z [0.1 0.5]
  )
  
  laser (
    range_max 5.0
    fov 180.0
    samples 120
    color "blue"
    pose [0.45 0.0 -0.2 0.0]
    
    size [0.1 0.4 0.1]
    
    block (
      points 3
      point[0] [0.0 0.0]
      point[1] [1.0 0.5]
      point[2] [0.0 1.0]
      z [0 1]
    )
  )
  
  actuator (
    type "rotational"
    min_position -1.58
    max_position 1.58
    max_speed 1 
    
    color "green"
    
    size [0.03 0.03 0.01]
    pose [0.35 0.0 0 0.0]
    
    fiducial (
      color "pink"
      range_min 0.0
      range_max 5.0
      range_max_id 3.0
      fov 90
      
      size [0.07 0.1 0.05]
      pose [0.01 0 0 0]
      
      block (
        points 3
        point[0] [0.0 0.0]
        point[1] [1.0 0.5]
        point[2] [0.0 1.0]
        z [0 1]
      )
    )
  )
  
  color "red"
)

define sprayer actuator (
  #turning base

  size [0.1 0.1 1.2]
  color "green"
  
  type "rotational"
  min_position -3.15
  max_position 3.15
  max_speed 1   
  
  #sprayer
  actuator (
    color "yellow"
    size [0.4 0.2 0.1]
    pose [0.1 0.0 -1.2 0.0]
  
    type "linear"
    axis [0.0 0.0 1.0]
    min_position 0
    max_position 1
    max_speed 0.5
    
    block (
      points 6
      point[0] [0.00 0.00]
      point[1] [1.00 0.00]
      point[2] [2.00 0.33]
      point[3] [2.00 0.66]
      point[4] [1.00 1.00]
      point[5] [0.00 1.00]
      z [0 1]
    )
    
  )  
)

define tank_small model (
  color "brown"
  size [0.4 0.4 0.3]
  
  block (
    points 8
    point[0] [ 0.25 -0.50]
    point[1] [ 0.50 -0.25]
    point[2] [ 0.50  0.25]
    point[3] [ 0.25  0.50]
    point[4] [-0.25  0.50]
    point[5] [-0.50  0.25]
    point[6] [-0.50 -0.25]
    point[7] [-0.25 -0.50]
    z [0 1]
  )
)

define tank_static tank_small (
  size [1.0 1.0 0.7]
  fiducial_return 2
)


define tank_filler tank_small (
  actuator (
    size [0.1 0.1 0.1]
    color "green"
    type "rotational"
    min_position -3.14
    max_position 3.14
    max_speed 1  
    
    model (
      color "yellow"
      size [0.6 0.1 0.1]
      pose [0.15 0 0 0]
      
      actuator (
        size [0.5 0.08 0.08]
        pose [0.1 0 -0.09 0]
        color "brown"
        type "linear"
        axis [1 0 0 ]
        min_position 0
        max_position 0.4
        max_speed 0.1 
        
        model (
          size [0.08 0.08 0.12]
          pose [0.29 0 -0.12 0]
          color "brown"
        )
      )      
    )    
  )
)

define robot_sprayer robot_base (  
  tank_small (
    pose [0.05 0 -0.4 0 ]
  )  
  
  sprayer (
    pose [-0.5 0.0 -0.4 0.0 ]
  )
)

define robot_filler robot_base (  
  tank_small (
    pose [0.05 0 -0.4 0 ]
  )
  
  tank_filler (
    pose [-0.35 0 -0.4 0 ]
  )  
)

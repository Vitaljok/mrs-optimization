#========================
# lawn
#========================
define lawn model (
  gui_move 0
  color_rgba [ 0.7 1 0.7 1 ]
)

define lawn_shape_V block (
  points 4
  point[0] [ 0 0 ]
  point[1] [ 1 0 ]
  point[2] [ 0.661 1 ]
  point[3] [ 0.339 1 ]
  z [ 0 1 ]
)

define lawn_shape_H block (
  points 4
  point[0] [ 0 0 ]
  point[1] [ 1 0 ]
  point[2] [ 0.563 1 ]
  point[3] [ 0.437 1 ]
  z [ 0 1 ]
)

define lawn_H lawn (
  size [ 102.929 45 0.01 ] 
  lawn_shape_H ()
)

define lawn_V lawn (
  size [ 132.929 45 0.01 ] 
  lawn_shape_V ()
)

#top
lawn_H (
  pose [ 0 47.5 0 180 ]
)

#bottom
lawn_H (
  pose [ 0 -47.5 0 0 ]
)  

#left
lawn_V (
  pose [ -32.5 0 0 -90 ]
)

#right
lawn_V (
  pose [ 32.5 0 0 90 ]
)

#========================
# boundaries
#========================
define wall model (
  color_rgba [ 0.2 0.3 0.2 1] 
  gui_move 0
)

#top
wall (
  size [ 124.000 2.000 2.000 ]
  pose [ 0.000 76.000 0.000 0.000]
)

#left
wall (
  size [ 2.000 120.000 2.000 ]
  pose [ -61.000 5.000 0.000 0.000 ]
)

#right
wall (
  size [ 2.000 120.000 2.000 ]
  pose [ 61.000 5.000 0.000 0.000 ]
)

#bottom
wall (
  size [ 80.000 2.000 2.000 ]
  pose [ 0.000 -76.000 0.000 0.000 ]
)
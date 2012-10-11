#========================
# objects
#========================
define tree model (
  gui_move 0
  color "brown"
  size [ 1.000 1.000 5.000 ]
  
  model (
    color "forest green"
    size [ 3.000 3.000 3.000 ]
  )
)

define brush model (
  gui_move 0
  color "dark green"
  size [ 1.500 1.500 1.500 ]
)

define flower model (
  size [ 0.500 0.500 0.500 ]
)

define flower_red flower (
  color "red"
)

define flower_blue flower (
  color "blue"
)

define flowerbed model(
  gui_move 0
  color "dark olive green"
  size [ 4.000 4.000 0.300 ]
  
  flower_red ( pose [ -1.500  0.500 0.000 23.000 ] )
  flower_red ( pose [  0.500  1.500 0.000 74.000 ] )
  flower_red ( pose [  1.500 -0.500 0.000 12.000 ] )
  flower_red ( pose [ -0.500 -1.500 0.000 62.000 ] )  
  
  flower_blue ( pose [ -0.500  0.500 0.000 23.000 ] )
  flower_blue ( pose [  0.500  0.500 0.000 74.000 ] )
  flower_blue ( pose [  0.500 -0.500 0.000 12.000 ] )
  flower_blue ( pose [ -0.500 -0.500 0.000 62.000 ] )  
)
#!/bin/bash
. install_common.inc

STEP="[Step 4]"

log "Installing Stage dependecies"

smart_install freeglut3-dev
smart_install libfltk1.3-dev
smart_install libpng12-dev
smart_install libglu1-mesa-dev
smart_install mesa-utils
smart_install libltdl-dev

if [ ! -d $SRC_DIR ]; then
	log "Creating source directory"
	mkdir $SRC_DIR
fi
cd $SRC_DIR

#loading new settings
log "Reloading configuration"
. $INSTALL_DIR/$CONFIG_FILE

log "Checking out Stage sources"
svn co https://mrs-optimization.googlecode.com/svn/trunk/robot-sim/stage $SG_SRC

if [ ! -d $SG_BUILD ]; then
	log "Creating build directory"
	mkdir $SG_BUILD
fi

log "Configuring Stage"
cd $SG_BUILD
cmake \
	-DCMAKE_INSTALL_PREFIX=$INSTALL_DIR \
	$SG_SRC
	
log "$STEP Making and installing Stage"
make install

log "Done!"

#!/bin/bash
. install_common.inc

#installing dependencies
STEP="[Step 1]"
log "Installing Player dependecies..."

smart_install subversion
smart_install cmake
smart_install build-essential
smart_install cpp
smart_install libboost-thread-dev
smart_install libboost-signals-dev
smart_install libcv-dev
smart_install libcvaux-dev
smart_install libgdk-pixbuf2.0-dev
smart_install libjpeg8-dev
smart_install libxmu-dev
smart_install libgnomecanvas2-dev
smart_install libgsl0-dev
smart_install libtool
smart_install swig
smart_install libhighgui-dev

# Extracting sources
STEP="[Step 2]"

if [ ! -d $SRC_DIR ]; then
	log "Creating source directory"
	mkdir $SRC_DIR
fi

cd $SRC_DIR
log "Checking out Player sources"
svn co http://svn.code.sf.net/p/playerstage/svn/code/player/trunk/ $PL_SRC

if [ ! -d $PL_BUILD ]; then
	log "Creating build directory"
	mkdir $PL_BUILD
fi

log "Configuring Player"
cd $PL_BUILD
cmake \
	-DCMAKE_INSTALL_PREFIX=$INSTALL_DIR \
	-DBUILD_PLAYERCC=ON \
	-DBUILD_PYTHONC_BINDINGS=OFF \
	$PL_SRC
	
log "$STEP Making and installing Player"
make install

#configuring environment
STEP="[Step 3]"
log "Configuring environment"

if [ -f $INSTALL_DIR/$CONFIG_FILE ]; then
	rm -f $INSTALL_DIR/$CONFIG_FILE
fi

cat << EOF >> $INSTALL_DIR/$CONFIG_FILE
export PATH="$PATH:$INSTALL_DIR/bin"
export LD_LIBRARY_PATH=$INSTALL_DIR/lib
export PKG_CONFIG_PATH="$INSTALL_DIR/lib/pkgconfig/:$PKG_CONFIG_PATH"
EOF

if [ -z "`grep \"PLAYER/STAGE\" ~/.bashrc`" ]; then
cat << EOF >> ~/.bashrc 

# add PLAYER/STAGE config
. $INSTALL_DIR/$CONFIG_FILE
EOF
fi

log "Done!"
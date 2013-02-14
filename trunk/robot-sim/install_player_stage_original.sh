#!/bin/bash

function log() {
	flags="-e"
	if [ "$1" = "-n" ]; then
		flags="$flags -n"
		shift
	fi
	
	echo $flags "\e[1;31m$STEP \e[1;34m$1\e[0m"
}

function loga() {
	flags="-e"
	echo $flags "\e[1;34m$1\e[0m"
}

function smart_install()
{
	log -n "Checking for package: $1... "
	version=`dpkg-query -f'${Version}' -W $1 2>/dev/null`;
	
	if [ -z "$version" ]; then
		loga "Not found"
		log "Installing dependency package: $1"
		sudo apt-get -y install $1
	else
		loga "Found version $version"
	fi
}

SRC_DIR=~/source
PL_SRC=$SRC_DIR/player
PL_BUILD=$SRC_DIR/player_build
SG_SRC=$SRC_DIR/stage
SG_BUILD=$SRC_DIR/stage_build
INSTALL_DIR=~/playerstage
CONFIG_FILE=.player_env

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
smart_install libjpeg62-dev
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
svn co http://playerstage.svn.sourceforge.net/svnroot/playerstage/code/player/trunk/ $PL_SRC

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

#loading new settings
log "Reloading configuration"
. $INSTALL_DIR/$CONFIG_FILE

STEP="[Step 4]"

log "Installing Stage dependecies"

smart_install git
smart_install freeglut3-dev
smart_install libfltk1.1-dev
#smart_install libjpeg8-dev
smart_install libpng12-dev
smart_install libglu1-mesa-dev
smart_install libltdl-dev

cd $SRC_DIR
log "Checking out Stage sources"
if [ ! -d $SG_SRC ]; then
	git clone git://github.com/rtv/Stage.git $SG_SRC
else
	cd $SG_SRC
	git pull git://github.com/rtv/Stage.git
fi

#use older source
cd $SG_SRC
git checkout 13e03ee0068d6dfc7384cff9ee1ff22272729985

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

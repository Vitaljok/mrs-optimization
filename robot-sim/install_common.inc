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

/*
 *  Player - One Hell of a Robot Server
 *  Copyright (C) 2003
 *     Andrew Howard
 *     Brian Gerkey    
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301 USA
 *
 */


/**************************************************************************
 * Desc: KD tree functions
 * Author: Andrew Howard
 * Date: 18 Dec 2002
 * CVS: $Id: pf_kdtree.h 9120 2013-01-07 00:18:52Z jpgr87 $
 *************************************************************************/

#ifndef PF_KDTREE_H
#define PF_KDTREE_H

#include <playerconfig.h>

#ifdef INCLUDE_RTKGUI
#include "rtk.h"
#endif


// Info for a node in the tree
typedef struct pf_kdtree_node
{
  // Depth in the tree
  int leaf, depth;

  // Pivot dimension and value
  int pivot_dim;
  double pivot_value;

  // The key for this node
  int key[3];

  // The value for this node
  double value;

  // The cluster label (leaf nodes)
  int cluster;

  // Child nodes
  struct pf_kdtree_node *children[2];

} pf_kdtree_node_t;


// A kd tree
typedef struct
{
  // Cell size
  double size[3];

  // The root node of the tree
  pf_kdtree_node_t *root;

  // The number of nodes in the tree
  int node_count, node_max_count;
  pf_kdtree_node_t *nodes;

  // The number of leaf nodes in the tree
  int leaf_count;

} pf_kdtree_t;


// Create a tree
extern pf_kdtree_t *pf_kdtree_alloc(int max_size);

// Destroy a tree
extern void pf_kdtree_free(pf_kdtree_t *self);

// Clear all entries from the tree
extern void pf_kdtree_clear(pf_kdtree_t *self);

// Insert a pose into the tree
extern void pf_kdtree_insert(pf_kdtree_t *self, pf_vector_t pose, double value);

// Cluster the leaves in the tree
extern void pf_kdtree_cluster(pf_kdtree_t *self);

// Determine the probability estimate for the given pose
extern double pf_kdtree_get_prob(pf_kdtree_t *self, pf_vector_t pose);

// Determine the cluster label for the given pose
extern int pf_kdtree_get_cluster(pf_kdtree_t *self, pf_vector_t pose);


#ifdef INCLUDE_RTKGUI

// Draw the tree
extern void pf_kdtree_draw(pf_kdtree_t *self, rtk_fig_t *fig);

#endif

#endif
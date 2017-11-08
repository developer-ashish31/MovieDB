/*
 * Copyright (c) 2014 Jangobanana, Inc.
 * All rights reserved. Use is subject to license terms.
 */

package com.intigral.moviedb._init;

import android.view.View;


/**
 *
 * @version 1.0 27-Nov-2014 
 * @author Ajay Maurya
 *
 */

public interface DrawableClickListener {
	public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
	public void onClick(DrawablePosition target); 
	public void onClick(DrawablePosition target, View view);
}

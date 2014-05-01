package com.flaxtreme.gominsktestapp.interfaces;

import android.support.v4.app.Fragment;

/**
 * Describe behaviour which returns appropriate sub fragment of CategoryFragment for SectionAdapter
 * @author Alexander
 *
 */
public interface IFragmentsGetter {
	public Fragment getSubFragment(int pos);

	public int getSubFragmentCount();
}

/**
 * Copyright � 2018 Lhasa Limited
 * File created: 27 Apr 2018 by ThomasB
 * Creator : ThomasB
 * Version : $Id$
 */
package xawd.jflow.iterables;

import xawd.jflow.AbstractFlow;

/**
 * @author ThomasB
 * @since 27 Apr 2018
 */
public abstract class AbstractFlowIterable<T> implements FlowIterable<T>
{
	@Override
	public abstract AbstractFlow<T> iterator();
}

/* ---------------------------------------------------------------------*
 * This software is the confidential and proprietary
 * information of Lhasa Limited
 * Granary Wharf House, 2 Canal Wharf, Leeds, LS11 5PS
 * ---
 * No part of this confidential information shall be disclosed
 * and it shall be used only in accordance with the terms of a
 * written license agreement entered into by holder of the information
 * with LHASA Ltd.
 * --------------------------------------------------------------------- */
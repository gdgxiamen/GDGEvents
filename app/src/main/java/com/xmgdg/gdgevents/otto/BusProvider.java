package com.xmgdg.gdgevents.otto;

import com.squareup.otto.Bus;

/**
 * Created by qixingchen on 15/10/13.
 * 总线提供
 */
public class BusProvider {
    private static final Bus BUS = new Bus();

    private BusProvider() {
        // No instances.
    }

    public static Bus getInstance() {
        return BUS;
    }
}

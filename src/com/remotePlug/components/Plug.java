package com.remotePlug.components;

/**
 * Plug defines the blue-print for external and internal plugs that could be added to the system.
 * A plug would be a part of the application once it's added.
 * However, it can removed by simply excluding it from the class path.
 *
 * In a way, it provides an easy solution to expand the application.
 * These plugs make use of the Resources and do stuff with it, like Playing a video or open a document and so on
 */

public interface Plug {
    public boolean load();
    public boolean unload();
}

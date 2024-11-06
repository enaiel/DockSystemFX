module com.dansoftware.dock {

    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;

    requires com.goxr3plus.fxborderlessscene;

    exports com.dansoftware.dock.border;
    exports com.dansoftware.dock.docknode;
    exports com.dansoftware.dock.docksystem;
    exports com.dansoftware.dock.position;
    exports com.dansoftware.dock.util;
    exports com.dansoftware.dock.viewmode;
    exports com.dansoftware.dock.viewmode.event;

    opens com.dansoftware.dock;
    opens com.dansoftware.dock.image;
    opens com.dansoftware.dock.image.pos;
    opens com.dansoftware.dock.image.view_mode;
}
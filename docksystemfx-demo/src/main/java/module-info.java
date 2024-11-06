module com.dansoftware.dock.demo {

    requires com.dansoftware.dock;

    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires transitive javafx.web;

    requires com.goxr3plus.fxborderlessscene;

    requires org.jfxtras.styles.jmetro;

    exports com.dansoftware.dock.demo;

    opens com.dansoftware.dock.demo.icons;
}
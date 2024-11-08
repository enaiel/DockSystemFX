package com.dansoftware.dock.docksystem;

import com.dansoftware.dock.border.DockFrame;
import com.dansoftware.dock.docknode.DockNode;
import com.dansoftware.dock.position.DockPosition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;


import java.util.Objects;
import java.util.ResourceBundle;

public class DockSystem<C extends Node> extends StackPane {

    private DockPosition defaultDockPosition = DockPosition.LEFT_BOTTOM;

    private final ObservableList<DockNode> dockNodes = FXCollections.observableArrayList();
    private final ObjectProperty<C> center = new SimpleObjectProperty<>(this, "centerPane");
    private final DockFrame frame;
    private final SplitPaneSystem splitPaneSystem;
    private ResourceBundle resourceBundle;

    public DockSystem() {
        this.splitPaneSystem = new SplitPaneSystem();
        this.frame = new DockFrame();
        this.frame.setCenter(splitPaneSystem);

        this.getChildren().add(this.frame);

        this.center.addListener((observable, oldCenter, newCenter) ->
                splitPaneSystem.setCenterNode(newCenter));
    }

    public DockSystem(C center) {
        this();
        this.setDockedCenter(center);
    }

    public void hide(DockPosition pos, DockNode dockNode) {
        Objects.requireNonNull(pos, "The pos mustn't be null");

        if (this.getDockNodes().contains(dockNode)) {
            Parent parent = dockNode.getParent();
            if (parent != null) {
                SplitPane splitPane = (SplitPane) parent.getParent();
                if (splitPane != null)
                    splitPane.getItems().remove(dockNode);
            }
        }
    }

    public void dock(DockPosition pos, DockNode dockNode, boolean show) {
        if (pos == null) {
            dockNode.setDockPosition(defaultDockPosition);
            return;
        }

        this.frame.allocate(pos, dockNode.getBorderButton());

        dockNode.setDockSystem(this);
        dockNode.setDockPosition(pos);

        if (show) {
            pos.getPosStrategy().posDockNode(this.splitPaneSystem, dockNode);

            if (!this.dockNodes.contains(dockNode))
                this.dockNodes.add(dockNode);
        }
    }

    public void dock(DockPosition from, DockPosition to, DockNode dockNode, boolean show) {
        if (from != null) this.frame.deAllocate(from, dockNode.getBorderButton());
        this.dock(to, dockNode, show);
    }

    public ObservableList<DockNode> getDockNodes() {
        return new ReadOnlyListWrapper<>(dockNodes);
    }

    public Node getDockedCenter() {
        return center.get();
    }

    public ObjectProperty<C> dockedCenterProperty() {
        return center;
    }

    public void setDockedCenter(C center) {
        this.center.set(center);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public DockPosition getDefaultDockPosition() {
        return defaultDockPosition;
    }

    public void setDefaultDockPosition(DockPosition defaultDockPosition) {
        this.defaultDockPosition = defaultDockPosition;
    }
}

package com.tilldawn.View.Components;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class DragDropWindow extends Window {

    public interface DropListener {
        void onFileDropped(String path);
    }

    private DropListener listener;

    public DragDropWindow(Skin skin, DropListener listener) {
        super("Drop Image", skin);
        this.listener = listener;

        Label label = new Label("Drop an image here", skin);
        add(label).pad(20);
        pack();
        setMovable(false);
        setVisible(false);
        center();
    }

    public void show(Stage stage) {
        if (getParent() == null) stage.addActor(this);
        setVisible(true);
        setZIndex(Integer.MAX_VALUE); // on top
    }

    public void hideWindow() {
        setVisible(false);
    }

    public void handleDrop(String filePath) {
        if (listener != null) {
            listener.onFileDropped(filePath);
        }
        hideWindow();
    }
}

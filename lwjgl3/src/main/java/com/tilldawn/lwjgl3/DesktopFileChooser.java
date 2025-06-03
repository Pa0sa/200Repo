package com.tilldawn.lwjgl3;

import com.tilldawn.View.Components.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class DesktopFileChooser implements FileChooser {
    private boolean fileChooserOpen = false;

    @Override
    public void chooseFile(FileChosenCallback callback) {
        if (fileChooserOpen) return; // prevent re-opening
        fileChooserOpen = true;

        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg"));
            int result = fileChooser.showOpenDialog(null);  // <-- this creates a modal dialog

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                callback.onFileChosen(file.getAbsolutePath());
            }
            fileChooserOpen = false;
        });
    }
}

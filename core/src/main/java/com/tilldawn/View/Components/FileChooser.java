package com.tilldawn.View.Components;


public interface FileChooser {
    void chooseFile(FileChosenCallback callback);

    interface FileChosenCallback {
        void onFileChosen(String filePath);
    }
}


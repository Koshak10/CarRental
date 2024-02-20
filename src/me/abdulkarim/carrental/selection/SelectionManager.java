package me.abdulkarim.carrental.selection;

public class SelectionManager {

    private SelectionManager() { }

    private static final SelectionManager INSTANCE = new SelectionManager();

    public static SelectionManager getInstance() {
        return INSTANCE;
    }

    private Selection selection;

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

}
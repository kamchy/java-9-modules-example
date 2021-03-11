package com.kamilachyla.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

final class GuiHelper {
    private GuiHelper(){
        // helper
    }

    public static final Border createBorder() {
        return createMarkerBorder();
    }

    private static CompoundBorder createMarkerBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                createSpaceBorder());
    }

    private static Border createSpaceBorder() {
        return BorderFactory.createEmptyBorder(5, 5, 0, 5);
    }
}

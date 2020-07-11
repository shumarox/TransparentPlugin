import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.peer.WindowPeer;
import java.lang.reflect.Field;

public class TransparentAction extends AnAction {

    private boolean transparent = false;

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            JFrame frame = (JFrame) ((JWindow) e.getInputEvent().getSource()).getParent();
            Field field = Component.class.getDeclaredField("peer");
            field.setAccessible(true);
            WindowPeer peer = (WindowPeer) field.get(frame);

            transparent = !transparent;
            float opacity = transparent ? 0.8f : 1f;
            peer.setOpacity(opacity);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

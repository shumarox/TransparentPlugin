import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.peer.WindowPeer;
import java.lang.reflect.Field;

public class TransparentAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            JFrame frame = (JFrame) ((JWindow) e.getInputEvent().getSource()).getParent();
            Field field = Component.class.getDeclaredField("peer");
            field.setAccessible(true);
            WindowPeer peer = (WindowPeer) field.get(frame);
            peer.setOpacity(0.80f);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

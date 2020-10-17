import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.peer.WindowPeer;
import java.lang.reflect.Field;

public class TransparentAction extends AnAction {

    private float opacity = 1.0f;

    private boolean mouseWheelListenerAdded = false;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            JFrame frame = (JFrame) ((JWindow) e.getInputEvent().getSource()).getParent();
            Field field = Component.class.getDeclaredField("peer");
            field.setAccessible(true);
            WindowPeer peer = (WindowPeer) field.get(frame);

            opacity = opacity == 1.0f ? 0.8f : 1.0f;
            peer.setOpacity(opacity);

            if (!mouseWheelListenerAdded) {
                addMouseWheelListener(frame, peer);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void addMouseWheelListener(JFrame frame, WindowPeer peer) {
        JMenuBar menuBar = frame.getJMenuBar();

        menuBar.addMouseWheelListener(mouseWheelEvent -> {
            opacity = Math.max(Math.min(opacity - mouseWheelEvent.getWheelRotation() / 20f, 1.0f), 0.3f);
            peer.setOpacity(opacity);
        });

        mouseWheelListenerAdded = true;
    }
}

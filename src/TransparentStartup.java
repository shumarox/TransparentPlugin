import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.wm.impl.IdeFrameImpl;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.peer.WindowPeer;
import java.lang.reflect.Field;

public class TransparentStartup implements StartupActivity {

    private float opacity = 1.0f;

    @Override
    public void runActivity(@NotNull final Project project) {
        try {
            JFrame frame = (JFrame) IdeFrameImpl.getActiveFrame();
            Field field = Component.class.getDeclaredField("peer");
            field.setAccessible(true);
            WindowPeer peer = (WindowPeer) field.get(frame);

            frame.getJMenuBar().addMouseWheelListener(mouseWheelEvent -> {
                // ToolsMenu -> Startup
                SwingUtilities.invokeLater(() -> {
                    if (!mouseWheelEvent.isConsumed()) {
                        opacity = Math.max(Math.min(opacity - mouseWheelEvent.getWheelRotation() / 20f, 1.0f), 0.3f);
                        peer.setOpacity(opacity);
                        mouseWheelEvent.consume();
                    }
                });
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

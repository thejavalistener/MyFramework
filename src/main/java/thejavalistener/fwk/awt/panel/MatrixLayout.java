package thejavalistener.fwk.awt.panel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class MatrixLayout implements LayoutManager {
    public static final int CENTER_ALIGN = 0;
    public static final int LEFT_ALIGN = -1;
    public static final int RIGHT_ALIGN = 1;
    
	private int columns; // Número de columnas.
    private int hGap;    // Espacio total horizontal (entre dos componentes).
    private int vGap;    // Espacio total vertical (entre dos componentes).
    private int align;
    
    public MatrixLayout(int columns, int hGap, int vGap,int align) {
        if (columns <= 0) {
            throw new IllegalArgumentException("El número de columnas debe ser mayor a 0.");
        }
        this.columns = columns;
        this.hGap = hGap;
        this.vGap = vGap;
        this.align = align;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        // No utilizado.
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        // No utilizado.
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            int count = parent.getComponentCount();
            int rows = (int) Math.ceil((double) count / columns);

            int maxWidth = 0;
            int maxHeight = 0;

            for (int i = 0; i < count; i++) {
                Dimension d = parent.getComponent(i).getPreferredSize();
                maxWidth = Math.max(maxWidth, d.width);
                maxHeight = Math.max(maxHeight, d.height);
            }

            Insets insets = parent.getInsets();
            return new Dimension(
                insets.left + insets.right + columns * maxWidth + (columns - 1) * hGap,
                insets.top + insets.bottom + rows * maxHeight + (rows - 1) * vGap
            );
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int count = parent.getComponentCount();
            if (count == 0) {
                return;
            }

            Insets insets = parent.getInsets();

            int maxWidth = 0;
            int maxHeight = 0;
            for (int i = 0; i < count; i++) {
                Dimension d = parent.getComponent(i).getPreferredSize();
                maxWidth = Math.max(maxWidth, d.width);
                maxHeight = Math.max(maxHeight, d.height);
            }

            int cellWidth = maxWidth;
            int cellHeight = maxHeight;

            int rows = (int) Math.ceil((double) count / columns);
            int totalMatrixWidth = columns * cellWidth + (columns - 1) * hGap;

            int containerWidth = parent.getWidth() - insets.left - insets.right;

            int offsetX = 0;
            if (align == 0) {
                offsetX = (containerWidth - totalMatrixWidth) / 2; // Centrado.
            } else if (align == 1) {
                offsetX = containerWidth - totalMatrixWidth; // Derecha.
            }

            for (int i = 0; i < count; i++) {
                Component c = parent.getComponent(i);

                int row = i / columns;
                int col = i % columns;

                int x = insets.left + offsetX + col * (cellWidth + hGap);
                int y = insets.top + row * (cellHeight + vGap);

                c.setBounds(x, y, cellWidth, cellHeight);
            }
        }
    }
   
    
    public int getColumns() {
        return columns;
    }

    public int getHGap() {
        return hGap;
    }

    public int getVGap() {
        return vGap;
    }
}

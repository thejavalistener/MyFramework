package thejavalistener.fwk.awt.panel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class MatrixLayout implements LayoutManager {
    private int columns; // Número de columnas.
    private int hGap;    // Espacio total horizontal (entre dos componentes).
    private int vGap;    // Espacio total vertical (entre dos componentes).

    public MatrixLayout(int columns, int hGap, int vGap) {
        if (columns <= 0) {
            throw new IllegalArgumentException("El número de columnas debe ser mayor a 0.");
        }
        this.columns = columns;
        this.hGap = hGap;
        this.vGap = vGap;
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

//    @Override
//    public void layoutContainer(Container parent) {
//        synchronized (parent.getTreeLock()) {
//            int count = parent.getComponentCount();
//            if (count == 0) {
//                return;
//            }
//
//            Insets insets = parent.getInsets();
//            
//            // Determinamos el tamaño basado en el tamaño preferido de los componentes.
//            int maxWidth = 0;
//            int maxHeight = 0;
//            for (int i = 0; i < count; i++) {
//                Dimension d = parent.getComponent(i).getPreferredSize();
//                maxWidth = Math.max(maxWidth, d.width);
//                maxHeight = Math.max(maxHeight, d.height);
//            }
//
//            // Calculamos las dimensiones de las celdas.
//            int cellWidth = maxWidth;
//            int cellHeight = maxHeight;
//
//            for (int i = 0; i < count; i++) {
//                Component c = parent.getComponent(i);
//
//                // Fila y columna actuales del componente.
//                int row = i / columns;
//                int col = i % columns;
//
//                // Coordenadas del componente con espacio fijo entre celdas.
//                int x = insets.left + col * (cellWidth + hGap);
//                int y = insets.top + row * (cellHeight + vGap);
//
//                // Establecemos los límites del componente.
//                c.setBounds(x, y, cellWidth, cellHeight);
//            }
//        }
//    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int count = parent.getComponentCount();
            if (count == 0) {
                return;
            }

            Insets insets = parent.getInsets();
            
            // Determinamos el tamaño basado en el tamaño preferido de los componentes.
            int maxWidth = 0;
            int maxHeight = 0;
            for (int i = 0; i < count; i++) {
                Dimension d = parent.getComponent(i).getPreferredSize();
                maxWidth = Math.max(maxWidth, d.width);
                maxHeight = Math.max(maxHeight, d.height);
            }

            // Calculamos las dimensiones de las celdas.
            int cellWidth = maxWidth;
            int cellHeight = maxHeight;

            for (int i = 0; i < count; i++) {
                Component c = parent.getComponent(i);

                // Fila y columna actuales del componente.
                int row = i / columns;
                int col = i % columns;

                // Coordenadas del componente con el ajuste de gap/2 en la primera columna.
                int x = insets.left + col * (cellWidth + hGap) + hGap / 2;
                int y = insets.top + row * (cellHeight + vGap) + vGap / 2;

                // Establecemos los límites del componente.
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

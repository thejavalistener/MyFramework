package thejavalistener.fwk.util.hotkey;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class KeyIdentifier {
    private final int keyCode;
    private final int modifiers;

    public KeyIdentifier(int keyCode, int modifiers) {
        this.keyCode = keyCode;
        this.modifiers = modifiers;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public int getModifiers() {
        return modifiers;
    }

    public static KeyIdentifier fromCombo(String combo) {
        combo = combo.trim().toUpperCase();

        // Extraer letra clave final (ej. "CTRL+ALT+G" → "G")
        String[] parts = combo.split("\\+");
        String keyChar = parts[parts.length - 1];
        int keyCode = KeyEvent.getExtendedKeyCodeForChar(keyChar.charAt(0));

        int mods = 0;
        for (String p : parts) {
            switch (p) {
                case "CTRL"     -> mods |= InputEvent.CTRL_DOWN_MASK;
                case "ALT"      -> mods |= InputEvent.ALT_DOWN_MASK;
                case "SHIFT"    -> mods |= InputEvent.SHIFT_DOWN_MASK;
                case "ALTGR"    -> mods |= InputEvent.ALT_GRAPH_DOWN_MASK;
                case "META"     -> mods |= InputEvent.META_DOWN_MASK;
                default         -> {} // letra final, ignorar
            }
        }

        return new KeyIdentifier(keyCode, mods);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof KeyIdentifier)) return false;
        KeyIdentifier other = (KeyIdentifier) obj;
        return keyCode == other.keyCode && (modifiers & other.modifiers) == other.modifiers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyCode, modifiers);
    }

    @Override
    public String toString() {
        return "KeyIdentifier[keyCode=" + keyCode + ", modifiers=" + modifiers + "]";
    }
}
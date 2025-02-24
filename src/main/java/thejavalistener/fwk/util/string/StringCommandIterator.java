package thejavalistener.fwk.util.string;

import java.util.ArrayList;

public class StringCommandIterator {
    private String text;
    private int index;
    private String openDelim;
    private String closeDelim;

    public StringCommandIterator(String txt) {
        this(txt, "[", "]");
    }

    public StringCommandIterator(String txt, String openDelim, String closeDelim) {
        this.text = txt;
        this.index = 0;
        this.openDelim = openDelim;
        this.closeDelim = closeDelim;
    }

    public boolean hasNext() {
        return index < text.length();
    }

    public boolean next(StringBuffer sub) {
        if (!hasNext()) {
            return false;
        }

        sub.setLength(0);
        if (text.startsWith(openDelim, index)) {
            index += openDelim.length();
            while (index < text.length() && !text.startsWith(closeDelim, index)) {
                sub.append(text.charAt(index));
                index++;
            }
            index += closeDelim.length();
            return true;
        } else {
            while (index < text.length() && !text.startsWith(openDelim, index)) {
                sub.append(text.charAt(index));
                index++;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        StringBuffer buff = new StringBuffer();
//      String s = "Esta [[b]]es mi [[RED]]cadena roja [[x]] ya no es roja [[x]] ni negrita";
        String s = "Esta ${b}es mi ${RED}cadena roja${x} ya no es roja${x} ni negrita";
        StringCommandIterator si = new StringCommandIterator(s,"${","}");
        ArrayList<String> lst = new ArrayList<>();

        while (si.hasNext()) {
            boolean isCmd = si.next(buff);
            if (!isCmd) {
                System.out.print(buff);
            } else {
                lst.add(buff.toString());
            }
        }

        System.out.println();
        for (String cc : lst) {
            System.out.print(cc);
        }
    }
}

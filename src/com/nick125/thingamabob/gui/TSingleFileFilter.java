package simulation.gui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * A single-extension file filter
 *
 * @author kampernj. Created Nov 1, 2010.
 */
public class TSingleFileFilter extends FileFilter {

    private String extension;
    private String description;

    /**
     * Creates a new file filter with a single extension.
     *
     * @param extension
     * @param description
     */
    public TSingleFileFilter(String extension, String description) {
        super();
        this.extension = extension;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true; // We don't actually care..
        }

        String extension = this.getExtension(f);

        if (extension != null) {
            if (extension.equals(this.extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the file extension.
     * <p/>
     * Modified from the example given in the Java tutorial
     *
     * @param f
     * @return extension
     */

    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(++i).toLowerCase();
		}
		return ext;
	}
}

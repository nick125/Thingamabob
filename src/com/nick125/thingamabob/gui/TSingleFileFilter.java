/*
 * Thingamabob - A Java-based Turing Machine Emulator
 * Copyright (c) 2010 Nicholas Kamper, Drew Hill, Travis Baumbaugh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.nick125.thingamabob.gui;

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

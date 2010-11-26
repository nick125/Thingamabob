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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;

/**
 * Similar to an AbstractAction, except the threadedWorker() action is called in
 * a thread.
 *
 * @author kampernj. Created Oct 31, 2010.
 */
public abstract class ThreadedAction extends AbstractAction {
    private boolean status;

    /**
     * Creates a new ThreadedAction
     */
    public ThreadedAction() {
        super();
    }

    /**
     * Creates a new ThreadedAction
     *
     * @param name
     * @param icon
     */
    public ThreadedAction(String name, Icon icon) {
        super(name, icon);
    }

    /**
     * Creates a new ThreadedAction
     *
     * @param name
     */
    public ThreadedAction(String name) {
        super(name);
    }

    private class ActionRunner implements Runnable {

        @Override
        public void run() {
            ThreadedAction.this.threadedWorker();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if we're on the EDT
        if (!SwingUtilities.isEventDispatchThread()) {
            // We're not in the EDT, schedule the preThreadActions on the EDT
            // and wait;
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    @Override
                    public void run() {
                        ThreadedAction.this.status = ThreadedAction.this
                                .preThreadActions();

                    }
                });
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            } catch (InvocationTargetException exception) {
                exception.printStackTrace();
            }
        } else {
            // We're on the EDT - just run preThreadActions
            this.status = this.preThreadActions();
        }
        if (this.status) {
            // preThreadActions returned successfully, continue with the thread.
            Thread worker = new Thread(new ActionRunner());
            worker.start();
        }
    }

    /**
     * This is executed on the EDT before calling the thread. Returns true if
     * the thread can proceed while if the thread should stop, return false.
     * <p/>
     * You can assume that this _will_ execute on the EDT. Trust me.
     *
     * @return Whether the thread should continue or not.
     */
    public boolean preThreadActions() {
        // Empty
        return true;
    }

    /**
     * This is executed in the thread.
     */
    public abstract void threadedWorker();
}

package simulation.gui;

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

package me.richo.cabtraining.util;

/**
 * Class ini dibuat untuk membuat proses baru berguna sebagai perekam data ujian
 */
public class RunnableThread extends Thread{

    private Runnable runnable;

    public RunnableThread() {

    }

    /**
     * Set method untuk proses
     * @param runnable
     */
    public void setOnRun(Runnable runnable){
        this.runnable = runnable;
    }

    @Override
    public void run() {
        if(runnable==null){
            super.run();
        } else {
            runnable.run();
        }
    }
}

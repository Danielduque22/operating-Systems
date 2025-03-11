/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os;

import java.util.Comparator;

/**
 *
 * @author prestamour
 */
public class SJF_NP extends Scheduler{

    
    SJF_NP(OS os){
        super(os);
    }
    
   
    @Override
    public void getNext(boolean cpuEmpty) {
        if (!processes.isEmpty()) {
            // Ordena la cola de listos por la duración de la siguiente ráfaga de CPU
            processes.sort(Comparator.comparingInt(p -> {
                if (p.getPBL().bursts == null || p.getPBL().bursts.isEmpty()) {
                    return Integer.MAX_VALUE;
                } else {
                    return p.getPBL().bursts.get(0).getCycles(); // Accede directamente al primer burst
                }
            }));
            System.out.println(processes);
            // Selecciona el proceso con la ráfaga más corta
            Process selectedProcess = processes.get(0);

            // Si la CPU está vacía o el proceso seleccionado tiene una ráfaga más corta que el proceso actual
            if (cpuEmpty || (os.getProcessInCPU() != null && os.getProcessInCPU().getPBL().bursts != null && !os.getProcessInCPU().getPBL().bursts.isEmpty() && selectedProcess.getPBL().bursts != null && !selectedProcess.getPBL().bursts.isEmpty() && selectedProcess.getPBL().bursts.get(0).getCycles() < os.getProcessInCPU().getPBL().bursts.get(0).getCycles())) { // Accede directamente al primer burst
                // Interrumpe el proceso actual (si hay uno) y carga el seleccionado
                os.interrupt(InterruptType.SCHEDULER_CPU_TO_RQ, selectedProcess);
            }
        }

    }
    
    @Override
    public void newProcess(boolean cpuEmpty) {} //Non-preemtive

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {} //Non-preemtive
    
}

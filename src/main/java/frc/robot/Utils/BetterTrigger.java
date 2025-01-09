package frc.robot.Utils;

import static edu.wpi.first.util.ErrorMessages.requireNonNullParam;


import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class BetterTrigger extends Trigger{

    private final BooleanSupplier m_condition;
    private final EventLoop m_loop;

   

    public BetterTrigger(EventLoop loop, BooleanSupplier condition) {
        super(loop, condition);
        m_loop = requireNonNullParam(loop, "loop", "Trigger");
        m_condition = requireNonNullParam(condition, "condition", "Trigger");
    }

    /**
     * Creates a new trigger based on the given condition.
     *
     * <p>Polled by the default scheduler button loop.
     *
     * @param condition the condition represented by this trigger
     */
    public BetterTrigger(BooleanSupplier condition) {
        this(CommandScheduler.getInstance().getDefaultButtonLoop(), condition);
    }

    


  /**
   * Starts the command whenever the condition is true
   *
   * <p>Doesn't re-start the command if it ends while the condition is still `true`. If the command
   * should restart, see {@link edu.wpi.first.wpilibj2.command.RepeatCommand}.
   *
   * @param command the command to start
   * @return this trigger, so calls can be chained
   */
  public Trigger whileTrue(Command command) {
    requireNonNullParam(command, "command", "whileTrue");
    m_loop.bind(
        new Runnable() {
          

          @Override
          public void run() {
            boolean pressed = m_condition.getAsBoolean();

            if (!CommandScheduler.getInstance().isScheduled(command)&& pressed) {
              command.schedule();
            } else if (!pressed) {
              command.cancel();
            }

            
          }
        });
    return this;
  }

  /**
   * Starts the given command when the condition changes to `false` and cancels it when the
   * condition changes to `true`.
   *
   * <p>Doesn't re-start the command if it ends while the condition is still `false`. If the command
   * should restart, see {@link edu.wpi.first.wpilibj2.command.RepeatCommand}.
   *
   * @param command the command to start
   * @return this trigger, so calls can be chained
   */
  public Trigger whileFalse(Command command) {
    requireNonNullParam(command, "command", "whileFalse");
    m_loop.bind(
        new Runnable() {
          

          @Override
          public void run() {
            boolean pressed = m_condition.getAsBoolean();

            if (!CommandScheduler.getInstance().isScheduled(command)&&!pressed) {
              command.schedule();
            } else if (pressed) {
              command.cancel();
            }

            
          }
        });
    return this;
  }



}

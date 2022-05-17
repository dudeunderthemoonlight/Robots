package game;


import java.awt.*;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Модель поведения робота
 */
public class RobotModel extends Observable {

    private static Timer initTimer() {
        return new Timer("events generator", true);
    }

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;
    private static double angularVelocity = maxAngularVelocity;

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;

    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    public RobotModel() {
        Timer m_timer = initTimer();
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
                notifyObservers();
            }
        }, 0, 10);
    }

    /**
     * Отслеживает движение робота и меняет его координаты в зависимости от положения при помощи метода moveRobot.
     */
    protected void onModelUpdateEvent() {
        double distance = MathOperations.distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance <= 0.5) {
            return;
        }

        double velocity = maxVelocity;
        double error = 0.001d;
        double angleToTarget = MathOperations
                .angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);

        double diff = angleToTarget - m_robotDirection;
        if (Math.abs(diff) < error) {
            angularVelocity = -angularVelocity;
        }
        if (Math.abs(Math.PI - (Math.abs(diff))) < error) {
            angularVelocity = -angularVelocity;
        }
        if ((diff > error && diff <= Math.PI) || (diff < -Math.PI)) {
            angularVelocity = maxAngularVelocity;
        }
        if ((diff < -error && diff > -Math.PI) || (diff >= Math.PI)) {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity);
    }

    /**
     * Меняет кооринаты робота.
     *
     * @param angularVelocity угловая скорость (положение робота)
     */
    private void moveRobot(double velocity, double angularVelocity) {
        velocity = MathOperations.applyLimits(velocity, 0, maxVelocity);
        angularVelocity = MathOperations.applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection + angularVelocity * (double) 10) - Math.sin(m_robotDirection));
        if (!Double.isFinite(newX)) {
            newX = m_robotPositionX + velocity * (double) 10 * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection + angularVelocity * (double) 10) - Math.cos(m_robotDirection));
        if (!Double.isFinite(newY)) {
            newY = m_robotPositionY + velocity * (double) 10 * Math.sin(m_robotDirection);
        }
        m_robotDirection = MathOperations.asNormalizedRadians(m_robotDirection + angularVelocity * (double) 10);

        m_robotPositionX = newX;
        m_robotPositionY = newY;
        setChanged();
    }

    public void setTargetPosition(Point p) {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }

    public double getRobotPositionX() {
        return m_robotPositionX;
    }

    public double getRobotPositionY() {
        return m_robotPositionY;
    }

    public double getRobotDirection() {
        return m_robotDirection;
    }

    public int getTargetPositionX() {
        return m_targetPositionX;
    }

    public int getTargetPositionY() {
        return m_targetPositionY;
    }
}
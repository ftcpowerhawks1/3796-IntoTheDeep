package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.frozenmilk.dairy.cachinghardware.CachingServo;
import dev.frozenmilk.dairy.core.dependency.Dependency;
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation;
import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.subsystems.SDKSubsystem;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import dev.frozenmilk.util.cell.Cell;

public class DefaultTelemetry extends SDKSubsystem {
    public static final DefaultTelemetry INSTANCE = new DefaultTelemetry();
    private DefaultTelemetry() {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface Attach{}

    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(Attach.class));

    @NonNull
    @Override
    public Dependency<?> getDependency() {
        return dependency;
    }

    @Override
    public void setDependency(@NonNull Dependency<?> dependency) {
        this.dependency = dependency;
    }

    @Override
    public void preUserLoopHook(@NonNull Wrapper opMode) {

        opMode.getOpMode().telemetry.addData("IMU: ", Drive.INSTANCE.getIMU());

        opMode.getOpMode().telemetry.addData("---------------------","------------------------");

        opMode.getOpMode().telemetry.addData("Vertical Target Position: ", VerticalSlides.INSTANCE.getVerticalTargetPos());
        opMode.getOpMode().telemetry.addData("Vertical Actual Position: ", VerticalSlides.INSTANCE.getEncoder());
        opMode.getOpMode().telemetry.addData("Vertical Velocity: ", VerticalSlides.INSTANCE.getVelocity());
        opMode.getOpMode().telemetry.addData("Vertical Controller Finished: ", VerticalSlides.INSTANCE.getControllerFinished());

        opMode.getOpMode().telemetry.addData("Vertical Current Draw: ", VerticalSlides.INSTANCE.getCurrent());
        opMode.getOpMode().telemetry.addData("Vertical Current Draw Change: ", VerticalSlides.INSTANCE.getCurrentChange());

        opMode.getOpMode().telemetry.addData("---------------------","------------------------");

        opMode.getOpMode().telemetry.addData("Horizontal Target Position: ", HorizontalSlides.INSTANCE.getHorizontalTargetPos());
        opMode.getOpMode().telemetry.addData("Horizontal Actual Position: ", HorizontalSlides.INSTANCE.getEncoder());
        opMode.getOpMode().telemetry.addData("Horizontal Velocity: ", HorizontalSlides.INSTANCE.getVelocity());
        opMode.getOpMode().telemetry.addData("Horizontal Controller Finished: ", HorizontalSlides.INSTANCE.getControllerFinished());

        opMode.getOpMode().telemetry.addData("Horizontal Current Draw: ", HorizontalSlides.INSTANCE.getCurrent());
        opMode.getOpMode().telemetry.addData("Horizontal Current Draw Change: ", HorizontalSlides.INSTANCE.getCurrentChange());


        opMode.getOpMode().telemetry.update();
    }
}
package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.rev.RevColorSensorV3;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.frozenmilk.dairy.cachinghardware.CachingCRServo;
import dev.frozenmilk.dairy.cachinghardware.CachingServo;
import dev.frozenmilk.dairy.core.dependency.Dependency;
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation;
import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.commands.groups.Sequential;
import dev.frozenmilk.mercurial.subsystems.SDKSubsystem;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import dev.frozenmilk.util.cell.Cell;

public class IntakeSubsystem extends SDKSubsystem {
    public static final IntakeSubsystem INSTANCE = new IntakeSubsystem();
    public IntakeSubsystem() { }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface Attach{}
    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(HorizontalSlides.Attach.class));
    @NonNull
    @Override
    public Dependency<?> getDependency() {
        return dependency;
    }

    @Override
    public void setDependency(@NonNull Dependency<?> dependency) {
        this.dependency = dependency;
    }

    // hardware
    private final Cell<CachingServo> intakePivot = subsystemCell(() -> getHardwareMap().get(CachingServo.class, "intakePivotServo"));
    private final Cell<CachingCRServo> intake = subsystemCell(() -> getHardwareMap().get(CachingCRServo.class, "intake"));
    private final Cell<RevColorSensorV3> intakeColorSensor = subsystemCell(() -> getHardwareMap().get(RevColorSensorV3.class, "color"));


    // set target method
    public void setPosition(double Position) {
         intakePivot.get().setPosition(Position);
    }
    public double getPosition() {
        return(intakePivot.get().getPosition());
    }

    private void cashedIntakeCommand(double power) {
        intakePivot.get().setPosition(1);
        if (intake.get().getPower() != power) {
            intake.get().setPower(power);
        }
    }

    // init hook, to handle init config
    @Override
    public void preUserInitHook(@NonNull Wrapper opMode) {

    }

    // init hook, to handle init config
    @Override
    public void preUserStartHook(@NonNull Wrapper opMode) {

    }
    public Lambda intake(double power) {
        return new Lambda("intake")
                .setInit(() -> cashedIntakeCommand(1))
                .setFinish(() -> intakeColorSensor.get().getDistance(DistanceUnit.MM) <= 5);
    }
    public Lambda returnIntake(double target) {
        return new Lambda("return-intake")
                .setInit(() -> setPosition(target));
    }

    Sequential intakeCommand = new Sequential (
        new Lambda("intake"),
        new Lambda("return-intake")
    );
}
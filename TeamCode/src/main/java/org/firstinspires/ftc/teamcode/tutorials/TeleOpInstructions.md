# FTC Meeting Plan - Get TeleOp Driving

## Today's Mission

By the end of the meeting we want:

✅ Robot drives

✅ Robot strafes

✅ Robot rotates

✅ Robot reports position (X, Y, Heading)

✅ Robot sees AprilTags

We are NOT trying to finish autonomous today.

---

# Today's Priority Order

Follow these in order.

1. Robot Drives
2. Motor Directions Correct
3. Localization Works
4. Limelight Works
5. Slow Mode Works
6. Field Centric Works

Do NOT move to the next item until the previous one works.

---

# Use Existing Code

We are NOT starting from scratch.

We already have useful code in:

## AutoPathHeidi.java

Use:

- PinpointLocalizer setup
- PedroPathing follower setup
- Pose telemetry

Current Pedro Pose Methods:

```java
pose.x()
pose.y()
pose.heading()
```

---

## ApriltagLimelight.java

Use:

- Limelight setup
- AprilTag telemetry
- Pipeline switching

NOTE:

Alliance selection is moving to Autonomous.

CompetitionAuto will:

```text
Select Alliance
↓
Save Alliance
↓
BioBuzzTeleOp Reads Alliance
```

For now:

```java
alliance = "Red";
```

or

```java
alliance = "Unknown";
```

---

## statesExamples.java

Use:

- Mecanum drive math
- Drive motor setup
- Speed scaling
- Driving logic

---

# Team Assignments

## Team A - Drive Team

### Goal

Make the robot move correctly.

### Tasks

- [ ] Verify motor names
- [ ] Verify motor directions
- [ ] Verify forward/backward
- [ ] Verify strafe
- [ ] Verify rotate

### Copy From

statesExamples.java

Look at:

```java
frontLeft
frontRight
backLeft
backRight
```

and

```java
fl
fr
bl
br
```

wheel power calculations.

### Success

Robot drives correctly.

---

## Team B - Localization Team

### Goal

Robot knows where it is.

### Copy From

AutoPathHeidi.java

Use:

```java
PinpointLocalizer
Follower
Pose
```

### Tasks

- [ ] localizer initializes
- [ ] localizer.update()
- [ ] telemetry shows X
- [ ] telemetry shows Y
- [ ] telemetry shows Heading

### Example

```java
telemetry.addData("X", pose.x());
telemetry.addData("Y", pose.y());
telemetry.addData("Heading", pose.heading());
```

### Success

Robot position changes while driving.

---

## Team C - Vision Team

### Goal

Robot sees AprilTags.

### Copy From

ApriltagLimelight.java

Use:

```java
Limelight3A
```

### Tasks

- [ ] initialize Limelight
- [ ] start Limelight
- [ ] display tags seen
- [ ] display TX
- [ ] display TY
- [ ] display TA
- [ ] verify pipeline

### Success

Robot sees and displays AprilTags.

---

# Build BioBuzzTeleOp

Create:

```java
@TeleOp(name="BioBuzzTeleOp")
public class BioBuzzTeleOp extends OpMode
```

This will become our competition TeleOp.

---

# Main Structure

## init()

Initialize:

```java
Motors
Localizer
Follower
Limelight
```

Example:

```java
frontLeft = hardwareMap.get(DcMotor.class, "leftFront");
frontRight = hardwareMap.get(DcMotor.class, "rightFront");
backLeft = hardwareMap.get(DcMotor.class, "leftBack");
backRight = hardwareMap.get(DcMotor.class, "rightBack");

localizer =
    new PinpointLocalizer(
            hardwareMap,
            Constants.localizerConfig);

follower =
    Constants.create(hardwareMap);

limelight =
    hardwareMap.get(
        Limelight3A.class,
        "limeLight");
```

---

## start()

```java
limelight.start();
```

---

## loop()

Every loop:

```java
localizer.update();

follower.update();

Pose pose =
    follower.pose();

LLResult result =
    limelight.getLatestResult();
```

Then:

```java
Drive
Telemetry
Vision
```

---

# TeleOp Driving Controls

Goal:

```text
Left Stick Y = Forward / Backward

Left Stick X = Strafe

Right Stick X = Rotate
```

---

# IMPORTANT

Before using field-centric:

Verify robot-centric driving works.

---

# Drive Debug Checklist

Before enabling Field Centric:

□ Push stick forward

Robot moves forward

□ Pull stick backward

Robot moves backward

□ Push stick right

Robot strafes right

□ Push stick left

Robot strafes left

□ Right stick right

Robot rotates right

□ Right stick left

Robot rotates left

If any fail:

Check motor directions FIRST.

Do NOT modify Pedro.

Do NOT modify Limelight.

Fix drivetrain first.

---

# Telemetry Requirements

Show:

```text
Alliance
X Position
Y Position
Heading
Tags Seen
TX
TY
TA
```

Example:

```java
telemetry.addData("X", pose.x());
telemetry.addData("Y", pose.y());
telemetry.addData("Heading", pose.heading());
```

Optional:

```text
Tag Count
Current Pipeline
```

---

# Testing Checklist

## Drive Test

- [ ] Robot moves forward
- [ ] Robot moves backward
- [ ] Robot strafes left
- [ ] Robot strafes right
- [ ] Robot rotates

---

## Localization Test

- [ ] X changes while moving
- [ ] Y changes while moving
- [ ] Heading changes when rotating

---

## Vision Test

- [ ] Limelight starts
- [ ] Tag IDs display
- [ ] TX displays
- [ ] TY displays
- [ ] TA displays

---

# Stretch Goal #1

Slow Mode

Example:

```java
double speedScale =
    gamepad1.right_bumper
        ? 0.4
        : 1.0;
```

Purpose:

```text
Precision Driving
```

---

# Stretch Goal #2

Field Centric

Only after Drive Test passes.

Use:

```java
pose.heading()
```

Verify:

```text
Drive Forward

Rotate Robot

Drive Forward Again
```

Expected:

```text
Robot still drives toward same field direction.
```

---

# Things We Are NOT Building Today

Do NOT spend time on:

- AutoPaths
- CompetitionAuto
- ShootBox
- AutoAim
- State Machine
- Autonomous scoring
- Anti-tip logic

Those happen after TeleOp drives correctly.

---

# Future Architecture

## Today

```text
BioBuzzTeleOp
│
├── Motors
├── Pedro Localization
├── Pinpoint
└── Limelight
```

## Next

```text
BioBuzzTeleOp
│
├── ShootBox
└── AutoAim
```

## Later

```text
CompetitionAuto
│
├── AutoPaths
├── VisionSystem
├── StateMachine
└── Mechanisms
```

---

# What Success Looks Like

Driver can:

✅ Drive Forward

✅ Drive Backward

✅ Strafe Left

✅ Strafe Right

✅ Rotate

Telemetry shows:

✅ X

✅ Y

✅ Heading

✅ AprilTags

Robot does not crash.

Robot responds consistently.

---

# End Of Meeting Success Check

Can the robot:

- [ ] Drive forward
- [ ] Drive backward
- [ ] Strafe left
- [ ] Strafe right
- [ ] Rotate

Can the robot:

- [ ] Show X position
- [ ] Show Y position
- [ ] Show Heading

Can the robot:

- [ ] See AprilTags
- [ ] Display Tag IDs

If all boxes are checked:

🎉 Successful FTC meeting.

Rule #1:

A robot that drives today is more valuable than perfect software architecture that doesn't move.
=========================================================
FTC SOFTWARE ROADMAP
Team Goal:
Build a robot that can drive, localize, see AprilTags,
auto-aim, run autonomous paths, and coordinate mechanisms
using state machines.
=========================================================


IMPORTANT CONCEPTS
==================

Class
A thing.

Method
An action a thing can do.

Object
An instance of a class.

Examples:

Class: Shooter
Methods:
spinUp()
feed()
stop()

Class: Intake
Methods:
lower()
raise()
run()
stop()

Class: ShootBox
Methods:
isInBox()

---------------------------------------------------------
PHASE 1 - BASIC TELEOP
---------------------------------------------------------

Goal:
Robot can drive.

MainTeleOp Responsibilities:

    Read gamepads
    Drive robot
    Show telemetry

Controls:

    Left Stick  = Translate
    Right Stick = Rotate

Success Criteria:

    [ ] Drive forward
    [ ] Drive backward
    [ ] Strafe
    [ ] Rotate


---------------------------------------------------------
PHASE 2 - LOCALIZATION
---------------------------------------------------------

Goal:
Robot knows where it is.

Systems:

    Pinpoint
    PedroPathing

Telemetry:

    X
    Y
    Heading

Success Criteria:

    [ ] Position changes correctly
    [ ] Heading updates correctly


---------------------------------------------------------
PHASE 3 - VISION
---------------------------------------------------------

Goal:
Robot sees AprilTags.

Class:

    VisionSystem
    (currently ApriltagLimelight)

Responsibilities:

    getSeenTags()
    switchPipeline()
    getRobotPose()
    showTelemetry()

Telemetry:

    Alliance
    Tags Seen
    Tag Count
    Tx
    Ty
    Ta

Success Criteria:

    [ ] Robot detects tags
    [ ] Can switch pipelines


---------------------------------------------------------
PHASE 4 - ALLIANCE SELECTION
---------------------------------------------------------

Goal:
Select alliance during INIT.

Selection:

    Red
    Blue

Future:

    Save last alliance used.

Success Criteria:

    [ ] Driver can change alliance
    [ ] Display alliance on telemetry


---------------------------------------------------------
PHASE 5 - SHOOT BOXES
---------------------------------------------------------

Goal:
Robot knows when it is in a special area.

Class:

    ShootBox

Methods:

    isInBox(x,y)

Example Objects:

    northShootBox
    southShootBox
    centerShootBox

Success Criteria:

    [ ] Robot detects box entry
    [ ] Telemetry displays active box


---------------------------------------------------------
PHASE 6 - AUTO AIM
---------------------------------------------------------

Goal:
Robot points at target automatically.

Class:

    AutoAim

Responsibilities:

    getTargetHeading()
    calculateRotation()

Behavior:

Outside Shoot Box

    Driver controls translation
    Driver controls rotation

Inside Shoot Box

    Driver controls translation
    Robot controls rotation

Success Criteria:

    [ ] Robot faces target
    [ ] Driver can still move


---------------------------------------------------------
PHASE 7 - MECHANISM CLASSES
---------------------------------------------------------

Goal:
Create reusable robot subsystems.

Classes

    Shooter
    Intake
    Flower
    Lift
    Climber
    Transfer

Each class owns its hardware.

Example:

Shooter

    spinUp()
    feed()
    stop()

Intake

    lower()
    raise()
    run()
    stop()

Flower

    open()
    close()
    shake()
    stop()


---------------------------------------------------------
PHASE 8 - STATE MACHINE
---------------------------------------------------------

Goal:
Robot behavior is controlled by robot states.

State = What the robot is currently trying to do.

Example:

    IDLE
    PICK_UP
    SHOOT
    EMPTY_FLOWER
    MOVE
    PARK

Benefits:

    No giant nested if statements.
    Easier debugging.
    Easier autonomous.
    Easier driver assist.

Example:

IDLE

    Everything stopped.

PICK_UP

    Lower intake
    Run intake
    Wait for game piece
    Return to IDLE

SHOOT

    Spin shooter
    Feed game piece
    Return to IDLE

EMPTY_FLOWER

    Open flower
    Shake flower
    Close flower
    Return to IDLE

Future States:

    AUTO_AIM
    SCORE_HIGH
    SCORE_LOW
    HANG_PREP
    HANG
    CLIMB


---------------------------------------------------------
PHASE 9 - AUTONOMOUS PATHS
---------------------------------------------------------

Goal:
Store autonomous paths separate from robot behavior.

Class:

    AutoPaths

Contains:

    Start Poses
    Waypoints
    Path Definitions

Methods:

    buildPreloadPath()
    buildCyclePath()
    buildParkPath()

Benefits:

    Reusable by all autonomous programs.


---------------------------------------------------------
PHASE 10 - COMPETITION AUTO
---------------------------------------------------------

Goal:
Run complete autonomous routine.

Class:

    CompetitionAuto

Uses:

    AutoPaths
    VisionSystem
    StateMachine
    Mechanisms

Flow:

INIT
↓
Alliance Selection
↓
Verify Vision
↓
START
↓
Follow Paths
↓
Score Elements
↓
Park


---------------------------------------------------------
PHASE 11 - ADVANCED DRIVER ASSIST
---------------------------------------------------------

Examples:

    Auto Aim
    Heading Hold
    Face AprilTag
    Drive To Shoot Box
    Auto Lineup

These assist the driver but do not replace them.


---------------------------------------------------------
FINAL SOFTWARE STRUCTURE
---------------------------------------------------------

teamcode

├── teleop
│      MainTeleOp
│
├── autonomous
│      CompetitionAuto
│      AutoPaths
│
├── vision
│      VisionSystem
│
├── driverassist
│      ShootBox
│      AutoAim
│
├── mechanisms
│      Shooter
│      Intake
│      Flower
│      Lift
│      Climber
│
├── states
│      RobotState
│      StateMachine
│
└── util
Constants


NEXT TEAM GOALS
===============

Meeting 1
----------
[ ] TeleOp driving
[ ] Localization telemetry
[ ] AprilTag telemetry

Meeting 2
----------
[ ] ShootBox detection
[ ] AutoAim heading calculations

Meeting 3
----------
[ ] Mechanism classes operational
[ ] State machine controls mechanisms

Meeting 4
----------
[ ] Autonomous paths created
[ ] CompetitionAuto uses AutoPaths

Meeting 5
----------
[ ] Full autonomous routine
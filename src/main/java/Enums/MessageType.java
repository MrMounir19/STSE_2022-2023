package Enums;

public enum MessageType {

    //RobotSide
    RegistrationConfirmation,
    JobRequest,
    JobFinished,
    JobFailed,

    LocationRequest,

    //ServerSide
    Registration,
    Collision,
    Job
}

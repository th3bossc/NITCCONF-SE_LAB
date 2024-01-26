export type Session = {
    id: string,
    title: string,
    description: string,
    language: string,
    level: Level,
    status: Status,
    tags: Tag[],
    date: Date,
}

export type Tag = {
    id: string,
    title: string,
    sessions: Session[],
}

export type Review = {
    id : string,
    reviewer: User,
    comment: string,
}

export type User = {
    id: string,
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: string,
    role: Role,
}

export type Role = "USER" | "REVIEWER" | "PROGRAM_COMMITTEE";

export type Level = "BEGINNER" | "INTERMEDIATE" | "ADVANCED" | "EXPERT";

export type Status = "ACCEPTED" | "PENDING" | "REJECTED";

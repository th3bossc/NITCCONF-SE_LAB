import { Dispatch, SetStateAction } from "react"

export type Session = {
    id?: string,
    title: string,
    description: string,
    language: string,
    level: Level,
    status: Status,
    tags: Tag[],
    date: Date,
    documentVersions: DocumentVersion[]
}

export type Tag = {
    id?: string,
    title: string,
    sessions: Session[],
}

export type Review = {
    id: string,
    reviewer: User,
    comment: string,
}

export type User = {
    id?: string,
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: string,
    role: Role,
    isVerified: boolean,
}

export type DocumentVersion = {
    id?: string,
    changesDesc: string,
    version: number,
    date: Date,
    reviews: Review[]
}

export type Role = "USER" | "REVIEWER" | "PROGRAM_COMMITTEE";

export type Level = "BEGINNER" | "INTERMEDIATE" | "ADVANCED" | "EXPERT";

export type Status = "ACCEPTED" | "PENDING" | "REJECTED";


export type AuthDetails = {
    isLoggedIn: boolean,
    user: User | null,
    setUser: Dispatch<SetStateAction<User | null>>,
    sessions: Session[],
    setSessions: Dispatch<SetStateAction<Session[]>>
    jwt: string | null,
    logIn: (jwt: string) => void
    logOut: () => void,
    loading: boolean,
    updateSessions: () => void,
}


export type RegisterRequest = {
    firstName: string,
    lastName: string,
    email: string,
    password: string,
    phoneNumber: string,
}

export type LoginRequest = {
    email: string,
    password: string,
}

export type RegisterFields = {
    firstName: boolean,
    lastName: boolean,
    email: boolean,
    password: boolean,
    phoneNumber: boolean,
}

export type LoginFields = {
    email: boolean,
    password: boolean,
}

export type UpdateProfileRequest = {
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: string,
}

export type UpdateProfileFields = {
    firstName: boolean,
    lastName: boolean,
    email: boolean,
    phoneNumber: boolean,
}

export type SessionRequest = {
    title: string,
    description: string,
    language: string,
    level: Level,
    tags: string[],
    status: Status,
}

export type SessionFields = {
    title: boolean,
    description: boolean,
    language: boolean,
}
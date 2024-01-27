"use client";

import { createContext, useEffect, useState } from "react";
import { AuthDetails, Session, User } from "./types";
import { getProfile } from "./lib/profile";
import { useRouter } from "next/navigation";

export const AuthContext = createContext<AuthDetails | null>(null);

export const AuthContextProvider = ({
    children
} : {
    children: React.ReactNode
}) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState<User | null>(null);
    const [jwt, setJwt] = useState<string | null>(null);
    const [sessions, setSessions] = useState<Session[]>([]);
    const router = useRouter();
    useEffect(() => {
        let jwt = localStorage.getItem('jwt');
        if (jwt)
            setAuthenticatedUser(jwt);
    })

    const setAuthenticatedUser = async (jwt : string) : Promise<void> => {
        const currentUser = await getProfile(jwt);
        localStorage.setItem('jwt', jwt);
        setJwt(jwt);
        setUser(currentUser);
    }


    const logIn = () : void => {
        if (user !== null)
            router.push("/dashboard/profile");
        else
            router.push("/")
    }

    const logOut = () : void => {
        localStorage.removeItem('jwt');
        setJwt(null);
        router.push("/");
    }

    return (
        <AuthContext.Provider
            value={{
                isLoggedIn,
                user,
                sessions,
                setSessions,
                logIn,
                logOut,
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}
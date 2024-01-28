"use client";

import { createContext, useEffect, useState } from "react";
import { AuthDetails, Session, User } from "./types";
import { getProfile } from "./lib/profile";
import { useRouter, redirect } from "next/navigation";

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
    const [loading, setLoading] = useState(false);
    const router = useRouter();
    useEffect(() => {
        let jwt = localStorage.getItem('jwt');
        if (jwt)
            setAuthenticatedUser(jwt);
    }, [])

    const setAuthenticatedUser = async (jwt : string) : Promise<void> => {
        const currentUser = await getProfile(jwt);
        localStorage.setItem('jwt', jwt);
        setJwt(jwt);
        setUser(currentUser);
    }


    const loginStatus = (currentRoute: "home" | "dashboard") : void => {
        setLoading(true);
        if (user !== null && currentRoute === "home")
            redirect("/dashboard/profile");
        else if (user === null && currentRoute === "dashboard")
            redirect("/")
    }

    const logIn = (jwt : string) : void => {
        setLoading(true);
        setIsLoggedIn(true);
        setAuthenticatedUser(jwt);
        redirect('/dashboard/profile');
    }

    const logOut = () : void => {
        setLoading(true)
        localStorage.removeItem('jwt');
        setJwt(null);
        setUser(null);
        router.push("/");
        setLoading(false);
    }

    return (
        <AuthContext.Provider
            value={{
                isLoggedIn,
                user,
                sessions,
                setSessions,
                jwt,
                loginStatus,
                loading,
                setLoading,
                logIn,
                logOut,
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}
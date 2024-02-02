"use client";

import { createContext, useCallback, useEffect, useState } from "react";
import { AuthDetails, Session, User } from "./types";
import { getProfile } from "./lib/profile";
import { useRouter, usePathname } from "next/navigation";
import { getAllSessions } from "./lib/sessions";

export const AuthContext = createContext<AuthDetails | null>(null);

export const AuthContextProvider = ({
    children
}: {
    children: React.ReactNode
}) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState<User | null>(null);
    const [jwt, setJwt] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);
    const [sessions, setSessions] = useState<Session[]>([]);
    const router = useRouter();
    const pathname = usePathname();

    const setAuthenticatedUser = useCallback(async (token: string): Promise<void> => {
        const currentPath = pathname.split("/")[1];
        if (currentPath === "reset")
            return;
        setLoading(true);

        try {
            const currentUser = await getProfile(token);
            localStorage.setItem('jwt', token);
            setJwt(token);
            const currentSessions = await getAllSessions(token);
            setUser(currentUser);
            if (currentSessions)
                setSessions(currentSessions);
            router.push('/dashboard/profile');
        }
        catch (error) {
            console.log(error);
            localStorage.removeItem('jwt');
            setJwt(null);
            setUser(null);
            router.push("/");
        }
        setLoading(false);
    }, [router])

    useEffect(() => {
        let jwt = localStorage.getItem('jwt');
        if (jwt)
            setAuthenticatedUser(jwt);
    }, [setAuthenticatedUser])


    const logIn = (jwt: string): void => {
        setIsLoggedIn(true);
        setAuthenticatedUser(jwt);
    }

    const logOut = (): void => {
        localStorage.removeItem('jwt');
        setJwt(null);
        setUser(null);
        router.push("/");
    }

    const updateSessions = useCallback(async (): Promise<void> => {
        if (!jwt)
            return;
        setLoading(true);
        const currentSessions = await getAllSessions(jwt!);
        if (currentSessions)
            setSessions(currentSessions);
        setLoading(false);
    }, [jwt]);

    return (
        <AuthContext.Provider
            value={{
                isLoggedIn,
                user,
                sessions,
                setSessions,
                jwt,
                logIn,
                logOut,
                loading,
                updateSessions,
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}
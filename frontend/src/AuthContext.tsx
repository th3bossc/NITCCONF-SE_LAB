"use client";

import { createContext, useCallback, useEffect, useState } from "react";
import { AuthDetails, Session, Tag, User } from "./types";
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
            if (currentPath === "dashboard") {
                setLoading(false)
                return;
            }
            router.push('/dashboard/profile');

        }
        catch (error: any) {
            localStorage.removeItem('jwt');
            setJwt(null);
            setUser(null);
            router.push("/");
            /*
            TODO: error handling
            if (error.response.status === 500)
                SERVER IS NOT RESPONDING
            else
                INVALID TOKEN
            */

        }
        setLoading(false);
    }, [router, pathname])

    useEffect(() => {
        let jwt = localStorage.getItem('jwt');
        if (jwt)
            setAuthenticatedUser(jwt);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])


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
                setUser,
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
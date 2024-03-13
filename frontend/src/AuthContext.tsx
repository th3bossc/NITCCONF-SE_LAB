"use client";

import { createContext, useCallback, useEffect, useState } from "react";
import { AuthDetails, Paper, User } from "./types";
import { getProfile } from "./lib/profile";
import { useRouter, usePathname } from "next/navigation";
import { getAllPapers } from "./lib/papers";

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
    const [papers, setPapers] = useState<Paper[]>([]);
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
            const currentPapers = await getAllPapers(token);
            setUser(currentUser);
            if (currentPapers)
                setPapers(currentPapers);
            if (currentPath === "dashboard") {
                setLoading(false)
                return;
            }
            console.log(currentUser);
            router.push('/dashboard/profile');

        }
        catch (error: any) {
            localStorage.removeItem('jwt');
            setJwt(null);
            setUser(null);
            router.push("/");
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

    const updatePapers = useCallback(async (): Promise<void> => {
        if (!jwt)
            return;
        setLoading(true);
        const currentPapers = await getAllPapers(jwt!);
        if (currentPapers)
            setPapers(currentPapers);
        setLoading(false);
    }, [jwt]);

    return (
        <AuthContext.Provider
            value={{
                isLoggedIn,
                user,
                setUser,
                papers,
                setPapers,
                jwt,
                logIn,
                logOut,
                loading,
                updatePapers,
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}
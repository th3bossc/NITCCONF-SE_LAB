"use client";

import SessionPage from "@/components/SessionPage";
import { useAuthContext } from "@/hooks/useAuthContext";
import { getSession } from "@/lib/sessions";
import { Session } from "@/types";
import { useEffect, useState } from "react";

const SlugPage = ({ params }: { params: { id: string } }) => {
    const [session, setSession] = useState<Session | null>(null);
    const { jwt, sessions } = useAuthContext();
    // useEffect(() => {
    //     const fetchData = async () => {
    //         const data = await getSession(params.id, jwt)
    //         if (data)
    //             setSession(data);
    //     }
    //     fetchData();
    // }, [params.id, jwt])
    useEffect(() => {
        setSession(sessions.find((session) => session.id === params.id) || null);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    return (
        <div>
            <SessionPage session={session} />
        </div>
    );
}

export default SlugPage;
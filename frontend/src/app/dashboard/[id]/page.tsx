"use client";

import SessionPage from "@/components/SessionPage";
import { useAuthContext } from "@/hooks/useAuthContext";
import { getSession } from "@/lib/sessions";
import { Session } from "@/types";
import { useEffect, useState } from "react";

const SlugPage = ({params} : {params: {id : string}}) => {
    const [session, setSession] = useState<Session | null>(null);
    const { jwt } = useAuthContext();
    useEffect(() => {
        const fetchData = async () => {
            const data = await getSession(params.id, jwt)
            setSession(data);
        }
        fetchData();
    }, [params.id, jwt])
    return (
        <div>
            <SessionPage session={session} />
        </div>
    );
}

export default SlugPage;
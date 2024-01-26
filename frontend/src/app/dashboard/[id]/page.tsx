"use client";

import SessionPage from "@/components/SessionPage";
import { getSession } from "@/lib/sessions";
import { Session } from "@/types";
import { useEffect, useState } from "react";

const SlugPage = ({params} : {params: {id : string}}) => {
    const [session, setSession] = useState<Session | null>(null);
    useEffect(() => {
        const fetchData = async () => {
            const data = await getSession(params.id)
            setSession(data);
        }
        fetchData();
    }, [params.id])
    return (
        <div>
            <SessionPage session={session} />
        </div>
    );
}

export default SlugPage;
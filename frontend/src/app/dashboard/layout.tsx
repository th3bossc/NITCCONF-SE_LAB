"use client";

import Sidebar from "@/components/Sidebar";
import { getAllSessions } from "@/lib/sessions";
import { Session } from "@/types";
import { useEffect, useState } from "react";
export default function RootLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    const [sessions, setSessions] = useState<Session[]>([]);
    useEffect(() => {
      const fetchData = async () => {
        const data = await getAllSessions();
        setSessions(data);
      }
      fetchData()
    }, [])

    return (
        <div className="h-screen w-full bg-[#050729] text-white grid grid-cols-6 grid-rows-1">
            <div className="hidden xl:block col-start-1 col-span-1">
                <Sidebar
                  sessions={sessions}
                />
            </div>
            <div className="col-start-2 col-span-5">
              {children}
            </div>
        </div>
    )
}
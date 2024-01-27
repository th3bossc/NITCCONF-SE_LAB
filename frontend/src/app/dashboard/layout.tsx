"use client";

import Navbar from "@/components/Navbar";
import Sidebar from "@/components/Sidebar";
import { useAuthContext } from "@/hooks/useAuthContext";
import { getAllSessions } from "@/lib/sessions";
import { useEffect } from "react";
export default function RootLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    const {sessions, setSessions, logIn} = useAuthContext();
    // logIn();
    useEffect(() => {
      const fetchData = async () => {
        const data = await getAllSessions();
        setSessions(data);
      }
      fetchData()
    }, [setSessions])

    return (
      <>
        <div className="hidden h-screen w-full bg-[#050729] text-white xl:grid grid-cols-6 grid-rows-1">
            <div className="hidden xl:block col-start-1 col-span-1">
                <Sidebar
                  sessions={sessions}
                />
            </div>
            <div className="col-start-2 col-span-5">
              {children}
            </div>
        </div>
        <div className="xl:hidden"> 
          <Navbar 
            sessions={sessions}
          />
          {children}
        </div>
      </>
    )
}
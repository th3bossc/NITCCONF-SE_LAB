"use client";

import Navbar from "@/components/Navbar";
import Sidebar from "@/components/Sidebar";
import { useAuthContext } from "@/hooks/useAuthContext";
import { getAllSessions } from "@/lib/sessions";
import { AnimatePresence, motion } from "framer-motion";
import { useEffect, useState } from "react";
import ReactLoading from 'react-loading';
export default function RootLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    const {jwt, sessions, setSessions, loginStatus} = useAuthContext();
    const [loading, setLoading] = useState(true);
    // loginStatus();
    useEffect(() => {
      const fetchData = async () => {
        const data = await getAllSessions(jwt);
        setSessions(data);
        setLoading(false);
      }
      fetchData()
    }, [setSessions, jwt])

    return (
      <AnimatePresence>
        {
          loading ? (
            <motion.div 
              className="fixed top-0 left-0 w-screen h-screen bg-black z-[9999999] flex items-center justify-center"
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              >
              <ReactLoading
                type="bars"
                color="#fff"
                height="10rem"
                width="10rem"
                />
            </motion.div>
          ) : (
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
      </AnimatePresence>
        
    )
}
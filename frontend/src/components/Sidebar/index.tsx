"use client";

import { oswald } from "@/fonts";
import { motion } from "framer-motion";
import { useState } from "react";
import NavItem from "../NavItem";
const Sidebar = ({
    // current
} : {
    // current: number
}) => {
    const [current, setCurrent] = useState(0);
    const tempSessions = [
        "Session 1",
        "Session 2",
        "Session 3",
        "Add session + "
    ]


    return (
        <div className={`w-[22rem] h-screen bg-backgroundprimary flex flex-col ${oswald.className}`}>
            <div className="font-bold w-full text-center pt-16 text-2xl">
                <span className="text-nitconfprimary" >NIT</span>
                <span className="text-white">CONF</span>
                
            </div>
            <div className="flex flex-col items-center justify-center gap-16 mt-48 text-xl">
                {
                    tempSessions.map((session, index) => (
                        <NavItem  key={index} session={session} index={index} current={current} setCurrent={setCurrent} />
                    ))
                }
            </div>
        </div>
    )
}

export default Sidebar;
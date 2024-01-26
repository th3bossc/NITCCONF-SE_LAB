"use client";

import { oswald } from "@/fonts";
import NavItem from "../NavItem";
import { Session } from "@/types";
import Link from "next/link";
const Sidebar = ({
    sessions,
} : {
    sessions: Session[],
}) => {
    return (
        <div className={`w-full h-screen bg-backgroundprimary flex flex-col ${oswald.className}`}>
            <div className="font-bold w-full text-center pt-16 text-2xl">
                <span className="text-nitconfprimary" >NIT</span>
                <span className="text-white">CONF</span>
                
            </div>
            <div className="flex flex-col items-center justify-center gap-16 mt-48 text-xl">
                {
                    sessions.map((session, index) => (
                        <NavItem  key={index} session={session}  />
                    ))
                }
            </div>
            <Link className="mt-auto mb-8 w-full text-center text-xl" href="/dashboard/profile">
                view profile
            </Link>
        </div>
    )
}

export default Sidebar;
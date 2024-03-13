"use client";

import { oswald } from "@/fonts";
import NavItem from "../NavItem";
import { Paper } from "@/types";
import Link from "next/link";
import { usePathname, useRouter } from 'next/navigation';
import profileImage from '/public/profile.svg';
import Image from 'next/image';
import { motion } from 'framer-motion';

const Sidebar = ({
    papers,
}: {
    papers: Paper[],
}) => {
    const pathname = usePathname();
    const router = useRouter();
    const current = pathname.split("/")[2];
    console.log(papers);
    return (
        <div className={`w-full h-screen bg-backgroundprimary flex flex-col ${oswald.className}`}>
            <div className="font-bold w-full text-center pt-16 text-2xl">
                <span className="text-nitconfprimary" >NIT</span>
                <span className="text-white">CONF</span>

            </div>
            <div className="flex flex-col items-center justify-center gap-16 mt-48 text-xl">
                {
                    papers.map((paper, index) => (
                        <NavItem key={index} paper={paper} current={current} onClick={() => router.push(`/dashboard/${paper.id}`)} />
                    ))
                }
                <motion.span
                    className="w-full text-center relative font-regular cursor-pointer"
                    style={{
                        color: current === "add" ? "var(--primary)" : "#fff"
                    }}
                    onClick={() => router.push('/dashboard/add')}

                >
                    Add new paper +
                    {
                        current === "add" && (
                            <motion.span layoutId="current-item" className="absolute top-0 h-full left-0 w-[0.5rem] rounded-r-lg bg-nitconfprimary" />
                        )
                    }
                </motion.span>
            </div>
            <Link className="mt-auto mb-8 w-full text-center text-xl" href="/dashboard/profile">
                <div className="flex items-center justify-center gap-4 relative">
                    <Image src={profileImage} alt="profile-image" height={20} width={20} />
                    <span> Profile </span>
                    {
                        current === "profile" && (
                            <motion.span layoutId="current-item" className="absolute top-0 h-full left-0 w-[0.5rem] rounded-r-lg bg-nitconfprimary" />
                        )
                    }
                </div>
            </Link>
        </div>
    )
}

export default Sidebar;
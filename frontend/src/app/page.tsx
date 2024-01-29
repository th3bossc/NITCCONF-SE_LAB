"use client";

import Register from "@/components/Register";
import Login from "@/components/Login";
import { useState } from "react";
import { oswald } from "@/fonts";
import AnimatedButton from "@/components/AnimatedButton";
import { AnimatePresence } from "framer-motion";

export default function Home() {
    const [login, setLogin] = useState(false);
    const [register, setRegister] = useState(false);
    return (
        <div className="h-screen w-screen flex flex-col items-center justify-center bg-backgroundprimary">
            <div className={`${oswald.className} text-6xl font-semibold`}>
                <span className="text-nitconfprimary">NIT</span>
                <span className="text-white">CONF</span>
            </div>
            <div className="flex flex-col lg:flex-row gap-4 mt-16">
                <AnimatedButton onClick={() => {
                    setLogin(true);
                    setRegister(false);
                }}>
                    Login
                </AnimatedButton>
                <AnimatedButton onClick={() => {
                    setRegister(true);
                    setLogin(false);
                }}>
                    Register
                </AnimatedButton>
            </div>
            <AnimatePresence>
                {
                    login && (
                        <Login setClose={() => setLogin(false)} />
                    )

                }
            </AnimatePresence>
            <AnimatePresence>
                {
                    register && (
                        <Register setClose={() => setRegister(false)} />
                    )
                }
            </AnimatePresence>
        </div>
    )
}
"use client";

import { useAuthContext } from "@/hooks/useAuthContext";
import { AnimatePresence, motion } from "framer-motion";
import Loading from "../Loading";

const PageWrapper = ({
    children
} : {
    children: React.ReactNode
}) => {
    const { loading } = useAuthContext();

    return (
        <>
            <AnimatePresence>
                {
                    loading && (
                        <motion.div
                            className="fixed top-0 left-0 w-screen h-screen bg-backgroundprimary flex items-center justify-center z-50"
                            initial={{ opacity: 0, scale: 0.8 }}
                            animate={{ opacity: 1, scale: 1 }}
                            exit={{ opacity: 0, scale: 0.8 }}
                            transition={{
                                type: "tween",
                                duration: 0.5,
                                ease: "backInOut"
                            }}
                        >
                            <Loading />
                        </motion.div>
                    )
                }
            </AnimatePresence>
            {children}
        </>
    )
}

export default PageWrapper;
import Image from "next/image"
import deleteIcon from '/public/trash.svg';
import hamburgerImage from '/public/hamburger.svg';
import closeMenu from '/public/close.svg';
import { useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import { oswald } from "@/fonts";
import { usePathname } from "next/navigation";
import { Session } from "@/types";
import Link from "next/link";
import { useAuthContext } from "@/hooks/useAuthContext";
import { deleteSession } from "@/lib/sessions";
import { ToastContainer, toast, Flip } from "react-toastify";
import 'react-toastify/ReactToastify.css';

const Navbar = ({
    sessions,
}: {
    sessions: Session[],
}) => {
    const { jwt, setSessions } = useAuthContext();
    const pathname = usePathname();
    const current = pathname.split("/")[2];
    const [open, setOpen] = useState(false);

    const deleteHandler = async (id: string | undefined) => {
        if (!id)
            return;
        try {
            toast.success('Deleted successfully.', {
                position: "bottom-right",
                autoClose: 1500,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "dark",
                transition: Flip,
                });
            await deleteSession(id, jwt);
            setSessions(prev => prev.filter(session => session.id !== id));
            //TODO: add a dialog prompt before deletion
        }
        catch (error) {
            console.log(error);
            toast.error('Something went wrong!', {
                position: "bottom-right",
                autoClose: 1500,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "dark",
                transition: Flip
                });
        }
    }
    return (
        <div className="fixed top-0 w-full z-[99999]">
            <nav className="bg-backgroundprimary w-full text-black flex gap-4 justify-between items-center p-4">
                <div className="font-bold text-center text-2xl">
                    <span className="text-nitconfprimary" >NIT</span>
                    <span className="text-white">CONF</span>
                </div>
                <Image
                    src={open ? closeMenu : hamburgerImage}
                    alt="menu-open"
                    height={40}
                    width={40}
                    onClick={() => setOpen(prev => !prev)}
                />
            </nav>
            <div>
                <AnimatePresence>
                    {
                        open && (
                            <motion.div
                                className={`${oswald.className} font-normal w-full backdrop-blur-lg text-center overflow-hidden bg-black/60 flex flex-col gap-4 p-2 text-lg`}
                                initial={{ opacity: 0, height: 0 }}
                                animate={{ opacity: 1, height: 'auto' }}
                                exit={{ opacity: 0, height: 0 }}
                                transition={{
                                    staggerChildren: 0.5,
                                }}
                            >
                                {
                                    sessions.map((session) => (
                                        <Link
                                            href={`/dashboard/${session.id}`}
                                            key={session.id}
                                            className="flex relative"
                                            style={{
                                                color: current === session.id ? "var(--primary)" : "#fff",
                                                textDecoration: current === session.id ? "underline" : "none"
                                            }}
                                        >
                                            {session.title}
                                            <div
                                                className="absolute right-0 top-0"
                                                onClick={() => deleteHandler(session.id)}
                                            >
                                                <Image src={deleteIcon} alt="delete-icon" height={25} width={25} />
                                            </div>
                                        </Link>
                                    ))
                                }
                                <Link
                                    href={`/dashboard/add`}
                                    style={{
                                        color: current === "add" ? "var(--primary)" : "#fff",
                                        textDecoration: current === "add" ? "underline" : "none"
                                    }}
                                >
                                    Add new session +
                                </Link>
                                <div className="w-full p-[0.1rem] bg-white my-4" />
                                <Link
                                    href={`/dashboard/profile`}
                                    style={{
                                        color: current === "profile" ? "var(--primary)" : "#fff",
                                        textDecoration: current === "profile" ? "underline" : "none"
                                    }}
                                >
                                    Profile
                                </Link>
                            </motion.div>
                        )
                    }

                </AnimatePresence>
            </div>
            <ToastContainer />
        </div>
    )
}

export default Navbar
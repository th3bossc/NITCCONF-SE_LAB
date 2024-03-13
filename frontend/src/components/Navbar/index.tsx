import Image from "next/image"
import deleteIcon from '/public/trash.svg';
import hamburgerImage from '/public/hamburger.svg';
import closeMenu from '/public/close.svg';
import { useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import { oswald } from "@/fonts";
import { usePathname } from "next/navigation";
import { Paper } from "@/types";
import Link from "next/link";
import { useAuthContext } from "@/hooks/useAuthContext";
import { deletePaper } from "@/lib/papers";
import { ToastContainer, toast, Flip } from "react-toastify";
import 'react-toastify/ReactToastify.css';

import Dialog from '../Dialog';
import { useRouter } from 'next/navigation';

const Navbar = ({
    papers,
}: {
    papers: Paper[],
}) => {
    const { jwt, setPapers } = useAuthContext();
    const pathname = usePathname();
    const current = pathname.split("/")[2];
    const [open, setOpen] = useState(false);
    const [dialog, setDialog] = useState(false);
    const [toDelete, setToDelete] = useState<string | null>(null);
    const router = useRouter();

    const deleteHandler = async (id: string | null) => {
        if (!id)
            return;
        try {
            await deletePaper(id, jwt);
            setPapers(prev => prev.filter(paper => paper.id !== id));
            router.push("/dashboard/profile");
            toast.success('Deleted paper successfully.', {
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
        }
        catch (error) {
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
                                    papers.map((paper) => (
                                        <Link
                                            href={`/dashboard/${paper.id}`}
                                            onClick={() => setOpen(false)}
                                            key={paper.id}
                                            className="flex relative"
                                            style={{
                                                color: current === paper.id ? "var(--primary)" : "#fff",
                                                textDecoration: current === paper.id ? "underline" : "none"
                                            }}
                                        >
                                            {paper.title}
                                            <div
                                                className="absolute right-0 top-0"
                                                onClick={(e) => {
                                                    e.stopPropagation();
                                                    setToDelete(paper.id || null);
                                                    setDialog(true)
                                                }}
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
                                    Add new paper +
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
            <AnimatePresence>
                {
                    dialog && (
                        <motion.div
                            className="fixed top-0 left-0 bg-black/75 w-full h-full z-[100] flex items-center justify-center"
                            initial={{ opacity: 0 }}
                            animate={{ opacity: 1 }}
                            exit={{ opacity: 0 }}
                            transition={{ duration: 0.25, type: "tween" }}
                        >
                            <Dialog
                                setClose={() => setDialog(false)}
                                eventHandler={() => {
                                    deleteHandler(toDelete);
                                    setDialog(false);
                                }}
                            />
                        </motion.div>
                    )
                }
            </AnimatePresence>
        </div>
    )
}

export default Navbar
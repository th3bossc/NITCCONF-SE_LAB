import { Session } from '@/types';
import { AnimatePresence, motion } from 'framer-motion';
import Image from 'next/image';
import deleteIcon from '/public/trash.svg';
import { useState } from 'react';
import { useAuthContext } from '@/hooks/useAuthContext';
import { deleteSession } from '@/lib/sessions';
import { ToastContainer, toast, Flip } from "react-toastify";
import 'react-toastify/ReactToastify.css';

const NavItem = ({ session, current, onClick }: {
    session: Session,
    current: string,
    onClick: () => void
}) => {
    const { jwt, setSessions } = useAuthContext();
    const [hover, setHover] = useState(false);

    const deleteHandler = async (id: string | undefined) => {
        if (!id)
            return;
        try {
            await deleteSession(id, jwt);
            setSessions(prev => prev.filter(session => session.id !== id));
            //TODO: add a dialog prompt before deletion
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
        <motion.span
            className="w-full text-center relative font-regular cursor-pointer"
            onMouseOver={() => setHover(true)}
            onMouseLeave={() => setHover(false)}
            style={{
                color: current === session.id ? "var(--primary)" : "#fff"
            }}
            onClick={onClick}

        >
            {session.title}

            {
                current === session.id && (
                    <motion.span layoutId="current-item" className="absolute top-0 h-full left-0 w-[0.5rem] rounded-r-lg bg-nitconfprimary" />
                )
            }
            <AnimatePresence>
                {
                    hover && (
                        <motion.div
                            className="absolute top-0 h-full right-2"
                            initial={{ opacity: 0, x: 10 }}
                            animate={{ opacity: 1, x: 0 }}
                            exit={{ opacity: 0, x: 10 }}
                            onClick={() => deleteHandler(session.id)}
                        >
                            <Image src={deleteIcon} alt="delete-icon" height={25} width={25} />
                        </motion.div>
                    )
                }
            </AnimatePresence>
            <ToastContainer />
        </motion.span>
    )
}

export default NavItem;
import { Session } from '@/types';
import { AnimatePresence, motion } from 'framer-motion';
import Image from 'next/image';
import deleteIcon from '/public/trash.svg';
import { useState } from 'react';
import { useAuthContext } from '@/hooks/useAuthContext';
import { deleteSession } from '@/lib/sessions';

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
            //TODO: toastify session deleted successfully
        }
        catch (error) {
            console.log(error);
            //TODO: toastify something went wrong
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

        </motion.span>
    )
}

export default NavItem;
import { Session } from '@/types';
import { motion } from 'framer-motion';
import { usePathname, useRouter } from 'next/navigation';
import { Dispatch, SetStateAction } from 'react';

const NavItem = ({session} : {
    session: Session,
}) => {
    const pathname = usePathname();
    const router = useRouter();
    const current = pathname.split("/")[2];
    return (

        <motion.span 
            className="w-full text-center relative font-regular cursor-pointer"
            style={{
                color: current === session.id ? "var(--primary)" : "#fff"
            }}
            onClick={() => router.push(`/dashboard/${session.id}`)}
            
        >
            {session.title}
            
            {
                current === session.id && (
                    <motion.span layoutId="current-item" className="absolute top-0 h-full left-0 w-[0.5rem] rounded-r-lg bg-nitconfprimary" />
                )
            }
        </motion.span>
    )
}

export default NavItem;
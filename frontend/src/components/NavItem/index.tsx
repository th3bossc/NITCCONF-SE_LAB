import { Session } from '@/types';
import { motion } from 'framer-motion';

const NavItem = ({session, current, onClick} : {
    session: Session,
    current: string,
    onClick: () => void
}) => {
    return (

        <motion.span 
            className="w-full text-center relative font-regular cursor-pointer"
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
        </motion.span>
    )
}

export default NavItem;
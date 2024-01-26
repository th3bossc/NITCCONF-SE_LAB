import { motion } from 'framer-motion';
import { Dispatch, SetStateAction } from 'react';

const NavItem = ({index, session, current, setCurrent} : {
    index: number,
    session: string,
    current: number,
    setCurrent: Dispatch<SetStateAction<number>>
}) => {
    return (
        <motion.span 
            key={index} 
            className="w-full text-center relative font-regular cursor-pointer"
            style={{
                color: current === index ? "var(--primary)" : "#fff"
            }}
            onClick={() => setCurrent(index)}
        >
            {session}
            
            {
                current === index && (
                    <motion.span layoutId="current-item" className="absolute top-0 h-full left-0 w-[0.5rem] rounded-r-lg bg-nitconfprimary" />
                )
            }
        </motion.span>
    )
}

export default NavItem;
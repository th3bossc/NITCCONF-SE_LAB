"use client";

import styles from './styles.module.scss'

import useMousePosition from '@/hooks/useMousePosition';

import { motion } from 'framer-motion';
import Link from 'next/link';
import { useRef, useState } from 'react';

const AnimatedButton = ({ children, onClick }: {
    children: React.ReactNode,
    onClick: () => void
}) => {
    const [isHovered, setIsHovered] = useState(false)
    const size = isHovered ? 500 : 1;
    const container = useRef(null);
    const { x, y } = useMousePosition({ ref: container });
    return (
            <motion.div
                ref={container}
                className="z-10 relative w-48"
                whileTap={{ scale: 0.95 }}
            >
                <motion.div
                    className={styles.mask}
                    animate={{
                        WebkitMaskPosition: `${x - size / 2}px ${y - size / 2}px`,
                        WebkitMaskSize: `${size}px`,
                    }}
                    transition={{ type: "tween", ease: "backOut", duration: 1 }}
                    onMouseOver={() => setIsHovered(true)}
                    onMouseLeave={() => setIsHovered(false)}
                >
                    <button onClick={onClick} className="w-full text-lg p-4 font-semibold rounded-lg text-nitconfprimary bg-neutral-200">
                        {children}
                    </button>
                </motion.div>
                <button onClick={onClick} className="w-full text-lg p-4 font-semibold rounded-lg bg-neutral-900 text-white">
                    {children}
                </button>
            </motion.div >
    )
}

export default AnimatedButton;
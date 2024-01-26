import { motion } from "framer-motion";
import Link from "next/link";


const textAnimation = {
    topTextAnimation: {
        rest: {
            y: 0
        },
        hover: {
            y: "-110%",
            transition: {
                duration: 0.3,
                ease: [0.6, 0.01, 0.05, 0.95],
                type: "tween",
            },
        }
    },
    bottomTextAnimation: {
        rest: {
            y: "110%"
        },
        hover: {
            y: 0,
            transition: {
                duration: 0.3,
                ease: [0.6, 0.01, 0.05, 0.95],
                type: "tween",
            },
        }
    }
}

const TextAnimation = ({
    title,
    href,
    top,
    hovered,
}: {
    title: string,
    href: string,
    top: boolean,
    hovered: boolean,
}) => {
    return (
        <Link href={href}>
            <motion.span
                initial="rest"
                animate={hovered ? "hover" : "rest"}
                transition={{
                    staggerChildren: 0.02,
                }}
            >
                {
                    title.split("").map((char, index) => {
                        return (char === " ") ? (
                            <span key={index}>&nbsp;</span>
                        ) : (
                            <motion.span
                                key={index}
                                className="relative inline-block whitespace-nowrap text-inherit font-bold text-3xl text-inherit"
                                variants={top ? textAnimation.topTextAnimation : textAnimation.bottomTextAnimation}
                            >
                                {char}
                            </motion.span>
                        )
                    })
                }
            </motion.span>
        </Link>
    )
}

export default TextAnimation;
"use client";

import { oswald } from "@/fonts"
import AnimatedButton from "../AnimatedButton"
import { motion } from 'framer-motion';
const Dialog = ({ setClose, eventHandler }: {
    eventHandler: () => void,
    setClose: () => void
}) => {
    return (
        <motion.div
            className="bg-white w-[300px] md:w-[500px] h-[300px] p-4 rounded-lg relative text-black"
            initial={{ scale: 0.9 }}
            animate={{ scale: 1 }}
            exit={{ scale: 1.1, opacity: 0 }}
            transition={{ duration: 0.25, type: "tween" }}
        >
            <h1 className={`${oswald.className} font-medium text-2xl w-full text-center`} > Delete Paper? </h1>
            <span className="text-center w-full block pt-8"> Deleting the paper can&apos;t be undone. </span>
            <span className="text-center w-full block pt-8"> Are you sure you want to proceed? </span>
            <div className="mt-auto w-full absolute bottom-4 left-0 flex justify-center items-center">
                <AnimatedButton onClick={eventHandler}>
                    Delete
                </AnimatedButton>
            </div >
            <motion.button type="button"
                whileHover={{ backgroundColor: "#A276FF" }}
                transition={{ duration: 0.1 }}
                onClick={setClose}
                className="absolute top-3 right-2.5 text-gray-400 bg-transparent rounded-lg text-sm p-1.5 ml-auto inline-flex items-center popup-close"><svg
                    aria-hidden="true" className="w-5 h-5" fill="#c6c7c7" viewBox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg">
                    <path fillRule="evenodd"
                        d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                    ></path>
                </svg>
                <span className="sr-only">Close popup</span>
            </motion.button>
        </motion.div >
    )
}

export default Dialog
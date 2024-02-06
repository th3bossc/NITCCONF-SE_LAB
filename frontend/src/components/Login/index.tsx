"use client"
import { ChangeEvent, useEffect, useState } from "react";
import "./index.css";
import AnimatedButton from "../AnimatedButton/index";
import { LoginFields, LoginRequest } from "@/types";
import { useAuthContext } from "@/hooks/useAuthContext";
import { login } from "@/lib/authentication";
import { useRouter } from "next/navigation";
import { ToastContainer, toast, Flip } from "react-toastify";
import 'react-toastify/ReactToastify.css';
import { motion } from "framer-motion";

const Login = ({
    setClose,
}: {
    setClose: () => void
}) => {
    const { jwt, logIn } = useAuthContext();
    const [userRed, setUserRed] = useState(false);
    const [passRed, setPassRed] = useState(false);
    const router = useRouter();
    const [formData, setFormData] = useState<LoginRequest>({
        email: "",
        password: "",
    })

    const [changedData, setChangedData] = useState<LoginFields>({
        email: false,
        password: false,
    })

    useEffect(() => {
        if (changedData.email && formData.email == "")
            setUserRed(true);
        else
            setUserRed(false);
    }, [formData.email, changedData.email])

    useEffect(() => {
        if (changedData.password && formData.password == "")
            setPassRed(true);
        else
            setPassRed(false);
    }, [changedData.password, formData.password])

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }))

        setChangedData((prev) => ({
            ...prev,
            [name]: true,
        }))

    }

    const handleSubmit = async () => {
        try {
            const { token } = await login(formData);
            logIn(token);
        }
        catch (err) {
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

    const handleResetPassword = async () => {
        router.push("/reset")
    }
    return (
        <motion.div
            exit={{ opacity: 0 }}
            className="w-screen h-screen fixed top-0 left-0 z-10 backdrop-blur">
            <div id="login-popup"
                className="bg-black/75 loginContainer">
                <motion.div
                    initial={{ scale: 0.9 }}
                    animate={{ scale: 1 }}
                    exit={{ scale: 1.1, opacity: 0 }}
                    transition={{ duration: 0.25, type: "tween" }}
                    className="relative p-4 w-full max-w-md h-full md:h-auto flex items-center justify-center">

                    <div className="p-1 relative bg-zinc-900 rounded-lg shadow outline">
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

                        <div className="p-5">
                            <p className="mb-4 text-sm font-normal text-gray-800"></p>

                            <div className="text-center">
                                <p className="mb-6 text-2xl font-semibold leading-5 text-white">
                                    Login
                                </p>
                            </div>


                            <form className="w-full">
                                <h1 className="text-white font-light">Email</h1>
                                <input name="email" type="text"
                                    className={userRed ?
                                        "inputUser text-gray-400 mt-2 block w-full rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                        : "inputUser text-gray-400 mt-2 block w-full rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                    placeholder="Email"
                                    autoComplete="off"
                                    onChange={handleChange}
                                    value={formData.email} />
                                {userRed ? <p className="font-normal text-red-500 text-xs pt-2">Email cannot be empty</p> : <></>}

                                <h1 className="text-white font-light mt-2">Password</h1>
                                <input name="password" type="password"
                                    className={passRed ?
                                        "inputPass text-gray-400 mt-2 block w-full rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                        : "inputPass text-gray-400 mt-2 block w-full rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"} placeholder="Password"
                                    autoComplete="off"
                                    onChange={handleChange}
                                    value={formData.password} />
                                {passRed ? <p className="font-normal text-red-500 text-xs pt-2">Password cannot be empty</p> : <></>}
                            </form>
                            <button
                                className="forgotPass mb-3 mt-2 text-sm text-gray-500"
                                onClick={handleResetPassword}
                            >
                                Reset your password?
                            </button>

                            <div className="submitButtonContainer">
                                <AnimatedButton
                                    onClick={handleSubmit}
                                >
                                    <div className="submitButton">Submit</div>
                                </AnimatedButton>
                            </div>
                        </div>
                    </div>
                </motion.div>
            </div>
            <ToastContainer />
        </motion.div>
    )
}

export default Login;
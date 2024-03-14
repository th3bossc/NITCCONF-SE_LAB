"use client"
import { ChangeEvent, useEffect, useState } from "react";
import "./index.css";
import AnimatedButton from "../AnimatedButton/index";
import { RegisterFields, RegisterRequest } from "@/types";
import { register } from "@/lib/authentication";
import { useAuthContext } from "@/hooks/useAuthContext";
import { ToastContainer, toast, Flip } from "react-toastify";
import 'react-toastify/ReactToastify.css';
import { motion } from "framer-motion";
import ReactLoading from 'react-loading';

const Register = ({
    setClose,
}: {
    setClose: () => void
}) => {
    const { logIn } = useAuthContext();
    const [submitted, setSubmitted] = useState(false);
    const [fNameRed, setFNameRed] = useState(false);
    const [lNameRed, setLNameRed] = useState(false);
    const [userRed, setUserRed] = useState(false);
    const [passRed, setPassRed] = useState(false);
    const [phoneRed, setPhoneRed] = useState(false);


    const [formData, setFormData] = useState<RegisterRequest>({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        phoneNumber: ""
    });

    const [changedData, setChangedData] = useState<RegisterFields>({
        firstName: false,
        lastName: false,
        email: false,
        password: false,
        phoneNumber: false,
    })

    useEffect(() => {
        if (changedData.firstName && formData.firstName == "")
            setFNameRed(true);
        else
            setFNameRed(false);
    }, [formData.firstName, changedData.firstName])

    useEffect(() => {
        if (changedData.lastName && formData.lastName == "")
            setLNameRed(true);
        else
            setLNameRed(false);
    }, [formData.lastName, changedData.lastName])

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
    }, [formData.password, changedData.password])

    useEffect(() => {
        if (changedData.phoneNumber && formData.phoneNumber == "")
            setPhoneRed(true);
        else
            setPhoneRed(false);
    }, [formData.phoneNumber, changedData.phoneNumber])


    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
        setChangedData((prev) => ({
            ...prev,
            [name]: true
        }));
    }

    const handleSubmit = async () => {
        if (fNameRed || lNameRed || userRed || passRed || phoneRed)
            return;
        setSubmitted(true);
        try {
            const { token } = await register(formData);
            logIn(token)
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
        setSubmitted(false);
    }

    return (
        <motion.div
            exit={{ opacity: 0 }}
            className="w-screen h-screen z-10 fixed top-0 left-0">
            <div id="Register-popup"
                className="bg-black/75 registerContainer">
                <motion.div
                    initial={{ scale: 0.9 }}
                    animate={{ scale: 1 }}
                    exit={{ scale: 1.1, opacity: 0 }}
                    transition={{ duration: 0.25, type: "tween" }}
                    className="relative p-4 w-full max-w-md h-full md:h-auto flex items-center justify-center">

                    <div className="p-1 relative bg-zinc-900 rounded-lg shadow">
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
                                    Register
                                </p>
                            </div>


                            <form className="w-full flex flex-wrap gap-0 justify-center">

                                <div className="w-1/2">
                                    <h1 className="text-white font-light mt-2">First Name</h1>
                                    <input name="firstName" type="text"
                                        className={fNameRed ?
                                            "inputName text-gray-400 mt-1 block w-[96%] rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            : "inputName text-gray-400 mt-1 block w-[96%] rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={handleChange}
                                        value={formData.firstName} />
                                    {fNameRed ? <p className="font-normal text-red-500 text-xs pt-2">First Name cannot be empty</p> : <></>}
                                </div>

                                <div className="w-1/2">
                                    <h1 className="text-white font-light mt-2">Last Name</h1>
                                    <input name="lastName" type="text"
                                        className={lNameRed ?
                                            "inputName text-gray-400 mt-1 block w-[100%] rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            : "inputName text-gray-400 mt-1 block w-[100%] rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={handleChange}
                                        value={formData.lastName} />
                                    {lNameRed ? <p className="font-normal text-red-500 text-xs pt-2">Last Name cannot be empty</p> : <></>}
                                </div>

                                <div className="w-full">
                                    <h1 className="text-white font-light mt-2">Email</h1>
                                    <input name="email" type="email"
                                        className={userRed ?
                                            "inputUser text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            : "inputUser text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={handleChange}
                                        value={formData.email} />
                                    {userRed ? <p className="font-normal text-red-500 text-xs pt-2">Email cannot be empty</p> : <></>}
                                </div>

                                <div className="w-full">
                                    <h1 className="text-white font-light mt-2">Password</h1>
                                    <input name="password" type="password"
                                        className={passRed ?
                                            "inputPass text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            : "inputPass text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={handleChange}
                                        value={formData.password} />
                                    {passRed ? <p className="font-normal text-red-500 text-xs pt-2">Password cannot be empty</p> : <></>}
                                </div>

                                <div className="w-full">
                                    <h1 className="text-white font-light mt-2">Phone Number</h1>
                                    <input name="phoneNumber" type="text"
                                        className={phoneRed ?
                                            "inputPhone text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            : "inputPhone text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={handleChange}
                                        value={formData.phoneNumber} />
                                    {phoneRed ? <p className="font-normal text-red-500 text-xs pt-2">Phone Number cannot be empty</p> : <></>}
                                </div>
                            </form>

                            <div className="submitButtonContainer mt-5">
                                <AnimatedButton
                                    onClick={handleSubmit}
                                >
                                    <div className="flex items-center justify-center gap-4">
                                        Submit
                                        {
                                            submitted && (
                                                <ReactLoading
                                                    type="spin"
                                                    color="#A276FF"
                                                    height={30}
                                                    width={30}
                                                />
                                            )
                                        }
                                    </div>
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

export default Register;
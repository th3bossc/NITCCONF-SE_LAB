"use client"
import { ChangeEvent, useEffect, useState } from "react";
import "./index.css";
import AnimatedButton from "../AnimatedButton/index";
import { UpdateProfileFields, UpdateProfileRequest, User } from "@/types";
import { useAuthContext } from "@/hooks/useAuthContext";
import { getProfile, updateProfile } from "@/lib/profile";


const EditProfile = ({
    initialData,
    setClose,
}: {
    initialData: User,
    setClose: () => void
}) => {
    const { jwt, setUser } = useAuthContext();
    const [fNameRed, setFNameRed] = useState(false);
    const [lNameRed, setLNameRed] = useState(false);
    const [userRed, setUserRed] = useState(false);
    const [phoneRed, setPhoneRed] = useState(false);


    const [formData, setFormData] = useState<UpdateProfileRequest>({
        firstName: initialData.firstName,
        lastName: initialData.lastName,
        email: initialData.email,
        phoneNumber: initialData.phoneNumber,
    });

    const [changedData, setChangedData] = useState<UpdateProfileFields>({
        firstName: false,
        lastName: false,
        email: false,
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
        if (fNameRed || lNameRed || userRed || phoneRed)
            return;
        try {
            await updateProfile(formData, jwt)
            const updatedUser = await getProfile(jwt);
            setUser(updatedUser);

            console.log("profile updated");
        }
        catch (err) {
            console.log(err);
            //TODO: toastify
        }
    }

    return (
        <div className="w-screen h-screen z-10 fixed top-0 left-0">
            <div id="Register-popup"
                className="bg-black/75 registerContainer">
                <div className="relative p-4 w-full max-w-md h-full md:h-auto flex items-center justify-center">

                    <div className="relative bg-zinc-900 rounded-lg shadow">
                        <button type="button"
                            onClick={setClose}
                            className="exitButton absolute top-3 right-2.5 text-gray-400 bg-transparent rounded-lg text-sm p-1.5 ml-auto inline-flex items-center popup-close"><svg
                                aria-hidden="true" className="w-5 h-5" fill="#c6c7c7" viewBox="0 0 20 20"
                                xmlns="http://www.w3.org/2000/svg">
                                <path fillRule="evenodd"
                                    d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                ></path>
                            </svg>
                            <span className="sr-only">Close popup</span>
                        </button>

                        <div className="p-5">
                            <p className="mb-4 text-sm font-normal text-gray-800"></p>

                            <div className="text-center">
                                <p className="mb-6 text-2xl font-semibold leading-5 purpleText">
                                    Edit Profile
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
                                        value={formData.firstName}
                                    />
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
                                        value={formData.lastName}
                                    />
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
                                        value={formData.email}
                                        disabled
                                    />
                                    {userRed ? <p className="font-normal text-red-500 text-xs pt-2">Email cannot be empty</p> : <></>}
                                </div>

                                <div className="w-full">
                                    <h1 className="text-white font-light mt-2">Phone Number</h1>
                                    <input name="phoneNumber" type="text"
                                        className={phoneRed ?
                                            "inputPhone text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            : "inputPhone text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={handleChange}
                                        value={formData.phoneNumber}
                                    />
                                    {phoneRed ? <p className="font-normal text-red-500 text-xs pt-2">Phone Number cannot be empty</p> : <></>}
                                </div>
                            </form>

                            <div className="submitButtonContainer mt-5">
                                <AnimatedButton
                                    onClick={handleSubmit}
                                >
                                    <div className="submitButton purpleText">Submit</div>
                                </AnimatedButton>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EditProfile;
"use client";

import AnimatedButton from "@/components/AnimatedButton";
import { updatePassword } from "@/lib/profile";
import { ChangeEvent, useState } from "react";

const ResetPasswordPage = ({ params }: { params: { token: string } }) => {

    const [formData, setFormData] = useState({
        password: "",
        confirmPassword: ""
    });

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        setFormData(prev => (
            {
                ...prev,
                [event.target.name]: event.target.value
            }
        ));
    }

    const handleSubmit = async () => {
        if (formData.password !== formData.confirmPassword)
            return;
        if (formData.confirmPassword === "" || formData.password === "")
            return;
        await updatePassword(formData.password, params.token);
    }

    return (
        <div className="h-screen w-screen flex items-center justify-center flex-col gap-4">
            <form className="w-1/3 ">
                <div>
                    <h1 className="text-white font-regular">Password</h1>
                    <input name="password" type="password"
                        className="text-gray-400 mt-2 block w-full rounded-lg border bg-zinc-900 border-nitconfprimary-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-nitconfprimary-500 focus:ring-1 focus:ring-nitconfprimary-500"
                        autoComplete="off"
                        onChange={handleChange}
                        value={formData.password}
                    />
                </div>
                <div>
                    <h1 className="text-white font-regular">Confirm Password</h1>
                    <input name="confirmPassword" type="password"
                        className="text-gray-400 mt-2 block w-full rounded-lg border bg-zinc-900 border-nitconfprimary-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-nitconfprimary-500 focus:ring-1 focus:ring-nitconfprimary-500"
                        autoComplete="off"
                        onChange={handleChange}
                        value={formData.confirmPassword}
                    />
                </div>
            </form >
            <AnimatedButton onClick={handleSubmit}> Update Password </AnimatedButton>
        </div>
    )
}

export default ResetPasswordPage;
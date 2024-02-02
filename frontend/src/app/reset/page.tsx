"use client";

import AnimatedButton from "@/components/AnimatedButton";
import { sendPasswordEmail } from "@/lib/profile";
import { ChangeEvent, useState } from "react";

const ResetPasswordPage = () => {
    const [formData, setFormData] = useState({
        email: ""
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
        console.log(formData)
        if (formData.email === "")
            return;
        await sendPasswordEmail(formData);
    }

    return (
        <div className="h-screen w-screen flex items-center justify-center flex-col gap-4">
            <form className="w-1/3 ">
                <div>
                    <h1 className="text-white font-regular">Email</h1>
                    <input name="email" type="text"
                        className="text-gray-400 mt-2 block w-full rounded-lg border bg-zinc-900 border-nitconfprimary-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-nitconfprimary-500 focus:ring-1 focus:ring-nitconfprimary-500"
                        autoComplete="off"
                        onChange={handleChange}
                        value={formData.email}
                    />
                </div>
            </form >
            <AnimatedButton onClick={handleSubmit}>
                send email
            </AnimatedButton>
        </div>
    )
}

export default ResetPasswordPage;
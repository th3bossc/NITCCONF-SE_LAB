"use client";

import { SessionFields, SessionRequest } from "@/types";
import { ChangeEvent, useEffect, useState } from "react";

const AddSession = () => {
    const [formData, setFormData] = useState<SessionRequest>({
        title: "",
        description: "",
        language: "",
        level: "BEGINNER",
        tags: [],
        status: "PENDING",
    });

    const [changedData, setChangedData] = useState<SessionFields>({
        title: false,
        description: false,
        language: false,
    })

    const [titleRed,setTitleRed] = useState(false);
    const [descRed,setDescRed] = useState(false);
    const [langRed,setLangRed] = useState(false);

    useEffect(() => {
        if(changedData.title && formData.title=="")
            setTitleRed(true);
        else
            setTitleRed(false);
    }, [formData.title, changedData.title])

    useEffect(() => {
        if(changedData.description && formData.description=="")
            setDescRed(true);
        else
            setDescRed(false);
    }, [formData.description, changedData.description])

    useEffect(() => {
        if(changedData.language && formData.language=="")
            setLangRed(true);
        else
            setLangRed(false);
    }, [formData.language, changedData.language])

    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
        setChangedData({
            ...changedData,
            [e.target.name]: true
        });
    }

    const handleSubmit = () => {
        console.log(formData);
    }

    return (
            <div className="w-full h-screen xl:flex xl:justify-center xl:pt-16 text-[#111]">
                <div className="w-full xl:w-[80%] xl:rounded-t-[40px] pt-32 p-4 xl:p-10 2xl:p-16 bg-white relative overflow-y-scroll">
                    <div className="flex flex-col items-center">
                        <h1 className="text-3xl font-bold mb-4">Add Session</h1>
                        <form className="w-full flex flex-col items-center">
                            <div className="w-full">
                                <label htmlFor="title" className="text-lg font-medium mb-2">Title</label>
                                <input
                                    className={titleRed?
                                        "inputPhone text-gray-900 mt-1 block w-full rounded-lg bg-neutral-100 border-red-700 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-900 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                        :"inputPhone text-gray-900 mt-1 block w-full rounded-lg bg-neutral-100 border-gray-700 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-900 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                    type="text"
                                    name="title"
                                    id="title"
                                    value={formData.title}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="w-full flex gap-4 items-center justify-center">
                                <div className="w-full">
                                    <label htmlFor="language" className="text-lg font-medium mb-2">Language</label>
                                    <input
                                        className={langRed?
                                            "inputPhone text-gray-900 mt-1 block w-full rounded-lg bg-neutral-100 border-red-700 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-900 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            :"inputPhone text-gray-900 mt-1 block w-full rounded-lg bg-neutral-100 border-gray-700 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-900 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        type="text"
                                        name="language"
                                        id="language"
                                        value={formData.language}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div className="w-full">
                                    <label htmlFor="level" className="text-lg font-medium mb-2">Level</label>
                                    <select
                                        className={`w-full rounded-md p-2 outline-none ${langRed ? "border-red-500" : "border-[#111]"}`}
                                        name="level"
                                        id="level"
                                        value={formData.level}
                                        onChange={handleChange}
                                    >
                                        <option className="bg-neutral-100" value="BEGINNER">Beginner</option>
                                        <option className="bg-neutral-100" value="INTERMEDIATE">Intermediate</option>
                                        <option className="bg-neutral-100" value="ADVANCED">Advanced</option>
                                        <option className="bg-neutral-100" value="EXPERT"> Expert </option>
                                    </select>
                                </div>
                            </div>
                            <div className="w-full">
                                <label htmlFor="description" className="text-lg font-medium mb-2">Description</label>
                                <textarea
                                    className={descRed?
                                        "inputPhone text-gray-900 mt-1 block w-full rounded-lg bg-neutral-100 border-red-700 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-900 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                        :"inputPhone text-gray-900 mt-1 block w-full rounded-lg bg-neutral-100 border-gray-700 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-900 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                    name="description"
                                    id="description"
                                    value={formData.description}
                                    onChange={handleChange}
                                    rows={20}
                                />
                            </div>
                            <div>

                            </div>
                        </form>
                    </div>
                </div>    
            </div>
    )
}

export default AddSession;
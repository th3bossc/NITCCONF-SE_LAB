"use client";

import AnimatedButton from "@/components/AnimatedButton";
import { useAuthContext } from "@/hooks/useAuthContext";
import { createSession, getSession, updateSession, uploadDoc } from "@/lib/sessions";
import { getTags } from "@/lib/tags";
import { SessionFields, SessionRequest, Tag } from "@/types";
import { ChangeEvent, useEffect, useState } from "react";
import Select, { MultiValue } from 'react-select';
import ReactLoading from 'react-loading';
import { motion, AnimatePresence } from "framer-motion";
import { useRouter } from "next/navigation";
import { ToastContainer, toast, Flip } from "react-toastify";
import 'react-toastify/ReactToastify.css';

const EditSession = ({ params }: { params: { id: string } }) => {
    const router = useRouter();
    const [loading, setLoading] = useState(false);
    const { jwt, updateSessions, sessions } = useAuthContext();
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

    const [file, setFile] = useState<File | null>(null);

    const [titleRed, setTitleRed] = useState(false);
    const [descRed, setDescRed] = useState(false);
    const [langRed, setLangRed] = useState(false);
    const [tags, setTags] = useState<Tag[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            const session = sessions.find((session) => session.id === params.id);
            setFormData({
                title: session?.title || "",
                description: session?.description || "",
                language: session?.language || "",
                level: session?.level || "BEGINNER",
                tags: session?.tags.map((tag) => tag.id || "") || [],
                status: session?.status || "PENDING",
            });
            const res = await getTags(jwt);
            if (res)
                setTags(res);
            setLoading(false);
        }
        fetchData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    console.log(formData);
    useEffect(() => {
        if (changedData.title && formData.title == "")
            setTitleRed(true);
        else
            setTitleRed(false);
    }, [formData.title, changedData.title])

    useEffect(() => {
        if (changedData.description && formData.description == "")
            setDescRed(true);
        else
            setDescRed(false);
    }, [formData.description, changedData.description])

    useEffect(() => {
        if (changedData.language && formData.language == "")
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
        console.log(file);
        const sendData = async () => {
            try {
                const res = await updateSession(params.id, formData, jwt);
                updateSessions();
                const id = res?.id;
                if (id && file) {
                    await uploadDoc(id, file, jwt);
                }
                router.push(`/dashboard/${id}`);
                toast.success('Edited session successfully.', {
                    position: "bottom-right",
                    autoClose: 1500,
                    hideProgressBar: true,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "dark",
                    transition: Flip,
                    });
            }
            catch (error) {
                console.log(error);           
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
        sendData();
    }

    return (
        <div className="w-full h-screen xl:flex xl:justify-center xl:pt-16 text-[#111]">
            <div className="w-full xl:w-[80%] xl:rounded-t-[40px] pt-32 p-4 xl:p-10 2xl:p-16 bg-white relative overflow-y-scroll">
                <AnimatePresence>
                    {
                        loading ? (
                            <motion.div className="absolute w-full h-full top-0 left-0 flex items-center justify-center">
                                <ReactLoading
                                    type="bars"
                                    color="#000"
                                    height="4rem"
                                    width="4rem"
                                />
                            </motion.div>
                        ) : (
                            <div className="flex flex-col items-center">
                                <h1 className="text-3xl font-bold mb-4">Edit Session</h1>
                                <form className="w-full flex flex-col items-center">
                                    <div className="w-full">
                                        <label htmlFor="title" className="text-lg font-medium mb-2">Title</label>
                                        <input
                                            className={titleRed ?
                                                "text-gray-900 mt-1 block w-full rounded-lg bg-neutral-100 border-red-700 py-2 pl-4 shadow-sm outline-none placeholder:text-gray-900 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                                : "text-gray-900 mt-1 block w-full rounded-md bg-white border border-[#ccc] py-2 pl-4 shadow-sm outline-none placeholder:text-gray-900 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
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
                                                className={langRed ?
                                                    "text-gray-900 mt-1 block w-full rounded-md bg-white border-red-700 py-2 pl-4 shadow-sm outline-none placeholder:text-gray-900 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                                    : "text-gray-900 mt-1 block w-full rounded-md bg-white border border-[#ccc] py-2 pl-4 shadow-sm outline-none placeholder:text-gray-900 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
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
                                                className={`text-gray-900 mt-1 block w-full rounded-md bg-white border border-[#ccc] py-2 pl-4 shadow-sm outline-none placeholder:text-gray-900 focus:border-gray-500 focus:ring-1 focus:ring-gray-500`}
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
                                        <div className="w-full">
                                            <label htmlFor="tags" className="text-lg font-medium mb-2">Tags</label>
                                            <Select
                                                id="tags"
                                                options={tags.map((tag) => ({ value: tag.id, label: tag.title }))}
                                                className="w-full rounded-md p-2 outline-none border-[#111]"
                                                isMulti
                                                onChange={(selected: MultiValue<{ value: string | undefined, label: string }>) => {
                                                    setFormData({
                                                        ...formData,
                                                        tags: selected && selected ? selected.map((s) => s.value || "") : [],
                                                    });
                                                }}
                                                defaultValue={
                                                    formData.tags.map((tag) => ({
                                                        value: tag,
                                                        label: tags.find((t) => t.id === tag)?.title || "",
                                                    }))
                                                }
                                            />
                                        </div>
                                    </div>
                                    <div className="w-full mb-2">
                                        <label htmlFor="description" className="text-lg font-medium mb-2">Description</label>
                                        <textarea
                                            className={descRed ?
                                                "text-gray-900 mt-1 block w-full rounded-lg bg-neutral-100 border-red-700 py-2 pl-4 shadow-sm outline-none placeholder:text-gray-900 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                                : "text-gray-900 mt-1 block w-full rounded-md bg-white border border-[#ccc] py-2 pl-4 shadow-sm outline-none placeholder:text-gray-900 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                            name="description"
                                            id="description"
                                            value={formData.description}
                                            onChange={handleChange}
                                            rows={20}
                                        />
                                    </div>
                                    <div>
                                        <div className="w-full mb-2">
                                            <input
                                                className={langRed ?
                                                    "text-gray-900 mt-1 block w-full rounded-lg bg-neutral-100 border-red-700 py-2 pl-4 shadow-sm outline-none placeholder:text-gray-900 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                                    : "text-gray-900 mt-1 block w-full rounded-md bg-white border border-[#ccc] py-2 pl-4 shadow-sm outline-none placeholder:text-gray-900 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                                type="file"
                                                name="file"
                                                id="file"
                                                onChange={(e) => setFile(e.target.files ? e.target.files[0] : null)}
                                            />
                                        </div>
                                    </div>
                                </form>
                                <AnimatedButton onClick={handleSubmit}>
                                    Submit
                                </AnimatedButton>
                            </div>
                        )
                    }
                </AnimatePresence>
            </div>
            <ToastContainer />
        </div>
    )
}

export default EditSession;
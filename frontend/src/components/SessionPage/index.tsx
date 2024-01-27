import { Review, Session, Tag } from "@/types";
import { oswald } from "@/fonts";
import PdfLink from "../PdfLink";
import Comment from "../Comment";
import Tags from "../Tags";
import { AnimatePresence, motion } from "framer-motion";
import ReactLoading from 'react-loading';
const SessionPage = ({
    session,
}: {
    session: Session | null
}) => {
    const tagTemp : Tag[] = [
        {
            id: "1234",
            title: "hello",
            sessions: [],
        },

        {
            id: "12334",
            title: "hello",
            sessions: [],
        },

        {
            id: "12342",
            title: "hello",
            sessions: [],
        }
    ]
    const commentsTemp : Review[] = [
        {
            id: "1234",
            reviewer: {
                id: "12",
                firstName: "Diljith",
                lastName: "Dileep",
                email: "diljith2003@gmail.com",
                phoneNumber: "8075762993",
                role: "REVIEWER"
            },
            comment: "comcomcomententent"
        },
        {
            id: "431",
            reviewer: {
                id: "12",
                firstName: "Diljith",
                lastName: "Dileep",
                email: "diljith2003@gmail.com",
                phoneNumber: "8075762993",
                role: "REVIEWER"
            },
            comment: "comcomcomententent"
        },
        {
            id: "123234344",
            reviewer: {
                id: "123",
                firstName: "Diljith",
                lastName: "Dileep",
                email: "diljith2003@gmail.com",
                phoneNumber: "8075762993",
                role: "REVIEWER"
            },
            comment: "comcomcomententent"
        },
        {
            id: "1232224",
            reviewer: {
                id: "1234",
                firstName: "Diljith",
                lastName: "Dileep",
                email: "diljith2003@gmail.com",
                phoneNumber: "8075762993",
                role: "REVIEWER"
            },
            comment: "comcomcomententent"
        }
    ]
    return (
        <div className="w-full h-screen xl:flex xl:justify-center xl:pt-16 text-[#111]">
            <div className="w-full xl:w-[80%] xl:rounded-t-[40px] pt-32 p-4 xl:p-10 2xl:p-16 bg-white relative overflow-y-scroll">
                <AnimatePresence>
                    {
                        session ? (
                            <motion.div>
                                <div className={`${oswald.className} font-bold text-4xl 2xl:text-6xl uppercase text-center xl:text-left w-full`}>
                                    {session.title}
                                </div>
                                <div className="mt-4 flex gap-4 justify-center xl:justify-start">
                                    {
                                        tagTemp.map((tag) => (
                                            <Tags key={tag.id} {...tag} />
                                        ))
                                    }
                                </div>
                                <div className="mt-16 2xl:mt-32 w-full xl:w-[30rem] 2xl:w-[50rem] font-medium text-md 2xl:text-2xl text-center xl:text-left">
                                    {session.description}
                                </div>
                                <div className="flex flex-col mt-4 2xl:mt-8 gap-2 font-regular text-md 2xl:text-2xl text-center xl:text-left">
                                    <span> language: {session.language} </span>
                                    <span> level: {session.level} </span>
                                </div>
                                {
                                    session.id && (
                                        <PdfLink id={session.id} status={session.status} />
                                    )
                                }

                                <div className="w-full mt-16 2xl:mt-32">
                                    <h2 className={`w-full text-center text-3xl 2xl:text-5xl font-bold ${oswald.className}`} > COMMENTS </h2>
                                    <div className="flex flex-col gap-4 mt-8">
                                        {
                                            commentsTemp.map((review) => (
                                                <Comment key={review.id} {...review} />
                                            ))
                                        }
                                    </div>
                                </div>
                            </motion.div>
                        ) : (
                            <motion.div className="absolute w-full h-full top-0 left-0 flex items-center justify-center">
                                <ReactLoading
                                    type="bars"
                                    color="#000"
                                    height="4rem"
                                    width="4rem"
                                />
                            </motion.div>
                        )
                    }
                </AnimatePresence>
            </div>
        </div>
    )
}

export default SessionPage;